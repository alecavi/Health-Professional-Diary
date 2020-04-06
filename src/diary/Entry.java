package diary;

import java.time.ZonedDateTime;

/**
 * Represents a single entry (appointment) in a diary
 * @author Scott Maclennan
 * @version 1.0
 */
public class Entry 
{
	private ZonedDateTime startDateTime;
	private ZonedDateTime endDateTime;
	private String treatmentType;
	
	/**
	 * Constructs a new entry with the specified parameters
	 * @param startDateTime the start date and time
	 * @param endDateTime the end date and time
	 * @param treatmentType the treatment type
	 */
	public Entry(ZonedDateTime startDateTime, ZonedDateTime endDateTime, String treatmentType) 
	{
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.treatmentType = treatmentType;
	}
    
	/**
	 * @return the start date and time
	 */
    public ZonedDateTime getStartDateTime()
    {
        return startDateTime;
    }
    
    /**
     * @return the end date and time
     */
    public ZonedDateTime getEndDateTime()
    {
        return endDateTime;
    }
    
    /**
     * @return the treatment type
     */
    public String getTreatmentType()
    {
        return treatmentType;
    }
    
    /**
     * Returns a human-readable string representation of the contents of this entry
     * @return a string representing the contents of this entry
     */
    public String getInfo() 
    {
    	return startDateTime + "-" + endDateTime + "," + treatmentType;
    }
    
}