package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedMap;

import tester.Tester;
import diary.Diary;
import diary.Entry;
import main.SearchResultsBuilder.TimePeriod;

public class Menu
{
	
	Scanner s = new Scanner(System.in);
	
	DataStore testDatastore;
	Diary testDiary;
	
	public static void main(String[] args) throws IOException, ClassNotFoundException 
	{
		// TODO Auto-generated method stub
		
		Menu menu = new Menu();
		
		menu.init();
		menu.process();
	}
	
	/**
	 * Runs the menu method
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 */
	private void process() throws IOException, ClassNotFoundException 
	{
		runMenu();
	}
	
	
	/**
	 * Initialises the DataStore class
	 */
	private void init() 
	{
		
		testDatastore= new DataStore();
		testDiary= new Diary();
		
	}
	
	/**
	 * Runs the menu so that the program can take user input and perform the chosen tasks
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 */
	
	private void runMenu() throws IOException, ClassNotFoundException {
		String choice;
        boolean exit=false;
        
        do
        {
            printMenu();
            choice=s.nextLine();
        
            switch (choice.toUpperCase())
            {
            	case "A":
            		add();
            		break;
            	case "P":
            		print();
            		break;
            	case "S":
            		find();
            		break;
            	case "R":
            		remove();
            		break;
            	case "E":
            		edit();
            		break;
            	case "Z":
            		testDiary.undo();
            		break;
            	case "X":
            		testDiary.redo();
            		break;
            	case "F":
            		saveToFile();
            		break;
            	case "L":
            		loadFile();
            		break;
            	case "V":
            		runAutomatedTest();
            		break;
            	case "Q":
            		System.out.println("Goodbye\n");
            		exit=true;
                    break;
            default: System.out.println("That is not a valid choice, please try again");
                    break;         
        }
    }while (!exit);
    }
	
	

	/**
	 * Sends the program to a method in the tree class
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 */
	private void loadFile() throws FileNotFoundException, IOException, ClassNotFoundException 
	{
		System.out.println("Enter the path name of the file you want to file");
		String path= s.nextLine();
		try(ObjectInputStream reader = new ObjectInputStream(new FileInputStream(path)))
		{
			testDatastore = (DataStore) reader.readObject();
		}
	}

	/**
	 * Method to save the binary tree to text file, creates the print writer and output stream, sending the values to a method in the tree class. 
	 * If an error occurs the program outputs "Error occured"
	 * @param root
	 * @throws IOException
	 */
	
	private void saveToFile() throws FileNotFoundException, IOException 
	{
		System.out.println("Enter the path name for the file you want to save");
		String path = s.nextLine();
		try(ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(path)))
		{
			writer.writeObject(testDatastore);
		}
	}
	
	
	/**
	 * A method to get user input, so that the user can give the input on what the program is finding
	 */
	private void find() 
	{
		while(true)
		{
		String name;
		String pattern = "yyyy-MM-dd HH:mm";
		ZoneId timeZone = ZoneId.systemDefault();
		DateTimeFormatter parser = DateTimeFormatter.ofPattern(pattern);
		
		// TODO Auto-generated method stub
		Set<String> names = new HashSet<String>();
		while(true)
		{
			System.out.println("Enter the name of the help professional you are looking for");
			name= s.nextLine();
			if(name.equals("")) break;
			names.add(name);
		}

		System.out.println("Enter the start date of the appointments you're are looking for");
		String startDateTimeStr= s.nextLine();
		System.out.println("Enter the end date of the appointments you're are looking up to");
		String endDateTimeStr= s.nextLine();
		
		//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		
		ZonedDateTime from;
		try
		{
			from= LocalDateTime.parse(startDateTimeStr, parser).atZone(timeZone);
		}
		catch(DateTimeParseException e)
		{
			System.out.println("Error");
			continue;
		}
		
		ZonedDateTime to;
		try
		{
			to= LocalDateTime.parse(endDateTimeStr, parser).atZone(timeZone);
		}
		catch(DateTimeParseException e)
		{
			System.out.println("Error");
			continue;
		}
		
		SortedMap<String, List<TimePeriod>> searchResults = testDatastore.searchForAppointmentTimes(names, from, to);
		for(Map.Entry<String, List<TimePeriod>> availableTimes : searchResults.entrySet())
		{
			System.out.println(availableTimes.getKey());
			for(TimePeriod time : availableTimes.getValue())
			{
				ZonedDateTime startDateTime =time.getStart();
				ZonedDateTime endDateTime = time.getEnd();
						
				String stringStartDateTime= startDateTime.toString();
				String stringEndDateTime= endDateTime.toString();
				
				System.out.println(stringStartDateTime + stringEndDateTime);
			}
		}
		}
		
		
	}
	
	
	
