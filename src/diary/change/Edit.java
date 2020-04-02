package diary.change;

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
	public void apply()
	{		
		
	}

	@Override
	public void undo()
	{

	}
}
