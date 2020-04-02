package diary.change;

import java.time.ZonedDateTime;
import java.util.Map;

import diary.Entry;

/**
 * A change that can be applied and undone
 * @author Alessandro Cavicchioli
 * @version 1.0
 */
public interface Change
{
	/**
	 * Applies this change, performing the operation
	 */
	public void apply(Map<ZonedDateTime, Entry> diary);
	
	/**
	 * Undoes this change, restoring the system to its state prior to the operation
	 */
	public void undo(Map<ZonedDateTime, Entry> diary);
}
