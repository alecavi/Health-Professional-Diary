package menu;

import static menu.Menu.promptForDateTime;
import static menu.Menu.scanner;

import java.time.ZonedDateTime;

import diary.Diary;
import diary.Entry;

/**
 * A menu to manipulate diaries
 * @author Scott Maclennan
 * @version 1.0
 */
public class DiaryMenu
{
	private Diary diary;
	
	/**
	 * Constructs a new DiaryMenu with {@code diary} as the diary to manipulate
	 * @param diary the diary this DiaryMenu will operate on
	 */
	public DiaryMenu(Diary diary)
	{
		this.diary = diary;
	}
	
	/**
	 * Prints a menu for the user to know what each command can do
	 */
	private static void printMenu()
	{
		System.out.println("add-	add an entry to this diary");
		System.out.println("delete-	delete an entry from this diary");
		System.out.println("edit- 	edit an entry in this diary");
		System.out.println("undo - 	undoes the last operation");
		System.out.println("redo -	redoes the last operation");
		System.out.println("back- 	return to the data store menu");
	}

	/**
	 * Runs the menu so that the program can take user input and perform the chosen tasks
	 */
	public void menu()
	{
		while(true)
		{
			printMenu();
			switch(scanner.nextLine().toLowerCase())
			{
			case "add":
				add();
				break;
			case "delete":
				delete();
				break;
			case "edit":
				edit();
				break;
			case "undo":
				diary.undo();
				break;
			case "redo":
				diary.redo();
				break;
			case "back":
				return;
			default:
				System.out.println("That is not a valid choice, please try again");
				break;
			}
		}
	}
	
	/**
	 * Prompts the user for the details of a diary entry and adds it
	 */
	private void add() 
	{	
		ZonedDateTime startDateTime;
		while(true)
		{
			startDateTime = promptForDateTime("Enter the start date and time of the appointment you want to add",
				"Invalid input. Please try again");
			
			if(diary.containsEntry(startDateTime))
				System.out.println("an appointment starting at that date and time already exists. Please try again");
			else break;
		}
		
		
		ZonedDateTime endDateTime = promptForDateTime("Enter the end date and time of the appointment you want to add",
				"Invalid input. Please try again");
						
			System.out.println("Enter the treatment type of the appointment you want to add");
			String treatmentType= scanner.nextLine();

			System.out.println(startDateTime);
			System.out.println(endDateTime);
			Entry newEntry= new Entry(startDateTime, endDateTime, treatmentType);
			
			diary.addEntry(newEntry);
	}
	
	/**
	 * Prompts the user for the start date and time of an entry in the diary and deletes the entry
	 */
	private void delete() 
	{	
		ZonedDateTime startDateTime = promptForDateTime("Enter the start date and time of the appointment you want to delete",
				"Invalid input. Please try again");
	
		diary.deleteEntry(startDateTime);	
	}
	
	/**
	 * Prompts the user for the start date and time of the entry to replace, as well as the details of
	 * the one to replace it with, then performs the replacement
	 */
	private void edit() 
	{	
			ZonedDateTime oldStartDateTime = promptForDateTime
					("Enter the start date and time of the appointment you want to replace",
					"Invalid input. Please try again");
			
			ZonedDateTime newStartDateTime = promptForDateTime
					("Enter the start date and time of the appointment you want to replace the old one with",
					"Invalid input. Please try again");
			
			ZonedDateTime newEndDateTime = promptForDateTime
					("Enter the end date and time of the appointment you want to replace the old one with",
					"Invalid input. Please try again");
			
			System.out.println("Enter the treatment type of the appointment you want to replace");
			String treatmentType = scanner.nextLine();
			
			Entry newEntry= new Entry(newStartDateTime, newEndDateTime, treatmentType);
			diary.editEntry(oldStartDateTime, newEntry);
	}
}
