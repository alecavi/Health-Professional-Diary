package main;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import main.SearchResultsBuilder.TimePeriod;

/**
 * A data store containing the data of multiple health professionals
 * @author Alessandro Cavicchioli
 * @version 1.0
 */
public class DataStore
implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private final SortedMap<String, HealthProfessional> dataStore = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
	
	/**
	 * Creates an empty dataStore
	 */
	public DataStore() {}
	
	/**
	 * Associates {@code entry} to {@code name} in the data store, replacing any previous value
	 * @param name the name to associate {@code entry} to
	 * @param entry the entry to set
	 * @throws NullPointerException if {@code entry} is {@code null} 
	 */
	public void setEntry(String name, HealthProfessional entry)
	{
		if(entry == null) throw new NullPointerException("Entry may not be null");
		dataStore.put(name, entry);
	}
	
	/**
	 * Returns the entry associated with {@code name}
	 * @param name the name whose entry should be returned
	 * @return the entry associated with {@code name}
	 */
	public HealthProfessional getEntry(String name)
	{
		return dataStore.get(name);
	}
	
	/**
	 * Associates {@code entry} to {@code name} in the data store, or throws a {@code DuplicateNameException}
	 * if the data store already contains an entry associated to that name
	 * @param name the name to associate {@code entry} to
	 * @param entry the entry to set
	 * @throws DuplicateNameExcepion if the data store already contains an entry associated to {@code name}
	 */
	public void addEntry(String name, HealthProfessional entry)
	{
		if(dataStore.containsKey(name)) 
			throw new DuplicateNameException("The datastore already contains the specified name");
		else setEntry(name, entry);
	}
	
	/**
	 * Removes the entry associated to {@code name} from the data store
	 * @param name the name whose entry should be removed
	 */
	public void deleteEntry(String name)
	{
		dataStore.remove(name);
	}
	
	/**
	 * Returns a list of all health professionals in this data store. The list is in the same order as the data store
	 * itself.
	 * @return an unmodifiable list of all health professionals in this data store
	 */
	public List<HealthProfessional> professionals()
	{
		return dataStore.values().stream().collect(Collectors.toUnmodifiableList());
	}
	
	/**
	 * Returns a list of all health professionals who match {@code filter}. The list is in the same order as
	 * the dataStore itself.
	 * @param filter a {@link Predicate} to use as the filter. Only professionals matching the predicate will be in the
	 * output
	 * @return an unmodifiable list of all professionals with a name in {@code names}
	 */
	public List<HealthProfessional> professionalsFiltered(Predicate<? super HealthProfessional> filter)
	{
		return dataStore.values().stream()
			.filter(filter)
			.collect(Collectors.toUnmodifiableList());
	}
	
	/**
	 * Searches the diaries of all professionals specified by {@code names} between {@code from} and {@code to} and
	 * returns a {@link SearchResults} instance containing the times when each professionals is available 
	 * @param names a collection of names to search
	 * @param from the time and date the search should be started
	 * @param to the time and date the search should be ended
	 * @return a {@code SearchResults} instance containing the times when each professional is available
	 * @implNote For best performance, it is highly advisable for {@code names} to be a set
	 */
	public SortedMap<String, List<TimePeriod>> searchForAppointmentTimes(Collection<String> names, ZonedDateTime from, ZonedDateTime to)
	{
		SearchResultsBuilder out = new SearchResultsBuilder();
		
		for(HealthProfessional professional : professionalsFiltered(prof -> names.contains(prof.getName())))
		{
			String name = professional.getName();
			out.addProfessional(name);
			
			ZonedDateTime lastAppointmentEnd = from;
			for(diary.Entry appointment : professional.getDiary())
			{
				if(appointment.getStartDateTime().isBefore(from)) continue;
				else if(appointment.getStartDateTime().isAfter(to)) break;
				else
				{
					out.addPeriodToProfessional(name, lastAppointmentEnd, appointment.getStartDateTime());
					lastAppointmentEnd = appointment.getEndDateTime();
				}
			}
			out.addPeriodToProfessional(name, lastAppointmentEnd, to);
		}
		return out.getResults();
	}
}



















