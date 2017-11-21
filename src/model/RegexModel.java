package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
/**
 * A class that provides facilities to parse strings, and retrieve formatted meta data.
 * The class takes in a string when initialised, and retrieves the following as strings:
 * Standardised date 
 * location
 * description
 * standardised time
 * 
 * Whether or not it was a reminder or calendar event
 * 
 * 
 * @author Nika Khaburzania
 * @author Jaysen Munsami
 *
 */
public class RegexModel{
	//Array will later be used to store each word in the supplied string
	private String[] result;
	
	//Features of the string that will be retrieved through regex parsing
	private String date;
	private String time;
	private String location;
	private String description;
	private boolean isReminder;
	private boolean isCalendarEvent;
	private int unsupported;
	//Index at which parsing starts
	private int index;
	
	/**
	 * The constructor takes in a string upon initialisation, and attempts to parse it.
	 * This is done by breaking the string (a sentence) down into an array of individual words,
	 * checking the first few words for 'remind' or 'meet' to identify the type (calendar event or reminder event),
	 * looping through every word in the array of words to retrieve a time, day or date.
	 * If the word 'on' is found, the following word is assumed to be a day in word form.
	 * If the word 'next' is found, the following word is assumed to be a day in word form.
	 * If the word is 'at', the following word is assumed to be a time, or 1 word location,
	 * If the word is anyhting other than this, the word is a 'descriptive' word, and added to the description.
	 *
	 */
	public RegexModel(String command){
		//Split command into an array of lowercase words from the given string
		command = command.toLowerCase();
		result = command.split("\\s");
		index = 0;
		
		//Initialise values
		description = "";
		time = "";
		date = "";
		location  = "";
		
		
		//Identify the type
		if(result[0].matches("[rR]emind|[Tt]ell")){
			this.isReminder = true;
			this.isCalendarEvent = false;
			
			if(result[1].matches("me") && result[2].matches("to")){
				index = 3;
			}else{
				index =1;
			}

		}
		
		else if(result[0].matches("[lL]unch|[mM]eet(ing)?|[dD]ate")){
			this.isReminder = false;
			this.isCalendarEvent = true;
			
			index = 0;
		}
		
		else{
			System.out.println("Unsupported command");
			unsupported = 1;
		}
		
		
		//Loop through the query to retrieve meta data
		for(int i= index; i< result.length -1; i++){
			//Scan for time
			if(result[i].matches("\\d[apAP][mM]|evening|morning|\\d{1,2}:\\d{2}(!?[apAP][mM])")){
				time = time(result[i]);
			}
			//Scan for Date
			else if(result[i].matches("\\d{1,2}\\/\\d{2}\\/\\d{2,4}")){ 
				//Slash format
				date = date(result[i]);
			}
			//Scan for day
			else if(parseDayFromString(result[i])>0){
				
				//Day found		
				date = result[i] + " " + result[i+ 1] + " " + result[i+2];
				i++;
				i++;
			}
			
			//Scan for on
			else if(result[i].matches("on")){
				date = on(result[i+1]);
				i++;
			}
			//Scan for next
			else if(result[i].matches("next")){
				date = next(result[i+1]);
				i++;
			}
			
			//Scan for at
			else if(result[i].matches("at")){
				//Check 
				if(result[i+1].matches("\\d[ap]m|evening|morning|\\d{1,2}:\\d{2}")){
					time = time(result[i+1]);
				}
				else {
					location = result[i+1];
				}
				i++;
			}else{
				description += result[i] + " ";
			}

		}
		// If there was no description, time, date or location, set the string value to '-'
		if(description == ""){description="-";}
		if(date == ""){date="-";}
		if(time == ""){time="-";}
		if(location == ""){location="-";}
		
		}


	/**
	 * This method defines the string representation of the item:
	 * using the boolean parameters to determine whether it's reminder or calendar event,
	 * and concatenating all of the stored strings of the class.
	 *
	 */
	public String toString(){
		if(isCalendarEvent){
			return "Event: " + description + "| Date: " + date + " | Time: " + time + " | Location: " + location;
		}
		else if(isReminder){
			return  description + "| Date: " + date + " | Time: " + time + " | Location: " + location;
		}
		
		else{
			return "";
		}
	}
	
	


