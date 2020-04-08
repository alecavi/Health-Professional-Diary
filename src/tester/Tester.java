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
import diary.Entry;

public class Tester
{	
	public void test()
	{
		
	}
	
	private void DataStoreAdd()
	{
		DataStore dataStore = new DataStore();
		HealthProfessional testEntry = new HealthProfessional("John Doe", "Doctor", "Dundee");
		
		System.out.println("DataStore tests:");
		System.out.println();
		
		System.out.print("adding entry: ");
        dataStore.addEntry(testEntry);
        if(dataStore.getEntry("DoctorName") != testEntry) System.out.println("Failure!");
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
        
        System.out.println("adding entry with null name");
        try
        {
        	dataStore.addEntry(new HealthProfessional(null, "foo", "bar"));
        	System.out.println("Failure!");
        }
        catch(NullPointerException e)
        {
        	System.out.println("Success!");
        }
        
        System.out.println("adding entry with null profession");
        try
        {
        	dataStore.addEntry(new HealthProfessional("name", null, "bar"));
        }
        catch(NullPointerException e)
        {
        	System.out.println("Success!");
        }
        
        System.out.println("adding entry with null work location");
        try
        {
        	dataStore.addEntry(new HealthProfessional("name", "foo", null));
        }
        catch(NullPointerException e)
        {
        	System.out.println("Success!");
        }
    	   
        System.out.print("adding null entry: ");
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
    	
    	System.out.println("Editing entry");
    	dataStore.editEntry(testEntry.getName(), new HealthProfessional("name", "foo", "bar"));
    	if(dataStore.getEntry(testEntry.getName()).equals(testEntry)) System.out.println("Success!");
    	else System.out.println("Failure!");
    	
    	System.out.println("Editing nonexistent entry");
    	try
    	{
    		dataStore.editEntry("missing", testEntry);
    		System.out.println("Failure!");
    	}
    	catch(MissingNameException e)
    	{
    		System.out.println("Success!");
    	}
    	
    	System.out.println("Editing entry into duplicate name");
    	try
    	{
    		dataStore.addEntry(new HealthProfessional("dupe", "foo", "bar"));
    		dataStore.editEntry(testEntry.getName(), new HealthProfessional("dupe", "foo", "bar"));
    		System.out.println("Failure!");
    	}
    	catch(MissingNameException e)
    	{
    		System.out.println("Success!");
    	}
    	
    	System.out.println("Editing entry into duplicate name");
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
    	
    	System.out.println("Editing entry with null name");
    	try
    	{
    		dataStore.editEntry(null, testEntry);
    		System.out.println("Failure!");
    	}
    	catch(NullPointerException e)
    	{
    		System.out.println("Success!");
    	}
    	
    	System.out.println("Editing entry into null entry");
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
		
		System.out.println("Deleting entry");
		dataStore.deleteEntry(testEntry.getName());
		if(dataStore.containsProfessional(testEntry.getName())) System.out.println("Failure!");
		else System.out.println("Success!");
		
		System.out.println("Deleting nonexistent entry");
		try
		{
			dataStore.deleteEntry("missing");
			System.out.println("Failure!");
		}
		catch(MissingNameException e)
		{
			System.out.println("Success!");
		}
		
		System.out.println("Deleting null entry");
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
    
    private void dataStoreSearch()
    {
		DataStore dataStore = new DataStore();
		dataStore.addEntry(new HealthProfessional("James", "Doctor", "Dundee"));
		dataStore.addEntry(new HealthProfessional("Mordin", "Geneticist", "Omega"));
		ZonedDateTime start = ZonedDateTime.of(2185, 3, 15, 13, 50, 2, 0, ZoneId.systemDefault());
		ZonedDateTime end = ZonedDateTime.of(2185, 3, 16, 2, 14, 5, 0, ZoneId.systemDefault());
		
		ZonedDateTime start2 = ZonedDateTime.of(2020, 3, 15, 13, 50, 2, 0, ZoneId.systemDefault());
		ZonedDateTime end2 = ZonedDateTime.of(2020, 3, 16, 2, 14, 5, 0, ZoneId.systemDefault());
		
		
		dataStore.getEntry("Mordin").getDiary().addEntry(new Entry(start, end, "Bullet"));
		dataStore.getEntry("Mordin").getDiary().addEntry(new Entry(start2, end2, "Nothing"));
		
		SortedMap<String, List<TimePeriod>> expectedResults = new TreeMap<>();
		
		ZonedDateTime period1Start = ZonedDateTime.of(2185, 3, 13, 13, 50, 2, 0, ZoneId.systemDefault());
		ZonedDateTime period1End = ZonedDateTime.of(2185, 3, 15, 13, 50, 2, 0, ZoneId.systemDefault());

		ZonedDateTime period2Start = ZonedDateTime.of(2185, 3, 16, 2, 14, 5, 0, ZoneId.systemDefault());
		ZonedDateTime period2End = ZonedDateTime.of(2185, 3, 19, 2, 14, 5, 0, ZoneId.systemDefault());
		
		expectedResults.put("Mordin", List.of
				(new TimePeriod(period1Start, period1End), 
				 new TimePeriod(period2Start, period2End)));
		
		ZonedDateTime searchStart = ZonedDateTime.of(2185, 3, 13, 13, 50, 2, 0, ZoneId.systemDefault());
		ZonedDateTime searchEnd = ZonedDateTime.of(2185, 3, 19, 2, 14, 5, 0, ZoneId.systemDefault());
		
		SortedMap<String, List<TimePeriod>> actualResults =
				dataStore.searchForAppointmentTimes(List.of("Mordin", "James"), searchStart, searchEnd);
				
		if(expectedResults.equals(actualResults)) System.out.println("Success!");
		else System.out.println("Failure!");
    }
    
    private void dataStoreSave()
    {
    	DataStore dataStore = new DataStore();
    	HealthProfessional testEntry = new HealthProfessional("John Doe", "Doctor", "Dundee");
    	
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
		try(ObjectInputStream reader = new ObjectInputStream(new FileInputStream("test.ser")))
		{
			dataStore = (DataStore) reader.readObject();
		}
		catch(Exception e) //Catching Exception is almost always bad, but in this case _any_ exception is a failure
		{
			System.out.println("Failure!");
		}
		
		if(dataStore.containsProfessional("John Doe")) System.out.println("Success!");
		else System.out.println("Failure!");
    }
    
    private void diaryAdd()
    {
    	
    }
}































