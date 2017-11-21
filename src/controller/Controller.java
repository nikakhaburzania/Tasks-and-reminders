package controller;
//Import actionlistener
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import model.Model;
import model.RegexModel;
import view.View;
/**
 * A class to control the application, providing listeners for clicks and managing the model and view.
 * @author Nika Khaburzania
 * @author Jaysen Munsami
 *
 */
public class Controller{
	
	//Model and view stored as class variables
	private View view;
	private Model model;
	//The file names where reminders and calendar events are stored
	String calendarItems = "ci.txt";
	String reminderItems = "ri.txt";
	
	/**
	 * This is the constructor. When the model and view are passed in,
	 * the are set to the class variables so that they can be accessed throughout the class.
	 * There's a listener for the 'enter' key, and a listener for double click on each panel.
	 * 
	 */
	public Controller(View view, Model model){
		this.view = view;
		this.model = model;
		
		view.addTextFieldListener(new tfListener());
		view.calendarAddClickListener(new clickListener());
		view.remindersAddClickListener(new clickListener());
	}
	
	/**
	 * This is a nested class that implements mouse listener that looks for double click.
	 * If a double click is found at an index, then the tab of the click is checked. After this occurs, 
	 * the model is informed and an item at the specified index is removed.
	 */
	class clickListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			 if (e.getClickCount() == 2) {
                 // Double-click detected
				 if(view.currentTab()==0){
			            if(model.getCalendarItems().size() != 0){  
			            	//item exists in calendar
			            	 int index = view.getList("calendar").getSelectedIndex();
			                 view.removeCalendarItem(index);
			                 System.out.println(model.getCalendarItems().size());
			                 System.out.println(model.getCalendarItems());
			                 model.removeCalendarItem(index);
			                 
			                 PrintWriter pw;
			 				try {
			 					pw = new PrintWriter(calendarItems);
			 					for (int i = 0; i < model.getCalendarItems().size(); i++) {
			 					    pw.println(model.getCalendarItems().get(i)); // note the newline here
			 					}
			 					pw.flush(); // make sure everything in the buffer actually gets written.
			 					System.out.println(model.getCalendarItems());
			 				} catch (FileNotFoundException e1) {
			 					// TODO Auto-generated catch block
			 					e1.printStackTrace();
			 				}
		               }
				 } else if(view.currentTab()==1){
					 if(model.getReminderItems().size() != 0){  	
						 //item exists in reminders
							 int index = view.getList("reminder").getSelectedIndex();
			                 view.removeRemindersItem(index);
			                 model.removeReminderItem(index);
			                 PrintWriter pw;
			 				try {
			 					pw = new PrintWriter(reminderItems);
			 					for (int i = 0; i < model.getReminderItems().size(); i++) {
			 					    pw.println(model.getReminderItems().get(i)); // note the newline here
			 					}
			 					pw.flush(); // make sure everything in the buffer actually gets written.
			 					System.out.println(model.getReminderItems());
			 				} catch (FileNotFoundException e1) {
			 					// TODO Auto-generated catch block
			 					e1.printStackTrace();
			 				}
					 }
				 }
             } 
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	/**
	 * This is a nested class that implements action listener that looks for the enter key to be pressed.
	 * If the enter key is pressed, then the model should be given a new string to interpret, and the view
	 * should be given that string to display. Using an if statement, and a boolean value from the model, the correct
	 * panel to display the string in is determined.
	 */
	class tfListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			String textFieldText = view.getFieldText();
			
			RegexModel regex = new RegexModel(textFieldText);
			if(regex.getUnsupported() == 0){
				//its supported command so we can proceed
				if(regex.isReminder()==false){
					model.addCalendarItem(regex.toString());
					view.addCalendarElement(regex.toString());
					
					PrintWriter pw;
					try {
						pw = new PrintWriter(calendarItems);
						for (int i = 0; i < model.getCalendarItems().size(); i++) {
						    pw.println(model.getCalendarItems().get(i)); // note the newline here
						}
						pw.flush(); // make sure everything in the buffer actually gets written.
						System.out.println(model.getCalendarItems());
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
				} else {
					model.addReminderItem(regex.toString());
					view.addRemindersElement(regex.toString());
					
					PrintWriter pw;
					try {
						pw = new PrintWriter(reminderItems);
						for (int i = 0; i < model.getReminderItems().size(); i++) {
						    pw.println(model.getReminderItems().get(i)); // note the newline here
						}
						pw.flush(); // make sure everything in the buffer actually gets written.
						System.out.println(model.getReminderItems());
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
			view.clearTextField();
			
			
			
			
		}
	}
	
	/**
	 * This method returns the correct string representation of a calendar event, given its components in string form.
	 */
	public String calendarString(String event, String date, String time, String location){
		String wholeString = "Event: " + event + " | Date: " + date +" | " + "Time: " + time + " | " + "Location: " + location ;		
		return wholeString;
	}
	

}
	
	
