package diary.change;

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

	public Addition(Entry newEntry)
	{
		this.entry = newEntry;
	}

	@Override
	public void apply()
	{		
		
	}

	@Override
	public void undo()
	{

	}
}
