import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * created for file writing operations
 * @author orcahm
 *
 */
public class FileWrite {

	BufferedWriter br;
	
	public FileWrite(String arg) throws IOException{
		br = new BufferedWriter(new FileWriter(new File(arg)));
	}
	
	public void close() throws IOException{
		this.br.close();
	}
	/**
	 * travel tree in level order and write
	 * @param list
	 * @throws IOException
	 */
	public void levelTraverse(ArrayList<Node> list) throws IOException{
		
		
		br.append(list.get(0).toString());
		
		if(!list.get(0).getObjectList(0).getLeftChild().getObjectList().isEmpty()){
			for(int i=0;i<list.get(0).getObjectList().size();i++){
				list.add(list.get(0).getObjectList(i).getLeftChild());
				if(i==list.get(0).getObjectList().size()-1){
					list.add(list.get(0).getObjectList(i).getRightChild());
				}
			}
		}
		
		list.remove(0);
		if(list.size()==0){
			return;
		}else levelTraverse(list);
		
	}
	/**
	 * travel tree with levelorder format and write it
	 * @param node 
	 * @throws IOException
	 */
	public void inOrderTraverse(Node node) throws IOException{
		
		int i=0;
		
		if(node.getObjectList().isEmpty()){
			return;
		}
		
		for(;i<node.getObjectList().size();i++){
			inOrderTraverse(node.getObjectList(i).getLeftChild());
			br.append(node.getObjectList(i).getWord() + " ");	
		}
		inOrderTraverse(node.getObjectList(i-1).getRightChild());

	}
/**
 * write word and it's attributes
 * @param temp
 * @param key
 * @throws IOException
 */
	public void objectWrite(BTree temp,int key) throws IOException{
		
		String fileName = "" ;
		int count=0;
		
		for(int i=0;i<temp.getFileName().size();i++){
			if(!fileName.equals(temp.getFileName().get(i))){
				br.append("\n");
				if(key==1){
					br.append(temp.getWord()+ " ");
				}
				fileName = temp.getFileName().get(i);
				br.append(fileName + " ");
				count=0;
				
			}
			if(count!=0) br.append(", ");
			br.append(temp.getLineNo().get(i)+":"+temp.getCharNo().get(i));
			count=1;
			
		}
	}
}
