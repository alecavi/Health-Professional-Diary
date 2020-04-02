package diary;

import java.time.ZonedDateTime;

/**
 * 
 * @author scott
 *
 */
public class Entry 
{
	String date;
	ZonedDateTime startTime;
	ZonedDateTime endTime;
	String treatmentType;
	String info;
	
	public Entry(String date, ZonedDateTime startTime, ZonedDateTime endTime, String treatmentType) 
	{
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.treatmentType = treatmentType;
	}
	
    public String getDate()
    {
        return date;
    }
    
    public ZonedDateTime getStartTime()
    {
        return startTime;
    }
    
    public ZonedDateTime getEndTime()
    {
        return endTime;
    }
    
    public String getTreatmentType()
    {
        return treatmentType;
    }
    
    public String getInfo() 
    {
    	String info= date +" "+ startTime + " " + endTime + " " + treatmentType;
    	return info;
    }
    
}