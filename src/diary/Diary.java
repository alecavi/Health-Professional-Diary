package diary;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import diary.change.Addition;
import diary.change.Change;
import diary.change.Deletion;
import diary.change.Edit;

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
		Addition addition = new Addition(newEntry);
		addition.apply(diary);
		history.push(addition);
	}
	
	public void deleteEntry(Entry entry)
	{
		Deletion deletion = new Deletion(entry);
		deletion.apply(diary);
		history.push(deletion);
	}
	
	public void editEntry(Entry oldEntry, Entry newEntry)
	{
		Edit edit = new Edit(oldEntry, newEntry);
		edit.apply(diary);
		history.push(edit);
	}
	
	public void undo()
	{
		history.pop().undo(diary);
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
