package model;

//Import file management
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
/**
 * A class to provide store lists of each item, and manage storing them persistently onto a serialised file.
 * @author Nika Khaburzania
 * @author Jaysen Munsami
 *
 */

public class Model{

	//Variable sized arraylists store calendar items, and reminder items respectively
	private ArrayList<String> calendarItems;
	private ArrayList<String> reminderItems;
	
	
	/**
	 * The constructor initialises the arraylists that were detailed above.
	 * It then checks if the file's already exist. If it does,
	 * then it tries to load strings from that file into the program. This occurs only if the file is not blank.
	 *
	 */
	public Model(){
		calendarItems = new ArrayList<String>();
		reminderItems =  new ArrayList<String>();
		
		BufferedReader br = null;
		FileReader fr;
		String line;
		
		File f = new File("ci.txt");
		if(f.exists() && !f.isDirectory()) { 

			
			try {
				br = new BufferedReader(new FileReader("ci.txt"));
				while((line = br.readLine()) != null){
					addCalendarItem(line);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		File f2 = new File("ri.txt");
		if(f2.exists() && !f2.isDirectory()) { 
			try {
				br = new BufferedReader(new FileReader("ri.txt"));
				while((line = br.readLine()) != null){
					addReminderItem(line);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
		
		System.out.println(calendarItems);
		System.out.println(reminderItems);
	}
	
	// Setter methods to add items
	
	public void addCalendarItem(String item){
		calendarItems.add(item);
	}
	
	public void addReminderItem(String item){
		reminderItems.add(item);
	}
	
	//Getter methods to get all items
	
	
	public ArrayList<String> getCalendarItems(){
		return calendarItems;
	}
	
	public ArrayList<String> getReminderItems(){
		return reminderItems;
	}
	// Setter methods to remove an item at a given index.
	
	public void removeCalendarItem(int index){
		calendarItems.remove(index);
	}
	
	public void removeReminderItem(int index){
		reminderItems.remove(index);
	}
}
