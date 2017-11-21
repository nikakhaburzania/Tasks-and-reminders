package view;
 
 
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import model.Model;


 
public class View extends JPanel{
	
	Model model;
	private JPanel calendarPanel;
	private JPanel remindersPanel;
	private JTextField textField;
	private DefaultListModel<String> calendarListModel;
	private DefaultListModel<String> remindersListModel;
	private JList<String> calendarList;
	private JList<String> remindersList;
	private JTabbedPane tabbedPane;

    public View(Model model) {
        super(new BorderLayout());
         this.model = model;
        tabbedPane = new JTabbedPane();	//creating tabs
         
        /*
         * Creating calendar list and calendar list model
         */
        calendarListModel = new DefaultListModel<String>();
        calendarList = new JList<String>(calendarListModel);
        
        /*
         * Creating reminder list and reminder list model
         */
        remindersListModel = new DefaultListModel<String>();
        remindersList = new JList<String>(remindersListModel);
        
        /*
         * Creating one jpanel for each tab, one scrollpane for each tab and adding this scrollpane to panels
         */
        calendarPanel = new JPanel();
        remindersPanel = new JPanel();

    	JScrollPane jscrollpane;
    	JScrollPane jscrollpane2;
    	
        tabbedPane.addTab("Calendar", calendarPanel);
        calendarPanel.setLayout(new GridLayout(1,1));
        jscrollpane = new JScrollPane(calendarList);
        calendarPanel.add(jscrollpane);
        
		tabbedPane.addTab("Reminders", remindersPanel );
		remindersPanel.setLayout(new GridLayout(1,1));
        jscrollpane2 = new JScrollPane(remindersList);
        remindersPanel.add(jscrollpane2);
        
        
        add(tabbedPane, BorderLayout.CENTER);
        tabbedPane.setAlignmentX(JComponent.CENTER_ALIGNMENT);;
               
        this.textField = new JTextField("");
        add(textField, BorderLayout.SOUTH);
       
        

        for(int i=0; i<model.getCalendarItems().size(); i++){
			calendarListModel.addElement(model.getCalendarItems().get(i));
		}
		
		for(int i=0; i<model.getReminderItems().size(); i++){
			remindersListModel.addElement(model.getReminderItems().get(i));
		}
        
        
    }
     
    /*
     * methods to add listener to specific component
     */
    public void addTextFieldListener(ActionListener listener){
    	textField.addActionListener(listener);
    }
    
    public void calendarAddClickListener(MouseListener listener){
    	calendarList.addMouseListener(listener);
    }
    
    public void remindersAddClickListener(MouseListener listener){
    	remindersList.addMouseListener(listener);
    }
    
    
    /*
     * method to add new element to calendar JList
     */
    public void addCalendarElement(String element){
    	calendarListModel.addElement(element);
    }
    
    /*
     * method to add new element to reminder JList
     */
    public void addRemindersElement(String element){
    	remindersListModel.addElement(element);
    }
    
    
    
    public String getFieldText(){
    	return textField.getText();
    }
    
    public void clearTextField(){
    	textField.setText("");
    }
    
    /*
     * method to find out current tab
     */
    public int currentTab(){
    	return tabbedPane.getSelectedIndex();
    }
   
    
    public int getSelectedCalendar(){
    	return calendarList.getSelectedIndex();
    }
    
    public int getSelectedReminders(){
    	return remindersList.getSelectedIndex();
    }
    
    public void removeCalendarItem(int index){
    	calendarListModel.remove(index);
    }
    
    public void removeRemindersItem(int index){
    	remindersListModel.remove(index);
    }
    
    public JList<String> getList(String listName){
    	if(listName == "calendar"){
    		return calendarList;
    	} else {
    		return remindersList;
    	}
    }
    
    public static void showGUI(View view) {
        //Creating and setting up the window.
        JFrame frame = new JFrame("TabbedPaneDemo");
        frame.setSize(550,250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         
        //Add some content to the window
        frame.add(view, BorderLayout.CENTER);
        
        frame.setVisible(true);
    }

	
}
