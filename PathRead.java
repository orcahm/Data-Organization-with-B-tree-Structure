import java.io.File;

/**
 * Class for reading files
 * @author orcahm
 *
 */
public class PathRead {
	
	private File [] fileList ;
	/**
	 * reads file and sort it
	 * @param fileName (paths name)
	 */
	public PathRead(File fileName){
		
		fileList = fileName.listFiles();
		quickSort(0, fileList.length-1);
	}
	/**
	 * 
	 * @return
	 */
	public File [] getFileList() {
		return fileList;
	}
	/**
	 * 
	 * @param index
	 * @return
	 */
	public File getFileList(int index) {
		return fileList[index];
	}
	
	/**
	 * 
	 * @param lo
	 * @param hi
	 */
	public void quickSort(int lo,int hi){
		if(hi<=lo){
			return;
		}
		int pivotIndex = sort(lo,hi);
		this.quickSort(lo, pivotIndex-1);
		this.quickSort(pivotIndex+1, hi);	
	}
	/**
	 * 
	 * @param lo
	 * @param hi
	 * @return
	 */
	public int sort(int lo,int hi){
		   int i = lo, j = hi+1; 
		   
		   while (true) 
		   { 
			  while (fileList[lo].getName().toLowerCase().compareTo(fileList[++i].getName().toLowerCase())>0)
		         if (i == hi) break; 
			  while (fileList[--j].getName().toLowerCase().compareTo(fileList[lo].getName().toLowerCase())>0)
		         if (j == lo) break;
			  
		      if (i >= j) break; 
		      this.swap(i, j); 
		   } 
		   this.swap(lo, j); 
		   return j; 
		} 
	/**
	 * 
	 * @param arg1
	 * @param arg2
	 */
	public void swap(int arg1,int arg2){
		
		File temp = fileList[arg1];
		fileList[arg1] = fileList[arg2];
		fileList[arg2] = temp;
	}
	
}