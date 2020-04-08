package tester;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import datastore.DataStore;
import datastore.DuplicateNameException;
import datastore.HealthProfessional;
import datastore.MissingNameException;
import datastore.SearchResultsBuilder.TimePeriod;
import diary.Diary;
import diary.DuplicateDateTimeException;
import diary.Entry;
import diary.MissingEntryException;

public class Tester
{	
	public void test()
	{
		System.out.println("DataStore tests:");
		System.out.println();
		
		dataStoreAdd();
		dataStoreEdit();
		dataStoreDelete();
		dataStoreSave();
		dataStoreLoad();
		
		System.out.println();
		System.out.println("Diary tests:");
		System.out.println();
		
		diaryAdd();
		diaryEdit();
		diaryDelete();
		diaryUndoRedo();
	}
	
	private void dataStoreAdd()
	{
		DataStore dataStore = new DataStore();
		HealthProfessional testEntry = new HealthProfessional("John Doe", "Doctor", "Dundee");
		
		System.out.print("adding entry: ");
        dataStore.addEntry(testEntry);
        if(dataStore.getEntry(testEntry.getName()) != testEntry) System.out.println("Failure!");
        else System.out.println("Success!");
        
        System.out.print("adding entry with duplicate name: ");
        try
        {
        	dataStore.addEntry(testEntry);
        	System.out.println("Failure!");
        }
        catch(DuplicateNameException e)
        {
        	System.out.println("Success!");
        }
        
        System.out.print("adding entry with null name: ");
        try
        {
        	dataStore.addEntry(new HealthProfessional(null, "foo", "bar"));
        	System.out.println("Failure!");
        }
        catch(NullPointerException e)
        {
        	System.out.println("Success!");
        }
        
        System.out.print("adding entry with null profession: ");
        try
        {
        	dataStore.addEntry(new HealthProfessional("name", null, "bar"));
        }
        catch(NullPointerException e)
        {
        	System.out.println("Success!");
        }
        
        System.out.print("Adding entry with null work location: ");
        try
        {
        	dataStore.addEntry(new HealthProfessional("name", "foo", null));
        }
        catch(NullPointerException e)
        {
        	System.out.println("Success!");
        }
    	   
        System.out.print("Adding null entry: ");
        try
        {
        	dataStore.addEntry(null);
        	System.out.println("Failure!");
        }
        catch(NullPointerException e)
        {
        	System.out.println("Success!");
        }
    }
	
    private void dataStoreEdit()
    {
    	DataStore dataStore = new DataStore();
    	HealthProfessional testEntry = new HealthProfessional("John Doe", "Doctor", "Dundee");
    	
    	dataStore.addEntry(testEntry);
    	
    	System.out.print("Editing entry: ");
    	HealthProfessional newEntry = new HealthProfessional("name", "foo", "bar");
    	dataStore.editEntry(testEntry.getName(), newEntry);
    	if(dataStore.getEntry(newEntry.getName()).equals(newEntry)) System.out.println("Success!");
    	else System.out.println("Failure!");
    	
    	System.out.print("Editing nonexistent entry: ");
    	try
    	{
    		dataStore.editEntry("missing", testEntry);
    		System.out.println("Failure!");
    	}
    	catch(MissingNameException e)
    	{
    		System.out.println("Success!");
    	}
    	
    	System.out.print("Editing entry into duplicate name: ");
    	try
    	{
    		dataStore.addEntry(new HealthProfessional("dupe", "foo", "bar"));
    		dataStore.editEntry(testEntry.getName(), new HealthProfessional("dupe", "foo", "bar"));
    		System.out.println("Failure!");
    	}
    	catch(DuplicateNameException e)
    	{
    		System.out.println("Success!");
    	}
    	
    	System.out.print("Editing entry into duplicate name: ");
    	try
    	{
    		dataStore.addEntry(new HealthProfessional("dupe", "foo", "bar"));
    		dataStore.editEntry(testEntry.getName(), new HealthProfessional("dupe", "foo", "bar"));
    		System.out.println("Failure!");
    	}
    	catch(DuplicateNameException e)
    	{
    		System.out.println("Success!");
    	}
    	
    	System.out.print("Editing entry with null name: ");
    	try
    	{
    		dataStore.editEntry(null, testEntry);
    		System.out.println("Failure!");
    	}
    	catch(NullPointerException e)
    	{
    		System.out.println("Success!");
    	}
    	
    	System.out.print("Editing entry into null entry: ");
    	try
    	{
    		dataStore.editEntry("somename", null);
    		System.out.println("Failure!");
    	}
    	catch(NullPointerException e)
    	{
    		System.out.println("Success!");
    	}
    }
    
