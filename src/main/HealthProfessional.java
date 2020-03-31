package main;

import java.io.Serializable;

/**
 * Represents a health professional and stores their diary
 * @author Alessandro Cavicchioli
 * @version 1.0
 */
public class HealthProfessional
implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String profession;
	private String workLocation;
	private Diary diary;
	
	/**
	 * Constructs a new HealthProfessional with the specified profession and workLocation, as well as an
	 * empty diary
	 * @param profession the profession to set
	 * @param workLocation the work location to set
	 */
	public HealthProfessional(String profession, String workLocation)
	{
		this.profession = profession;
		this.workLocation = workLocation;
	}
	
	/**
	 * @return the profession
	 */
	public String getProfession()
	{
		return profession;
	}
	/**
	 * @param profession the profession to set
	 */
	public void setProfession(String profession)
	{
		this.profession = profession;
	}
	/**
	 * @return the work location
	 */
	public String getWorkLocation()
	{
		return workLocation;
	}
	/**
	 * @param work location the work location to set
	 */
	public void setWorkLocation(String workLocation)
	{
		this.workLocation = workLocation;
	}
	/**
	 * @return the diary
	 */
	public Diary getDiary()
	{
		return diary;
	}
	
	
}
