package diary.change;

import java.time.ZonedDateTime;
import java.util.Map;

import diary.Entry;

/**
 * A change that can be applied to edit an entry and undone to delete it to its previous state
 * @author Alessandro Cavicchioli
 * @version 1.0
 */
public class Addition 
implements Change
{
	private Entry entry;

	public Addition(Entry entry)
	{
		this.entry = entry;
	}

	@Override
	public void apply(Map<ZonedDateTime, Entry> diary)
	{		
		diary.put(entry.getStartTime(), entry);
	}

	@Override
	public void undo(Map<ZonedDateTime, Entry> diary)
	{
		diary.remove(entry.getStartTime());
	}
}
