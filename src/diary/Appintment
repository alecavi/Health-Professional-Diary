package diary;


/**
 * 
 * @author scott
 *
 */
public class Appointment 
{
	String date;
	double startTime;
	double endTime;
	String treatmentType;
	String info;
	
	Appointment root;
	Appointment left;
	Appointment right;
	
	public Appointment(String date ,double startTime ,double endTime ,String treatmentType) 
	{
        this.date = date;
        this.startTime = startTime;
        this.endTime= endTime;
        this.treatmentType= treatmentType;
	}
	
	public Appointment getAppointment() 
	{
		return root;
	}
	
    public String getDate()
    {
        return date;
    }
    
    public double getStartTime()
    {
        return startTime;
    }
    
    public double getEndTime()
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
