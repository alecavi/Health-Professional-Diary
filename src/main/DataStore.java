package main;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class DataStore
implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Map<String, HealthProfessional> dataStore = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
	
	/**
	 * Associates {@code entry} to {@code name} in the data store, replacing any previous value
	 * @param name the name to associate {@code entry} to
	 * @param entry the entry to set
	 */
	public void setEntry(String name, HealthProfessional entry)
	{
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
	 * Returns a {@link Set} view of the entries in the data store
	 * The set is backed by the data store, so changes to the data store are reflected in the set, and vice-versa. 
	 * If the data store is modified while an iteration over the set is in progress 
	 * (except through the iterator's own remove operation, or through the setValue operation on an entry 
	 * returned by the iterator) the results of the iteration are undefined. The set supports element removal, 
	 * which removes the corresponding entry from the data store, via the {@code Iterator.remove}, {@code Set.remove}, 
	 * {@code removeAll}, {@code retainAll} and {@code clear} operations. It does not support the {@code add} or
	 * {@code addAll} operations.
	 * @return a set view of the entries contained in the data store
	 */
	public Set<Map.Entry<String, HealthProfessional>> entrySet()
	{
		return dataStore.entrySet();
	}
}
