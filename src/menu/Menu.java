package menu;

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

import datastore.DataStore;
import datastore.HealthProfessional;
import datastore.SearchResultsBuilder.TimePeriod;
import tester.Tester;

/**
 * The main user-facing menu for this program
 * @author Scott Maclennan
 * @version 1.0
 */
public class Menu
{
	public static final Scanner scanner = new Scanner(System.in);
	private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm";
	
	private DataStore dataStore = new DataStore();
	
	/**
	 * Entry point of the program
	 * @param args command line arguments
	 */
	public static void main(String[] args)
	{
		Menu menu = new Menu();
		menu.menu();
	}
	
	/**
	 * Prompts the user for a valid date and time and returns it
	 * @param promptMessage the message to use to prompt the user
	 * @param errorMessage the message to display in case of invalid input
	 * @return
	 */
	public static ZonedDateTime promptForDateTime(String promptMessage, String errorMessage)
	{
		ZoneId timeZone = ZoneId.systemDefault();
		DateTimeFormatter parser = DateTimeFormatter.ofPattern(DATE_PATTERN);	
		
		while(true)
		{
			System.out.println(promptMessage);
			System.out.println("Enter in the format dd-mm-yyyy hh:mm");
			String startDateTimeStr= Menu.scanner.nextLine();
			
			try
			{
				return LocalDateTime.parse(startDateTimeStr, parser).atZone(timeZone);
			}
			catch(DateTimeParseException e)
			{
				System.out.println(errorMessage);
			}
		}
	}
	
	/**
	 * Prints a menu for the user to know what each command can do
	 */
	private static void printMenu() 
	{
		System.out.println("add-	add health professionals");
		System.out.println("edit- 	edit already existing health professionals");
		System.out.println("remove- remove a health professional");
		System.out.println("search- search through health professionals to find the times they are available");
		System.out.println("save- 	save the current list of health professionals to a file");
		System.out.println("load- 	load the list of health professioanls from a user selected file");
		System.out.println("diary-	open the diary menu");
		System.out.println("test- 	Run the automated tests ");
		System.out.println("exit-	Quit the program");
	}
	
	/**
	 * Runs the menu so that the program can take user input and perform the chosen tasks
	 */
	private void menu()
	{        
        while(true)
        {
            printMenu();
            switch (scanner.nextLine().toLowerCase())
            {
        	case "add":
        		add();
        		break;
        	case "edit":
        		edit();
        		break;
        	case "delete":
        		delete();
        		break;
        	case "print":
        		print();
        		break;
        	case "search":
        		search();
        		break;
        	case "save":
        		saveToFile();
        		break;
        	case "load":
        		loadFile();
        		break;
        	case "diary":
        		diaryMenu();
        		break;
        	case "test":
        		runAutomatedTest();
        		break;
        	case "exit":
        		System.out.println("Goodbye");
        		return;
        	default: 
        		System.out.println("That is not a valid choice, please try again");
                break;
            }
        }
    }
	
	/**
	 * Prompts the user for the details of a health professional and adds it
	 */
	private void add() 
	{ 
		String name;
		while(true)
		{
			System.out.println("Enter the name of the health professional you want to add");
			name= scanner.nextLine();
			if(dataStore.containsProfessional(name)) 
				System.out.println("A professional with this name already exists. Please try again");
			else break;
		}
		
		System.out.println("Enter the profession of the health professional you want to add");
		String profession= scanner.nextLine();
		System.out.println("Enter the work location of the health professional you want to add");
		String workLocation= scanner.nextLine();
		
		dataStore.addEntry(new HealthProfessional(name, profession, workLocation));
	}
	
	/**
	 * Prompts the user for the name of a health professional to replace, as well as the details of the one to
	 * replace it with, then performs the replacement
	 */
	private void edit()
	{
		String oldName;
		while(true)
		{
			System.out.println("Enter the name of the health professional you want to replace");
			oldName= scanner.nextLine();
			if(!dataStore.containsProfessional(oldName)) 
				System.out.println("A professional with this name doesn't exist. Please try again");
			else break;
		}
		
		String newName;
		while(true)
		{
			System.out.println("Enter the name of the health professional you want to replace it with");
			newName= scanner.nextLine();
			if(dataStore.containsProfessional(newName)) 
				System.out.println("A professional with this name already exists. Please try again");
			else break;
		}
		
		System.out.println("Enter the profession of the health professional you want to replace it with");
		String profession= scanner.nextLine();
		System.out.println("Enter the work location of the health professional you want to replace it with");
		String workLocation= scanner.nextLine();
		
		dataStore.editEntry(oldName, new HealthProfessional(newName, profession, workLocation));
		
	}
	