	/**
	 * This method is used when the user types 'on'. This method is given the word after 'on',
	 * which is assumed to be a written day word (eg. 'wednesday')
	 * 
	 * The program retrieves a day of the week number from this word string using another method.
	 * The program creates a new instance of calendar.
	 * The difference between today's date and the day number is calculated, and the appropriate amount of days is added to todays date
	 */
		public String on(String day) {
			int dayNumber = parseDayFromString(day);
			
		        Calendar date = Calendar.getInstance();
		        int difference = dayNumber - date.get(Calendar.DAY_OF_WEEK);
		        if (difference < 0)  {difference += 7;}
		        date.add(Calendar.DAY_OF_MONTH, difference);
		        String dateString = dateFormatter(date.getTime());
	//	        System.out.println(dateString);
		        return dateString;
			
		}
		/**
		 * This method is used when the user types 'next'. This method is given the word after 'next',
		 * which is assumed to be a written day word (eg. 'wednesday')
		 * 
		 * The program retrieves a day of the week number from this word string using another method.
		 * The program creates a new instance of calendar.
		 * The difference between today's date and the day number is calculated, and the appropriate amount of days is added to todays date + 7 days
		 * 
		 * This method is ideentical to the on method, but adds 7 extra days.
		 */
		public String next(String day) {
			int dayNumber = parseDayFromString(day);			
	        Calendar date = Calendar.getInstance();
	        int difference = dayNumber - date.get(Calendar.DAY_OF_WEEK);
	        if (difference < 0) {difference += 7;}
	        difference +=7;
	        date.add(Calendar.DAY_OF_MONTH, difference);
	        String dateString = dateFormatter(date.getTime());
//	        System.out.println(dateString);
	        return dateString;
		}
		
		/**
		 * Given a string in slash format: dd/MM/YYYY or dd/MM/YY, the program parses this into a date object.
		 * The date object is then reformatted using another method, to the stanardised format needed as a string. 
		 */
		public String date(String input){
			Date date = new Date();
			try {	date = new SimpleDateFormat("dd/MM/yy").parse(input);	}
			catch (ParseException e) {	e.printStackTrace();	}
			
			return dateFormatter(date);
		}
		
		/**
		 * Given a string in with time in format #am/#pm/'evening'/'morning'/##:##,
		 * The program parses the time and returns a string of the time in 24h format.
		 * 
		 * Morning is assumed to be 09:00
		 * Evening is assumed to be 20:00 
		 */
		public String time(String input){
			if(input.matches("\\d[apAP][mM]")){
				SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
			       SimpleDateFormat parseFormat = new SimpleDateFormat("hha");
			       Date date;
				try {
					date = parseFormat.parse(input);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return "";
				}
			       return displayFormat.format(date);
			}
			else if(input.matches("evening")){return "20:00";}
			else if(input.matches("morning")){return "09:00";}
			else if(input.matches("\\d{1,2}:\\d{2}")){return input;} 
			else{System.out.println("improper time: "+ input);return "";}
			
		}
		
		
		/**
		 * Given a date object, this method formats the date into the correct date as a string:
		 * 
		 * day + month + #st/#nd/#rd/#th
		 * 
		 * Eg. given 		Friday 17 Mar 2017	 ---> Friday March 17th
		 */
		public String dateFormatter(Date date){
			SimpleDateFormat newFormat = new SimpleDateFormat("d");
	        String dateString = newFormat.format(date);
	        if(dateString.endsWith("1") && !dateString.endsWith("11")){
	        	newFormat = new SimpleDateFormat("EEEE MMMM d'st'");
	        }    
	        else if(dateString.endsWith("2") && !dateString.endsWith("12")){
	        	newFormat = new SimpleDateFormat("EEEE MMMM d'nd'");
	        }
	        else if(dateString.endsWith("3") && !dateString.endsWith("13")){
	        	newFormat = new SimpleDateFormat("EEEE MMMM d'rd'");
	        }  
	        else{
	        	newFormat = new SimpleDateFormat("EEEE MMMM d'th'");
	        } 
	        String newDate = newFormat.format(date.getTime());
	        return newDate;
		}

		
	
		//given a string 'day', it returns an integer, denoting the day of the week
		public int parseDayFromString(String day){
			int dayNumber =0;
			switch(day){
				case "monday": dayNumber = Calendar.MONDAY;
					break;
				case "tuesday": dayNumber = Calendar.TUESDAY;
					break;
				case "wednesday": dayNumber = Calendar.WEDNESDAY;
					break;
				case "thursday": dayNumber = Calendar.THURSDAY;
					break;
				case "friday": dayNumber = Calendar.FRIDAY;
					break;
				case "saturday": dayNumber = Calendar.SATURDAY;
					break;
				case "sunday": dayNumber = Calendar.SUNDAY;
					break;
				default: break;
			}
			return dayNumber;
		}
	
		
		//	Getter method 
		public boolean isReminder(){
			return isReminder;
		}
		
		public int getUnsupported(){
			return unsupported;
		}
}
