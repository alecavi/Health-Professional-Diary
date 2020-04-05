package diary;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import diary.change.Addition;
import diary.change.Change;
import diary.change.Deletion;
import diary.change.Edit;

/**
 * A diary storing health professional appointments
 * @author Scott Maclennan
 * @version 1.0
 *
 */
public class Diary 
implements Serializable, Iterable<Entry>
{
	private static final long serialVersionUID = 1L;
	
	private Map<ZonedDateTime, Entry> diary = new TreeMap<>();
	
	private static final int HISTORY_LENGTH = 24;
	private transient Deque<Change> historyDone = new ArrayDeque<>(HISTORY_LENGTH);
	private transient Deque<Change> historyUndone = new ArrayDeque<>(HISTORY_LENGTH);
	
	/**
	 * Applies {@code change}, and adds it to the history of done operations, deleting the oldest one if necessary, then
	 * clears the history of undone operations
	 * @param change the change to apply
	 */
	private void applyChange(Change change)
	{
		change.apply(diary);
		if(historyDone.size() == HISTORY_LENGTH) historyDone.removeLast(); //Delete bottom of the stack
		historyDone.push(change);
		historyUndone.clear();
	}
	
	/**
	 * Adds {@code entry} to this diary
	 * @param entry the entry to add
	 * @throws DuplicateDateTimeException if the entry already exists
	 */
	public void addEntry(Entry entry) 
	{
		if(diary.containsKey(entry.getStartDateTime()))
			throw new DuplicateDateTimeException("An appointment starting at this date and time already exists");
		
		applyChange(new Addition(entry));
	}
	
	/**
	 * Deletes {@code entry} from this diary
	 * @param entry the entry to delete
	 */
	public void deleteEntry(Entry entry)
	{
		applyChange(new Deletion(entry));
	}
	
	/**
	 * Replaces {@code oldEntry} with {@code newEntry} in this diary
	 * @param oldEntry the entry to be replaced
	 * @param newEntry the entry to replace {@code oldEntry} with
	 * @throws MissingEntryException if {@code oldEntry} is not in this diary
	 */
	public void editEntry(Entry oldEntry, Entry newEntry)
	{
		if(! diary.containsKey(oldEntry.getStartDateTime()))
			throw new MissingEntryException("Cannot edit a nonexistent entry");
		applyChange(new Edit(oldEntry, newEntry));
	}
	
	/**
	 * Undoes the last operation performed, if any
	 * @return {@code true} if an operation was undone, {@code false} otherwise
	 */
	public boolean undo()
	{
		if(historyDone.size() == 0) return false;
		Change change = historyDone.pop();
		change.undo(diary);
		historyUndone.push(change);
		return true;
	}
	
	/**
	 * Redoes the last operation performed, if any
	 * @return {@code true} if an operation was redone, {@code false} otherwise
	 */
	public boolean redo()
	{
		if(historyUndone.size() == 0) return false;
		Change change = historyUndone.pop();
		change.apply(diary);
		historyDone.push(change);
		return true;
	}
	
	/**
	 * Returns the entry associated with the specified start date and time
	 * @param startDateTime the start date and time of the entry
	 */
	public void getEntryByStartDateTime(ZonedDateTime startDateTime) 
	{
		diary.get(startDateTime);	
	}
	
	/**
	 * Returns an iterator over the entries of this diary
	 * @return an iterator over the entries of this diary
	 */
	@Override
	public Iterator<Entry> iterator() 
	{
		return diary.values().iterator();
	}
}