	/**
	 * Prompts the user for the details of a health professional and deletes it
	 */
	private void delete()
	{
		String name;
		while(true)
		{
			System.out.println("Enter the name of the health professional you want to delete");
			name= scanner.nextLine();
			if(!dataStore.containsProfessional(name)) 
				System.out.println("A professional with this name doesn't exist. Please try again");
			else break;
		}
		
		dataStore.deleteEntry(name);
	}


	/**
	 * Prints the diaries of every professional
	 */
	private void print() 
	{
		for(HealthProfessional prof : dataStore.professionals())
		{
			System.out.println(prof.getName());
			for(diary.Entry entry : prof.getDiary().entries())
			{
				System.out.println("\t" + entry.getInfo());
			}
		}
		
	}
	
	/**
	 * Prompts the user for a list of names of health professionals, as well as two dates and times, and displays
	 * the available times between the specified instants for each professional
	 */
	private void search() 
	{	
		System.out.println("Enter the names of the health professionals you are looking for");
		Set<String> names = new HashSet<String>();
		while(true)
		{
			System.out.println("Enter the next name, or enter nothing to continue");
			String name= Menu.scanner.nextLine();
			if(name.equals("")) break;
			names.add(name);
		}

		System.out.println("Enter the end date of the appointments you're are looking up to");
		
		ZonedDateTime from = promptForDateTime
				("Enter the start date and time of the period you want to search",
				"Invalid input, please try again");
		
		ZonedDateTime to = promptForDateTime
				("Enter the end date and time of the period you want to search",
				"Invalid input, please try again");
		
		SortedMap<String, List<TimePeriod>> searchResults = dataStore.searchForAppointmentTimes(names, from, to);
		
		for(Map.Entry<String, List<TimePeriod>> availableTimes : searchResults.entrySet())
		{
			System.out.println(availableTimes.getKey() + ":");
			for(TimePeriod time : availableTimes.getValue())
			{
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
				System.out.println(formatter.format(time.getStart()) + " - " + formatter.format(time.getEnd()));
			}
		}
	}

	/**
	 * Prompts the user for the path to a file and saves the data store to it
	 */
	private void saveToFile() 
	{
		System.out.println("Enter the path name for the file you want to save");
		String path = scanner.nextLine();
		try(ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(path)))
		{
			writer.writeObject(dataStore);
			System.out.println("Data store saved successfully!");
		}
		catch(FileNotFoundException e)
		{
			System.out.println("The specified file was not found or couldn't be opened. The data store was not saved");
		}
		catch(IOException e)
		{
			System.out.println("An I/O error has occurred. The data store was not saved");
		}
	}

	/**
	 * Prompts the user for the path to a file and loads the data store from it
	 */
	private void loadFile() 
	{
		System.out.println("Enter the path name of the file you want to file");
		String path= scanner.nextLine();
		try(ObjectInputStream reader = new ObjectInputStream(new FileInputStream(path)))
		{
			dataStore = (DataStore) reader.readObject();
			System.out.println("data store loaded successfully!");
		}
		catch(FileNotFoundException e)
		{
			System.out.println("The specified file was not found or couldn't be opened. The data store was not loaded");
		}
		catch (IOException e)
		{
			System.out.println("An I/O error has occurred. The data store was not loaded");
		}
		catch (ClassNotFoundException e)
		{
			System.out.println("The file couldn't be read, and is probably corrupted. The data store was not loaded");
		}
	}
	
	/**
	 * prompts the user for the name of a health professional and opens their diary
	 */
	private void diaryMenu()
	{
		String name;
		while(true)
		{
			System.out.println("Enter the name of the health professional whose diary you want to access");
			name= scanner.nextLine();
			if(!dataStore.containsProfessional(name)) 
				System.out.println("A professional with this name doesn't exist. Please try again");
			else break;
		}
		new DiaryMenu(dataStore.getEntry(name).getDiary()).menu();
	}

	/**
	 * Runs tests when selected in order to test that the program functions properly
	 */
	private void runAutomatedTest() 
	{
		new Tester().test();
	}
}