    private void dataStoreDelete()
    {
		DataStore dataStore = new DataStore();
		HealthProfessional testEntry = new HealthProfessional("John Doe", "Doctor", "Dundee");
		
		dataStore.addEntry(testEntry);
		
		System.out.print("Deleting entry: ");
		dataStore.deleteEntry(testEntry.getName());
		if(dataStore.containsProfessional(testEntry.getName())) System.out.println("Failure!");
		else System.out.println("Success!");
		
		System.out.print("Deleting nonexistent entry: ");
		try
		{
			dataStore.deleteEntry("missing");
			System.out.println("Failure!");
		}
		catch(MissingNameException e)
		{
			System.out.println("Success!");
		}
		
		System.out.print("Deleting null entry: ");
		try
		{
			dataStore.deleteEntry(null);
			System.out.println("Failure!");
		}
		catch(NullPointerException e)
		{
			System.out.println("Success!");
		}
    }
    
    private void dataStoreSave()
    {
    	DataStore dataStore = new DataStore();
    	HealthProfessional testEntry = new HealthProfessional("John Doe", "Doctor", "Dundee");
    	dataStore.addEntry(testEntry);
    	
    	System.out.print("Saving: ");
		try(ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream("test.ser")))
		{
			writer.writeObject(dataStore);
			System.out.println("Success!");
		}
		catch(IOException e)
		{
			System.out.println("Failure!");
		}
    }
    
    private void dataStoreLoad()
    {
    	DataStore dataStore;
    	System.out.print("Loading: ");
		try(ObjectInputStream reader = new ObjectInputStream(new FileInputStream("test.ser")))
		{
			dataStore = (DataStore) reader.readObject();
			if(dataStore.containsProfessional("John Doe")) System.out.println("Success!");
			else System.out.println("Failure!");
		}
		catch(Exception e) //Catching Exception is almost always bad, but in this case _any_ exception is a failure
		{
			System.out.println("Failure!");
		}
    }
    
    /**
     * Creates a ZonedDateTime with the year set to {@code num} and everything else set to 1, for ease of testing
     * @param num the number to use
     * @return the new ZonedDateTime
     */
    private static ZonedDateTime time(int num)
    {
    	return ZonedDateTime.of(num, 1, 1, 1, 1, 1, 1, ZoneId.systemDefault());
    }
    
    private void diaryAdd()
    {
    	Diary diary = new Diary();
    	Entry testEntry = new Entry(time(1), time(2), "treatmentType");
    	
    	System.out.print("Adding entry: ");
    	diary.addEntry(testEntry);
    	if(diary.containsEntry(testEntry.getStartDateTime())) System.out.println("Success!");
    	else System.out.println("Failure!");
    	
    	System.out.print("Adding entry with duplicate start time and date: ");
    	try
    	{
    		diary.addEntry(new Entry(testEntry.getStartDateTime(), time(999), "treatmentType"));
    		System.out.println("Failure!");
    	}
    	catch(DuplicateDateTimeException e)
    	{
    		System.out.println("Success!");
    	}
    	
    	System.out.print("Adding null entry: ");
    	try
    	{
    		diary.addEntry(null);
    		System.out.println("Failure!");
    	}
    	catch(NullPointerException e)
    	{
    		System.out.println("Success!");
    	}
    }
    
    private void diaryEdit()
    {
    	Diary diary = new Diary();
    	ZonedDateTime startDateTime = time(1);
    	Entry testEntry = new Entry(startDateTime, time(2), "treatmentType");
    	
    	diary.addEntry(testEntry);
    	
    	System.out.print("Editing entry: ");
    	Entry newEntry = new Entry(startDateTime, time(3), "foobar");
    	diary.editEntry(startDateTime, newEntry);
    	if(diary.getEntry(startDateTime).getTreatmentType().equals("foobar"))
    		System.out.println("Success!");
    	else System.out.println("Failure!");
    	
    	System.out.print("Editing nonexistent entry: ");
    	try
    	{
    		diary.editEntry(time(999), testEntry);
    		System.out.println("Failure!");
    	}
    	catch(MissingEntryException e)
    	{
    		System.out.println("Success!");
    	}
    	
    	System.out.print("Editing entry into duplicate time: ");
    	try
    	{
    		diary.addEntry(new Entry(time(50), time(51), "dupe"));
    		diary.editEntry(time(50), testEntry);
    		System.out.println("Failure!");
    	}
    	catch(DuplicateDateTimeException e)
    	{
    		System.out.println("Success!");
    	}
    	
    	System.out.print("Editing entry into null time: ");
    	try
    	{
    		diary.addEntry(new Entry(time(60), time(61), "dupe"));
    		diary.editEntry(time(60), new Entry(null, time(61), "error"));
    		System.out.println("Failure!");
    	}
    	catch(NullPointerException e)
    	{
    		System.out.println("Success!");
    	}
    	
    	System.out.print("Editing entry into null entry: ");
    	try
    	{
    		diary.editEntry(time(60), null);
    		System.out.println("Failure!");
    	}
    	catch(NullPointerException e)
    	{
    		System.out.println("Success!");
    	}
    }
    
    private void diaryDelete()
    {
    	Diary diary = new Diary();
    	ZonedDateTime startDateTime = time(1);
    	Entry testEntry = new Entry(startDateTime, time(2), "treatmentType");
    	
    	diary.addEntry(testEntry);
    	
    	System.out.print("Deleting entry: ");
    	diary.deleteEntry(startDateTime);
    	if(diary.containsEntry(startDateTime)) System.out.println("Failure!");
    	else System.out.println("Success!");
    }
    
    private void diaryUndoRedo()
    {
    	Diary diary = new Diary();
    	ZonedDateTime startDateTime = time(1);
    	Entry testEntry = new Entry(startDateTime, time(2), "treatmentType");
    	
    	diary.addEntry(testEntry);
    	
    	System.out.print("Undoing entry addition: ");
    	diary.undo();
    	if(diary.containsEntry(startDateTime)) System.out.println("Failure!");
    	else System.out.println("Success!");
    	
    	System.out.print("Redoing entry addition: ");
    	diary.redo();
    	if(diary.containsEntry(startDateTime)) System.out.println("Success!");
    	else System.out.println("Failure!");
    	
    	Entry newEntry = new Entry(time(3), time(4), "edit");
    	diary.editEntry(startDateTime, newEntry);
    	
    	System.out.print("Undoing entry edit: ");
    	diary.undo();
    	if(diary.containsEntry(startDateTime)) System.out.println("Success!");
    	else System.out.println("Failure!");
    	
    	System.out.print("Redoing entry edit: ");
    	diary.redo();
    	if(diary.containsEntry(time(3))) System.out.println("Success!");
    	else System.out.println("Failure!");
    	
    	diary.addEntry(new Entry(time(5), time(6), "delete"));
    	diary.deleteEntry(time(5));
    	
    	System.out.print("Undoing entry deletion: ");
    	diary.undo();
    	if(diary.containsEntry(time(5))) System.out.println("Success!");
    	else System.out.println("Failure!");
    	
    	System.out.print("Redoing entry deletion: ");
    	diary.redo();
    	if(diary.containsEntry(time(5))) System.out.println("Failure!");
    	else System.out.println("Success!");
    }
}