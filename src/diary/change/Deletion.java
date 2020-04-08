package diary.change;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Objects;

import diary.Entry;

/**
 * A change that can be applied to delete an entry and undone to restore it
 * @author Alessandro Cavicchioli
 * @version 1.0
 */
public class Deletion 
implements Change
{
	private Entry entry;

	public Deletion(Entry entry)
	{
		Objects.requireNonNull(entry, "aaa");
		this.entry = entry;
	}

	@Override
	public void apply(Map<ZonedDateTime, Entry> diary)
	{		
		diary.remove(entry.getStartDateTime());
	}

	@Override
	public void undo(Map<ZonedDateTime, Entry> diary)
	{
		diary.put(entry.getStartDateTime(), entry);
	}
}