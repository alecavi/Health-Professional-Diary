package tester;

import main.DataStore;
import main.DuplicateNameException;
import main.HealthProfessional;

public class Tester
{
	private static DataStore dataStore = new DataStore();
	
	public static void main(String[] args)
	{
		HealthProfessional testEntry = new HealthProfessional("John Doe", "Doctor", "Dundee");
		
		System.out.print("Test adding a name: ");
        dataStore.addEntry("DoctorName", testEntry);
        if(dataStore.getEntry("DoctorName") != testEntry) System.out.println("Failure!");
        else System.out.println("Success!");
        
        System.out.print("Test adding the same name again: ");
        try
        {
        	dataStore.addEntry("DoctorName", testEntry);
        	System.out.println("Failure!");
        }
        catch(DuplicateNameException e)
        {
        	System.out.println("Success!");
        }
        
        System.out.print("Test adding null entry: ");
        try
        {
        	dataStore.addEntry("NewName", null);
        	System.out.println("Failure!");
        }
        catch(NullPointerException e)
        {
        	System.out.println("Success!");
        }
    }
}
