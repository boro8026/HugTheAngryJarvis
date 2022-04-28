/**
 * This interface is used to insure that all classes that implement it
 * hold these important methods
 * @author Brandon Borowiak
 */
public interface Boardable 
{
	/**
	 * This method will return the visibility of the different boardable elements
	 * @return
	 */
	public boolean isVisible();
	
	/**
	 * This method will return a boolean for whether the passed in element 
	 * can share the cell with the other element object
	 * @param elem, element to be compared to the boardable object of whether or not it can share the cell
	 * @return true or false depending on whether or not the element can share
	 */
	public boolean share(Boardable elem);
	
	/**
	 * This method will return the string of a boardable element
	 * @return string of the boardable element
	 */
	public String toString();
}//Boardable
