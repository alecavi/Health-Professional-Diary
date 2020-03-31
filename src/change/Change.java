package change;

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
	public void apply();
	
	/**
	 * Undoes this change, restoring the system to its state prior to the operation
	 */
	public void undo();
}
