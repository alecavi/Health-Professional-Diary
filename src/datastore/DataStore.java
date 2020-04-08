package datastore;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import datastore.SearchResultsBuilder.TimePeriod;

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
	 * Adds {@code entry} to the data store, or throws a {@code DuplicateNameException}
	 * if the data store already contains an entry with the same name as {@code entry}
	 * @param entry the entry to set
	 * @throws NullPointerException if {@code name} is {@code null}
	 * @throws DuplicateNameException if the data store already contains an entry associated to {@code name}
	 */
	public void addEntry(HealthProfessional entry)
	{
		String name = entry.getName();
		
		Objects.requireNonNull(name, "name may not be null");
		
		if(dataStore.containsKey(name)) 
			throw new DuplicateNameException("The datastore already contains the specified name");
		dataStore.put(name, entry);
	}
	
	/**
	 * Returns the entry associated with {@code name}
	 * @param name the name whose entry should be returned
	 * @return the entry associated with {@code name}
	 * @throws NullPointerException if {@code name} is {@code null}
	 */
	public HealthProfessional getEntry(String name)
	{
		Objects.requireNonNull(name, "name may not be null");
		return dataStore.get(name);
	}
	
	/**
	 * Removes the entry associated to {@code name} from the data store
	 * @param name the name whose entry should be removed
	 * @throws NullPointerException if {@code name} is {@code null}
	 * @throws MissingNameException if there is no entry with {@code oldName} as its name
	 */
	public void deleteEntry(String name)
	{
		Objects.requireNonNull(name, "name may not be null");
		if(! containsProfessional(name)) throw new MissingNameException("The specified entry doesn't exist");
		dataStore.remove(name);
	}

	/**
	 * Replaces the entry associated with {@code oldName} with {@code newEntry}
	 * @param name the name to associate {@code newEntry} to
	 * @param newEntry the entry to set
	 * @throws MissingNameException if there is no entry with {@code oldName} as its name 
	 * @throws DuplicateNameException if there is already an entry with the same name as {@code newEntry}
	 * @throws NullPointerException if either parameter is {@code null} 
	 */
	public void editEntry(String oldName, HealthProfessional newEntry)
	{
		if(containsProfessional(newEntry.getName())) 
			throw new DuplicateNameException("The datastore already contains the specified name");
		deleteEntry(oldName);
		addEntry(newEntry);
	}
	
	/**
	 * Returns {@code true} if {@code name} is already in this data store, and {@code false} if it isn't
	 * @param name the name to check
	 * @return {@code true} if {@code name} is already in this data store, and {@code false} if it isn't
	 */
	public boolean containsProfessional(String name)
	{
		return dataStore.containsKey(name);
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
	 * returns the results
	 * @param names a collection of names to search
	 * @param from the time and date the search should be started
	 * @param to the time and date the search should be ended
	 * @return a sorted map (in lexicographic order) associating each professional's name to a list 
	 * (in chronological order) of their available times
	 * @implNote For best performance, it is highly advisable for {@code names} to be a set, or otherwise have a fast
	 * {@code contains()} method
	 */
	public SortedMap<String, List<TimePeriod>> searchForAppointmentTimes(Collection<String> names, ZonedDateTime from, ZonedDateTime to)
	{
		SearchResultsBuilder out = new SearchResultsBuilder();
		
		for(HealthProfessional professional : professionalsFiltered(prof -> names.contains(prof.getName())))
		{
			String name = professional.getName();
			out.addProfessional(name);
			
			ZonedDateTime lastAppointmentEnd = from;
			for(diary.Entry appointment : professional.getDiary().entriesBetween(from, to))
			{
				if(!(lastAppointmentEnd == appointment.getStartDateTime()))
					out.addPeriodToProfessional(name, lastAppointmentEnd, appointment.getStartDateTime());
				lastAppointmentEnd = appointment.getEndDateTime();
			}
			out.addPeriodToProfessional(name, lastAppointmentEnd, to);
		}
		return out.getResults();
	}
}



















