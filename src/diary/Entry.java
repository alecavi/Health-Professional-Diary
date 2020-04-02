package diary;

/**
 * 
 * @author Scott
 *
 */
import java.util.Scanner;
import java.io.Serializable;
import java.time.ZonedDateTime;

public class Entry implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	String info;
	String date;
	ZonedDateTime startTime;
	ZonedDateTime endTime;
	String treatmentType;
	Boolean valid = false;
	
	Appointment root;
	Appointment testApp;
	static Entry testEntry;
	
	Scanner s = new Scanner(System.in);
	
	public Entry(String date, ZonedDateTime startTime, ZonedDateTime endTime, String treatmentType) 
	{
		testApp = new Appointment(date, startTime, endTime, treatmentType);
		testEntry = new Entry(date, startTime, endTime, treatmentType);
	}
}
