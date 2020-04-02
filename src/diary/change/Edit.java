package diary.change;

import java.time.ZonedDateTime;
import java.util.Map;

import diary.Entry;

/**
 * A change that can be applied to edit an entry and undone to restore it to its previous state
 * @author Alessandro Cavicchioli
 * @version 1.0
 */
public class Edit 
implements Change
{
	private Entry oldEntry;
	private Entry newEntry;

	public Edit(Entry oldEntry, Entry newEntry)
	{	
		this.oldEntry = oldEntry;
		this.newEntry = newEntry;
	}

	@Override
	public void apply(Map<ZonedDateTime, Entry> diary)
	{		
		diary.remove(oldEntry.getStartTime());
		diary.put(newEntry.getStartTime(), newEntry);
	}

	@Override
	public void undo(Map<ZonedDateTime, Entry> diary)
	{
		diary.remove(newEntry.getStartTime());
		diary.put(oldEntry.getStartTime(), oldEntry);
	}
}
