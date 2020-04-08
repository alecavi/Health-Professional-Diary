package datastore;

import java.io.Serializable;

import diary.Diary;

/**
 * Represents a health professional and stores their diary
 * @author Alessandro Cavicchioli
 * @version 1.0
 */
public class HealthProfessional
implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String profession;
	private String workLocation;
	private final Diary diary;
	
	/**
	 * Constructs a new HealthProfessional with the specified name, profession, and work location, as well as an
	 * empty diary
	 * @param name the name
	 * @param profession the profession
	 * @param workLocation the work location
	 */
	public HealthProfessional(String name, String profession, String workLocation)
	{
		this.name = name;
		this.profession = profession;
		this.workLocation = workLocation;
		this.diary = new Diary();
	}
	
	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
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
