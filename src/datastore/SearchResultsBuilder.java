package datastore;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * A utility class used by  {@link DataStore#searchForAppointmentTimes} to construct its return value
 * @author Alessandro Cavicchioli
 * @version 1.0
 */
public class SearchResultsBuilder
{
	private SortedMap<String, List<TimePeriod>> searchResults = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
	
	/**
	 * Adds a professional to this results object, with an empty list of time periods
	 * @param name the name of the professional
	 * @throws DuplicateNameException if {@code name} is already in use
	 */
	public void addProfessional(String name)
	{
		if(searchResults.containsKey(name)) throw new DuplicateNameException("Name is already in use");
		searchResults.put(name, new ArrayList<>());
	}
	
	/**
	 * Adds a time period, defined as the time between {@code start} and {@code end} (both inclusive), to the list
	 * associated with {@code name}
	 * @param name the name of the professional
	 * @param start the start of the time period
	 * @param end the end of the time period
	 */
	public void addPeriodToProfessional(String name, ZonedDateTime start, ZonedDateTime end)
	{
		List<TimePeriod> appointments = searchResults.get(name);
		if(appointments == null) throw new IllegalArgumentException("Professional name not in list");
		appointments.add(new TimePeriod(start, end));
	}
	
	/**
	 * Returns the search results constructed by this builder
	 * @return the search results
	 */
	public SortedMap<String, List<TimePeriod>> getResults()
	{
		return searchResults;
	}
	
	/**
	 * Stores two dateTimes, defining a time period between them
	 * @author Alessandro Cavicchioli
	 * @version 1.0
	 */
	public static class TimePeriod
	{
		private final ZonedDateTime start;
		private final ZonedDateTime end;
		
		/**
		 * Constructs a new TimePeriod instance with the specified start and end times
		 * @param start
		 * @param end
		 */
		private TimePeriod(ZonedDateTime start, ZonedDateTime end)
		{
			this.start = start;
			this.end = end;
		}

		/**
		 * @return the start
		 */
		public ZonedDateTime getStart()
		{
			return start;
		}

		/**
		 * @return the end
		 */
		public ZonedDateTime getEnd()
		{
			return end;
		}
		
		
	}
}
