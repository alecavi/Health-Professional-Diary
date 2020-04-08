package diary;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.Objects;
import java.util.SortedMap;
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
implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private SortedMap<ZonedDateTime, Entry> diary = new TreeMap<>();
	
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
	 * @throws DuplicateDateTimeException if an entry already exists that starts at the same date and time
	 * @throws NullPointerException if {@code entry} is null
	 */
	public void addEntry(Entry entry) 
	{
		if(diary.containsKey(entry.getStartDateTime()))
			throw new DuplicateDateTimeException("An appointment starting at this date and time already exists");	
		applyChange(new Addition(entry));
	}
	
	/**
	 * Returns the entry associated with the specified start date and time
	 * @param startDateTime the start date and time of the entry
	 * @throws NullPointerException of {@code startDateTime} is null
	 */
	public Entry getEntry(ZonedDateTime startDateTime) 
	{	
		Objects.requireNonNull(startDateTime, "start date and time may not be null");
		return diary.get(startDateTime);	
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
	 * Deletes the entry starting at {@code startDateTime} from this diary
	 * @param startDateTime the start date and time of the entry to delete
	 */
	public void deleteEntry(ZonedDateTime startDateTime)
	{
		deleteEntry(diary.get(startDateTime));
	}
	
	/**
	 * Replaces {@code oldEntry} with {@code newEntry} in this diary
	 * @param oldEntry the entry to be replaced
	 * @param newEntry the entry to replace {@code oldEntry} with
	 * @throws MissingEntryException if {@code oldEntry} is not in this diary
	 * @throws DuplicateDateTimeException if an entry that starts at the same time as {@code newEntry} already exists
	 * @throws NullPointerException if either parameter is null
	 */
	public void editEntry(Entry oldEntry, Entry newEntry)
	{
		Objects.requireNonNull(oldEntry, "the entry to replace may not be null");
		Objects.requireNonNull(newEntry, "the new entry may not be null");
		
		if(! diary.containsKey(oldEntry.getStartDateTime()))
				throw new MissingEntryException("Cannot edit a nonexistent entry");
		else if(diary.containsKey(newEntry.getStartDateTime()))
			if(! oldEntry.getStartDateTime().equals(newEntry.getStartDateTime()))
				throw new DuplicateDateTimeException("An entry starting at the same date and time already exists");
		
		applyChange(new Edit(oldEntry, newEntry));
	}
	
	/**
	 * Replaces the entry starting at {@code oldStartDateTime} with {@code newEntry} in this diary
	 * @param oldStartDateTime the start date and time of the entry to be replaced
	 * @param newEntry the entry to replace {@code oldEntry} with
	 * @throws MissingEntryException if {@code oldEntry} is not in this diary
	 * @throws DuplicateDateTimeException if an entry that starts at the same time as {@code newEntry} already exists
	 * @throws NullPointerException if either parameter is null
	 */
	public void editEntry(ZonedDateTime oldStartDateTime, Entry newEntry)
	{
		Objects.requireNonNull(oldStartDateTime, "The start date and time may not be null");
		if(!diary.containsKey(oldStartDateTime)) throw new MissingEntryException("Cannot edit a nonexistent entry");
		editEntry(diary.get(oldStartDateTime), newEntry);
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
	 * Redoes the last undone operation, if any
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
	 * Returns whether this diary contains an entry that starts at {@code startDateTime}
	 * @param startDateTime the start date and time to check
	 * @return {@code true} if this diary contains such an entry, {@code false} otherwise
	 */
	public boolean containsEntry(ZonedDateTime startDateTime)
	{
		return diary.containsKey(startDateTime);
	}
	
	/**
	 * Returns a collection view of all diary entries between {@code from} (inclusive) and {@code to} (exclusive)
	 * @param from the start date and time
	 * @param to the end date and time
	 * @return a collection view of all diary entries between {@code from} (inclusive) and {@code to} (exclusive)
	 */
	public Collection<Entry> entriesBetween(ZonedDateTime from, ZonedDateTime to)
	{
		return diary.subMap(from, to).values();
	}
	
	/**
	 * Returns a collection view of all diary entries
	 * @return a collection view of all diary entries
	 */
	public Collection<Entry> entries()
	{
		return diary.values();
	}
}