/**
 * Runs tests when selected in order to test that the program functions properly
 */
	public void runAutomatedTest() 
	{
		// TODO Auto-generated method stub
		Tester.test();
	}

	/**
	 * Prints a menu for the user to know what each command can do
	 */
	
	public static void printMenu() 
	{
		System.out.println("A-	 Allows the user to add healthprofessional and appointments to the program");
		System.out.println("E-  Allows the user to edit already added appointments");
		System.out.println("R- Allows the user to remove a healthprofessional/ appointment");
		System.out.println("F- Allows the user to search through health professionals to find when a free appointment is");
		System.out.println("Z- Undo the last action");
		System.out.println("X- Redo the last action");
		System.out.println("S- Save the current list of health professionals to a file");
		System.out.println("L- Load the list of health professioanls from a user selected file");
		System.out.println("V-	Run the automated tests ");
		System.out.println("Q-	Quit the program");
	}
	
	/**
	 * This method is used to add more items to the tree when selected, takes user input and puts them in the tree
	 */
	public void add() 
	{
		addProf();
	}
	
	public void addProf() 
	{ 
		
		System.out.println("Enter the name of the health professional you want to add");
		String name= s.nextLine();
		
		System.out.println("Enter the profession of the health professional you want to add");
		String profession= s.nextLine();
		System.out.println("Enter the work location of the health professional you want to add");
		String workLocation= s.nextLine();
		
		HealthProfessional newHealthProfesional = new HealthProfessional(name, profession, workLocation);
		testDatastore.addEntry(name, newHealthProfesional);
	
		addEntry(name);

	}
	
	public void addEntry(String name) 
	{
		
		while(true)
		{
			String pattern = "yyyy-MM-dd HH:mm";
			ZoneId timeZone = ZoneId.systemDefault();
			DateTimeFormatter parser = DateTimeFormatter.ofPattern(pattern);
			
			System.out.println("Enter the start date of the appointments you want to add");
			System.out.println("Enter in the format dd-mm-yyyy hh:mm");
			
			String startDateTimeStr= s.nextLine();
			
			
	 
			System.out.println("Enter the end date of the appointments you want to add");
			System.out.println("Enter in the format dd-mm-yyyy hh:mm");
			String endDateTimeStr= s.nextLine();
	
	
						
			System.out.println("Enter the treatment type of the appointment you want to add");
			String treatmentType= s.nextLine();
					
			ZonedDateTime startDateTime;
			try
			{
				startDateTime= LocalDateTime.parse(startDateTimeStr, parser).atZone(timeZone);
			}
			catch(DateTimeParseException e)
			{
				System.out.println("Error");
				continue;
			}
			
			ZonedDateTime endDateTime;
			try
			{
				endDateTime= LocalDateTime.parse(endDateTimeStr, parser).atZone(timeZone);
			}
			catch(DateTimeParseException e)
			{
				System.out.println("Error");
				continue;
			}
			System.out.println(startDateTime);
			System.out.println(endDateTime);
			Entry newEntry= new Entry(startDateTime, endDateTime, treatmentType);
			
			testDatastore.addDiaryEntry(name, newEntry);
						
			// testDatastore.addDiaryEntry(name, newEntry);
			break;
		}
	}
	
	/**
	 * When chosen takes user input and sends that input to the remove method in the tree class
	 */

	public void remove() 
	{
		while(true)
		{
		
			String pattern = "yyyy-MM-dd HH:mm";
			ZoneId timeZone = ZoneId.systemDefault();
			DateTimeFormatter parser = DateTimeFormatter.ofPattern(pattern);
			
			System.out.println("Enter the start date of the appointment you want to remove");
			String startDateTimeStr= s.nextLine();
			
			ZonedDateTime startDateTime;
			try
			{
				startDateTime= LocalDateTime.parse(startDateTimeStr, parser).atZone(timeZone);
			}
			catch(DateTimeParseException e)
			{
				System.out.println("Error");
				continue;
			}
			
			System.out.println("Enter the name of the health profesional");
			String name=s.nextLine();
		
			testDatastore.removeDiaryEntry(name, startDateTime);
		
			break;
		}
		
	}

	public void edit() 
	{
		while(true)
		{
			
			String pattern = "yyyy-MM-dd HH:mm";
			ZoneId timeZone = ZoneId.systemDefault();
			DateTimeFormatter parser = DateTimeFormatter.ofPattern(pattern);
			
			String name=s.nextLine();
			String editStartDateTimeStr= s.nextLine();
			
			ZonedDateTime editStartDateTime;
			try
			{
				editStartDateTime= LocalDateTime.parse(editStartDateTimeStr, parser).atZone(timeZone);
			}
			catch(DateTimeParseException e)
			{
				System.out.println("Error");
				continue;
			}
			
			System.out.println("Enter the start date of the appointment you want to replace");
			String startDateTimeStr= s.nextLine();
			ZonedDateTime newStartDateTime;
			
			try
			{
				newStartDateTime= LocalDateTime.parse(startDateTimeStr, parser).atZone(timeZone);
			}
			catch(DateTimeParseException e)
			{
				System.out.println("Error");
				continue;
			}
			
			System.out.println("Enter the end date of the appointments you want to replace");
			String endDateTimeStr= s.nextLine();
			ZonedDateTime newEndDateTime;
			
			try
			{
				newEndDateTime= LocalDateTime.parse(endDateTimeStr, parser).atZone(timeZone);
			}
			catch(DateTimeParseException e)
			{
				System.out.println("Error");
				continue;
			}
			
			System.out.println("Enter the treatment type of the appointment you want to replace");
			String treatmentType= s.nextLine();
			
			Entry newEntry= new Entry(newStartDateTime, newEndDateTime, treatmentType);
			
			testDatastore.editDiaryEntry(name, editStartDateTime, newEntry);
			break;
		}
		
	}

	public void print() 
	{
		for(HealthProfessional prof : testDatastore.professionals())
		{
			for(diary.Entry entry : prof.getDiary().entries())
			{
				System.out.println(entry.getInfo());
			}
		}
		
	}
	
}

