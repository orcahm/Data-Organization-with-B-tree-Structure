import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * bütün yürütülen classdır.
 * @author orcahm
 *
 */
public class Operation {

	int n = 2; /*Defult Btree minimum element.Now it is 4-5 tree.*/
	
	ArrayList<ReadFile> readText;
	ReadFile stopWordList;
	ReadFile commentList;
	Node root;
	FileWrite output;
	/**
	 * operations begins and ends here
	 * @param args values from command line
	 * @throws IOException exception for not to read or write from file
	 */
	public Operation(String [] args) throws IOException{
		
		output = new FileWrite(args[3]);
		readText = new ArrayList<ReadFile>();
		File file = new File(args[0]);
		textRead(file);
		
		file = new File(args[1]);
		stopWordList = new ReadFile(file);
		file = new File(args[2]);
		commentList = new ReadFile(file);
		
		for(int i=0;i<readText.size();i++){
			splitFile(i);
		}
				
		for(int i=0;i<commentList.getLineList().size();i++){
			commandsRun(i);
		}
		
		output.close();
		
}
	/**
	 * Scan files in the path adress and give them order than open them and use them
	 * read text in the files and hold them in the readText arraylist.
	 * @param fileName path file adress
	 * @throws IOException for reading file this exception is for not to open file.
	 */
	private void textRead(File fileName) throws IOException{
		
		PathRead pr = new PathRead(fileName);
		
		for(int i=0; i<pr.getFileList().length;i++){
			if(pr.getFileList()[i].isDirectory()){
				textRead(pr.getFileList()[i]);
			}else{
				readText.add(new ReadFile(pr.getFileList(i)));
			}
		}
	}
	/**
	 * split lines in the text files
	 * the words which splitted eliminates according to stopWords.
	 * param file return readText arraylist index the file that is going to be read 
	 */
	private void splitFile(int file){
		
		for(int line=0;line<readText.get(file).getLineList().size();line++){
			int charNo=0;
			String [] str = readText.get(file).getLineList().get(line).split("[\\p{IsPunctuation}\\p{IsWhite_Space}&&[^']]+");
			
			for(int j=0;j<str.length;j++){
				charNo = readText.get(file).getLineList().get(line).indexOf(str[j],charNo);
				str[j] =str[j].trim();
				if(str[j].length()==0){
					charNo += str[j].length()+1;	
					continue;
				}
				if(!stopWordList.getLineList().contains(str[j].toLowerCase())){
					BTree temp = new BTree(str[j].toLowerCase(),readText.get(file).getFileName(),line+1,charNo+1);
					if(root==null){
						root = new Node();
						temp.setOwnNode(root);
						root.setObjectList(temp);
					}else
					putSearch(temp,root);
				}
				charNo += str[j].length()+1;		
			}
		}
	}
	/**
	 * Find the place that words will be add
	* it sends place and temp to addTree function
	 * @param temp word's temporarily created btree object
	 * @param node when  node scanning tree it holds where this node at.
	 * @return
	 */
	private boolean putSearch(BTree temp,Node node){
		
		boolean add = false;
		int index=0;
		
		if(node.getObjectList().isEmpty()){
			return true;
		}else{
			for(;index<node.getObjectList().size();index++){
				
				if(node.getObjectList(index).compareTo(temp)==0){
					node.getObjectList().get(index).attribute(temp);
					break;
				}else if(node.getObjectList(index).compareTo(temp)==1){
					add=putSearch(temp, node.getObjectList(index).getLeftChild());
					break;
				}else if(index==node.getObjectList().size()-1){
					add=putSearch(temp, node.getObjectList(index).getRightChild());
					index++;
					break;
				}
			}
			if(add){
				addTree(temp,node,index);
				return false;
			}
		}
		return false;
	}
	/**
	 * put temp in the right place in tree
	 * if node is full according to format of btree it sends btree object to upwards
	 * @param temp temp word's temporarily created btree object.
	 * @param node which is going to be add
	 * @param index index of add in the node
	 */
	private void addTree(BTree temp,Node node,int index){
	
	temp.setOwnNode(node);
	node.getObjectList().add(index, temp);
	if(index!=0 && !node.getObjectList(index).getLeftChild().getObjectList().isEmpty()){
		node.getObjectList(index).getLeftChild().setLeftParent(node.getObjectList(index-1));
		node.getObjectList(index-1).setRightChild(node.getObjectList(index).getLeftChild());
	}
	if(index!=node.getObjectList().size()-1 && !node.getObjectList(index).getLeftChild().getObjectList().isEmpty()){
		node.getObjectList(index).getRightChild().setRightParent(node.getObjectList(index+1));
		node.getObjectList(index+1).setLeftChild(node.getObjectList(index).getRightChild());		
	}
	
	if(node.getObjectList().size()<2*n+1){
		return;
	}else{
		int parentIndex = 0;
		Node tempParent = new Node();
		if(node.getRightParent()!=null){
			parentIndex =  node.getRightParent().getOwnNode().getObjectList().indexOf(node.getRightParent());
			tempParent = node.getRightParent().getOwnNode();
		}else if(node.getLeftParent()!=null){
			parentIndex = node.getLeftParent().getOwnNode().getObjectList().indexOf(node.getLeftParent())+1;
			tempParent = node.getLeftParent().getOwnNode();
		}
		
			temp = node.getObjectList(n);
			temp.getLeftChild().setRightParent(null);
			temp.getRightChild().setLeftParent(null);
			node.removeObject(n);
			
			Node tempNode = new Node();
			for(int i=0;i<n;i++){
				tempNode.setObjectList(node.getObjectList(n));
				tempNode.getObjectList(i).setOwnNode(tempNode);
				node.removeObject(n);
			}
			
			temp.setLeftChild(node);
			temp.setRightChild(tempNode);
			temp.getLeftChild().setRightParent(temp);
			temp.getRightChild().setLeftParent(temp);
			
			if(node==root){
				temp.setOwnNode(tempParent);
				tempParent.setObjectList(temp);
				root=tempParent;
			}else{
			addTree(temp, tempParent, parentIndex);
			}
	}	
}
	/**
	 * do command with use the tree
	 * and send it to writing function
	 * @param index which command is going to execute is hold in this index
	 * @throws IOException
	 */
	private void commandsRun(int index) throws IOException{
		
		String [] str = commentList.getLineList().get(index).split(" ");
		
		if(str[0].toLowerCase().equals("traverse")){
			if(str[1].toLowerCase().equals("level-order")){
				ArrayList<Node> list = new ArrayList<Node>();
				list.add(root);
				output.br.append(commentList.getLineList().get(index)+"\n");
				output.levelTraverse(list);
				output.br.append("\n\n");
			}else{
				output.br.append(commentList.getLineList().get(index)+"\n");
				output.inOrderTraverse(root);
				output.br.append("\n\n");
			}
		}else if(str[0].toLowerCase().equals("search")){
			if(commentList.getLineList().get(index).contains(" and ")){
				BTree find1 = root.searchWord(str[1]);
				BTree find2 = root.searchWord(str[3]);
				output.br.append(commentList.getLineList().get(index));
				combine(find1, find2);	
			}else if(commentList.getLineList().get(index).contains(" not ")){
				BTree find1 = root.searchWord(str[1]);
				BTree find2 = root.searchWord(str[3]);
				output.br.append(commentList.getLineList().get(index));
				deleteFileName(find1, find2);
			}else if(commentList.getLineList().get(index).contains("*")){
				str[1] = str[1].substring(0, str[1].length()-1);
				output.br.append(commentList.getLineList().get(index));
				root.searchWordWanting(str[1], output);
				output.br.append("\n\n");
			}else{
				output.br.append(commentList.getLineList().get(index));
				BTree temp = root.searchWord(str[1]);
				if(!temp.getFileName().isEmpty());
				output.objectWrite(temp,0);
				output.br.append("\n\n");
			}
		}
		
	}
	/**
	 * find the same words that several files has and send them to the writing function
	 * 
	 * @param sends the first word of find1 search to the Btree object
	 * @param sends the first word of find2 search to the Btree object
	 * @throws IOException
	 */
	private void combine(BTree find1,BTree find2) throws IOException{
		
		BTree temp1 = new BTree();
		BTree temp2 = new BTree();
		temp1.setWord(find1.getWord());
		temp2.setWord(find2.getWord());
		
		for(int i=0;i<find1.getFileName().size();i++){
			if(find2.getFileName().contains(find1.getFileName().get(i))){
				temp1.getFileName().add(find1.getFileName().get(i));
				temp1.getLineNo().add(find1.getLineNo().get(i));
				temp1.getCharNo().add(find1.getCharNo().get(i));
			}
		}
		for(int i=0;i<find2.getFileName().size();i++){
			if(find1.getFileName().contains(find2.getFileName().get(i))){
				temp2.getFileName().add(find2.getFileName().get(i));
				temp2.getLineNo().add(find2.getLineNo().get(i));
				temp2.getCharNo().add(find2.getCharNo().get(i));
			}
		}
		if(!temp1.getFileName().isEmpty()){
		output.objectWrite(temp1, 1);
		output.objectWrite(temp2, 1);
		}
		output.br.append("\n\n");
		
	}
	/**
	 * Finds the files that has got first word and hasn't got second word
	 * @param find1
	 * @param find2
	 * @throws IOException
	 */
	private void deleteFileName(BTree find1,BTree find2) throws IOException{
		
		BTree temp = new BTree();
		temp.setWord(find1.getWord());
		
		for(int i=0;i<find1.getFileName().size();i++){
			if(!find2.getFileName().contains(find1.getFileName().get(i))){
				temp.getFileName().add(find1.getFileName().get(i));
				temp.getLineNo().add(find1.getLineNo().get(i));
				temp.getCharNo().add(find1.getCharNo().get(i));
			}
		}
		
		if(!temp.getFileName().isEmpty()){
			output.objectWrite(temp, 1);
			}
			output.br.append("\n\n");
			
	}
}
