package diary;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import change.Change;

/**
 * @author Scott Maclennan
 * @version 1.0
 *
 */
public class Diary implements Serializable, Iterable<Entry>
{
	private static final long serialVersionUID = 1L;
	
	private Map<ZonedDateTime, Entry> diary = new TreeMap<>();
	private transient Deque<Change> history = new LinkedList<>();

	
	public void addEntry(Entry newEntry) 
	{
		
		diary.put(newEntry.getStartTime(), newEntry);
		
	}
	
	public void deleteEntry(Entry entry)
	{
		
		diary.remove(entry.getStartTime());
		
	}
	
	public void editEntry(Entry oldEntry, Entry newEntry)
	{
		diary.put(oldEntry.getStartTime(), newEntry);
	}
	
	public void getEntryByStartDateTime(ZonedDateTime startTime) 
	{
		
		diary.get(startTime);
		
	}
	
	
	
	@Override
	public Iterator<Entry> iterator() 
	{
		return diary.values().iterator();
	}
}
