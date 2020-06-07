package model;
import java.io.File;
/**
 * This class return an array of file in given directory.
 * @author Hung Vu
 *
 */
public class FileArray {
	/**
	 * Array of file.
	 */
	private static File[] myFileList;
	
	/**
	 * Constructor
	 * @param theDir given directory
	 */
	public FileArray(File theDir) {
		
		myFileList = theDir.listFiles();
		
	}
	
	/**
	 * Return array of file.
	 * @return array of file.
	 */
	public File[] getFileList() {
		
		return myFileList;
		
	}
	
//	public String toString() {
//		return "a";
//	}
}
