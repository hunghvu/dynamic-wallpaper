package model;

import java.util.ArrayList;
/**
 * This class construct list of time and manipulate it
 * @author Hung Vu
 *
 */
public class TimeList {
	/**
	 * List of time.
	 */
	private static ArrayList<String> MY_TIME_LIST;	
	
	/**
	 * Constructor
	 */
	public TimeList() {
		
		MY_TIME_LIST = new ArrayList<String>();
//		myTimeListString = new StringBuilder();
		
	}
	
	/**
	 * Add time to the list.
	 * @param theTime time given by a user.
	 * @return true when add is success, false otherwise.
	 */
	public boolean addTime(String theTime) {
		if(!MY_TIME_LIST.contains(theTime)) {
			
			MY_TIME_LIST.add(theTime);
			return true;
			
		} else {
			
			return false;
			
		}
		
	}
	
	/**
	 * Delete the a given time if it appears in the list.
	 * @param theTime time given by a user.
	 * @return an integer indicates whether a deletion is success. <br>
	 * false is fail
	 * true is success
	 */
	public boolean deleteTime(String theTime) {
		
		if(MY_TIME_LIST.contains(theTime)) {
			
			MY_TIME_LIST.remove(theTime);
			return true;
			
		} else {
			
			return false;
			
		}
		
	}
	
	public void clearTimeList() {
		
		MY_TIME_LIST.clear();
		
	}
	
	public boolean isEmpty() {
		
		return MY_TIME_LIST.isEmpty();
		
	}
	
	public String toString() {
		
		return String.join("\n\n", MY_TIME_LIST);
		
	}
}
