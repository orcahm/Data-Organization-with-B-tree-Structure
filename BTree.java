import java.util.ArrayList;
/**
 * creates object which will hold attributes of it
 * @author orcahm
 *
 */
public class BTree implements Comparable<BTree> {

	private String word;
	private ArrayList<String> fileName;
	private ArrayList<Integer> lineNo;
	private ArrayList<Integer> charNo;
	private Node leftChild;
	private Node rightChild;
	private Node ownNode;

	public BTree(String word,String fileName,int lineNo,int charNo){
		this.fileName = new ArrayList<String>();
		this.lineNo = new ArrayList<Integer>();
		this.charNo = new ArrayList<Integer>();
		this.leftChild = new Node();
		this.rightChild = new Node();
		this.ownNode = new Node();
		
		this.word = word;
		this.fileName.add(fileName);
		this.lineNo.add(lineNo);
		this.charNo.add(charNo);
	}
	
	public BTree(){
		this.word = new String();
		this.fileName = new ArrayList<String>();
		this.lineNo = new ArrayList<Integer>();
		this.charNo = new ArrayList<Integer>();
		this.leftChild = new Node();
		this.rightChild = new Node();
		this.ownNode = new Node();
	}
/**
 * birden fazla o kelimeden var ise sadece özelliklerini listeye eklemekte kullanılır.
 * @param temp
 */
	public void attribute(BTree temp){
		this.fileName.add(temp.getFileName().get(0));
		this.lineNo.add(temp.getLineNo().get(0));
		this.charNo.add(temp.getCharNo().get(0));
	}
	
	public int compareTo(BTree o) {
		if(this.word.compareTo(o.getWord())>0){
			return 1;
		}else if(this.word.compareTo(o.getWord())<0){
			return -1;
		}else return 0;
	}
	
	public Node getLeftChild() {
		return leftChild;
	}
	
	public void setLeftChild(Node leftChild) {
		this.leftChild = leftChild;
	}
	
	public Node getRightChild() {
		return rightChild;
	}
	
	public void setRightChild(Node rightChild) {
		this.rightChild = rightChild;
	}
	
	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public ArrayList<String> getFileName() {
		return fileName;
	}

	public void setFileName(ArrayList<String> fileName) {
		this.fileName = fileName;
	}

	public ArrayList<Integer> getLineNo() {
		return lineNo;
	}

	public void setLineNo(ArrayList<Integer> lineNo) {
		this.lineNo = lineNo;
	}

	public ArrayList<Integer> getCharNo() {
		return charNo;
	}

	public void setCharNo(ArrayList<Integer> charNo) {
		this.charNo = charNo;
	}

	public Node getOwnNode() {
		return ownNode;
	}

	public void setOwnNode(Node ownNode) {
		this.ownNode = ownNode;
	}

}
