import java.io.IOException;
import java.util.ArrayList;
/**
 * Class for holding trees nodes
 * @author orcahm
 *
 */
public class Node {

	private ArrayList<BTree> objectList;
	private BTree leftParent;
	private BTree rightParent;
	public Node(){
		this.objectList = new ArrayList<BTree>();
		leftParent = null;
		rightParent = null;
	}
	/**
	 * Writes every words in node
	 */
	public String toString(){
		StringBuilder str = new StringBuilder();
		for(int i=0;i<this.objectList.size();i++){
			str.append(this.objectList.get(i).getWord() + " ");
		}
		return str.toString();
	}
	/**
	 * Finds word in tree and returns its object

	 * @param word
	 * @return
	 */
	public BTree searchWord(String word){
		
		BTree temp = new BTree();
		
		if(this.getObjectList().isEmpty()) return null;
		
		for(int index=0;index<this.getObjectList().size();index++){
			
			if(this.getObjectList(index).getWord().compareTo(word)==0){
				return temp = this.getObjectList(index);
			}else if(this.getObjectList(index).getWord().compareTo(word)>0){
				temp = this.getObjectList(index).getLeftChild().searchWord(word);
				return temp;
			}else if(index==this.getObjectList().size()-1){
				temp = this.getObjectList(index).getRightChild().searchWord(word);
				return temp;
			}
		}
		return null;
	}
	/**
	 * finds words which is starts with the key
	 * @param word which is search
	 * @param file file will be write
	 * @throws IOException
	 */
	public void searchWordWanting(String word,FileWrite file) throws IOException{
		
		int size = word.length();
		
		if(this.getObjectList().isEmpty()) return;
		
		for(int index=0;index<this.getObjectList().size();index++){
			if(this.getObjectList(index).getWord().startsWith(word)){
				this.WantingWord(word,index,file);
				return;
			}else if(this.getObjectList(index).getWord().substring(0, size).compareTo(word)>0){
				this.getObjectList(index).getLeftChild().searchWordWanting(word,file);
				return;
			}else if(index==this.getObjectList().size()-1){
				this.getObjectList(index).getRightChild().searchWordWanting(word,file);
				return;
			}
		}
		return;
	}
	/**
	 * searchWordWanting function continous in here
	 * @param word words which searching
	 * @param index
	 * @param file file which will be written
	 * @throws IOException
	 */
	private void WantingWord(String word,int index,FileWrite file) throws IOException{
		
	int i=index;
		
		if(this.getObjectList().isEmpty()){
			return;
		}
		
		for(;i<this.getObjectList().size();i++){
			this.getObjectList(i).getLeftChild().WantingWord(word,0, file);
			if(this.getObjectList(i).getWord().startsWith(word)){
				file.objectWrite(this.getObjectList(i), 1);
			}
		}
		this.getObjectList(i-1).getRightChild().WantingWord(word, 0, file);
			
	}
		
	
	

	public BTree getLeftParent() {
		return leftParent;
	}

	public void setLeftParent(BTree leftParent) {
		this.leftParent = leftParent;
	}

	public BTree getRightParent() {
		return rightParent;
	}

	public void setRightParent(BTree rightParent) {
		this.rightParent = rightParent;
	}

	public ArrayList<BTree> getObjectList() {
		return objectList;
	}
	
	public BTree getObjectList(int index){
		return this.objectList.get(index);
	}

	public void setObjectList(BTree temp) {
		this.objectList.add(temp);
	}
	
	public void setObjectList(int index,BTree temp){
		this.objectList.add(index, temp);
	}
	
	public void removeObject(int index){
		this.objectList.remove(index);
	}
	
}
