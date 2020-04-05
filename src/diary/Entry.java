package diary;

import java.time.ZonedDateTime;

/**
 * 
 * @author scott
 *
 */
public class Entry 
{
	private ZonedDateTime startDateTime;
	private ZonedDateTime endDateTime;
	private String treatmentType;
	
	public Entry(ZonedDateTime startDateTime, ZonedDateTime endDateTime, String treatmentType) 
	{
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.treatmentType = treatmentType;
	}
    
    public ZonedDateTime getStartDateTime()
    {
        return startDateTime;
    }
    
    public ZonedDateTime getEndDateTime()
    {
        return endDateTime;
    }
    
    public String getTreatmentType()
    {
        return treatmentType;
    }
    
    public String getInfo() 
    {
    	return startDateTime + " " + endDateTime + " " + treatmentType;
    }
    
}