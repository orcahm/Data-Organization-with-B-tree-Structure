import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
/**
 * 
 * @author orcahm
 *
 */
public class ReadFile {

	private String fileName;
	private ArrayList<String> lineList;
	/**
	 * Class for reading text files
	 * @param filename (name of file text)
	 * @throws IOException
	 */
	public ReadFile(File filename) throws IOException{
		this.lineList = new ArrayList<String>();
		String str = null;
		
		try{
		
			BufferedReader bf = new BufferedReader(new FileReader(filename));
			
			this.fileName = filename.getName();
			while((str=bf.readLine()) != null ){
					this.lineList.add(str);	
			}
		}catch(Exception e){
			System.err.println("ERROR : " + e.getMessage());
		}
	}
	
	public ArrayList<String> getLineList() {
		return lineList;
	}

	public String getFileName() {
		return fileName;
	}
	
}