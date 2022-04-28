/**
 * This class holds the functionality of a HomeworkTrap
 * for the "Hug the Angry Jarvis" game. A HomeworkTrap's main functionality
 * is to delay the player when stepped on
 * @author Brandon Borowiak
 */
public class HomeworkTrap implements Boardable
{
	/**
	 * This instance variable is of the board object that
	 * the homeworkTrap will be placed on
	 */
	private Board board;
	
	/**
	 * Constructor, sets the instance variable board to the passed in board
	 * @param board, board that the HomeworkTrap will be placed in
	 */
	public HomeworkTrap(Board board)
	{
		this.board = board;
	}//Constructor, HomeworkTrap(Board board)
	
	/**
	 * This method will return the visibility of a HomeworkTrap.
	 * Which is always false.
	 * @return false, because a HomeworkTrap is always invisible
	 */
	@Override
	public boolean isVisible() 
	{
		return false;
	}//isVisible()

	/**
	 * This method will take in a passed in boardable and return true or false
	 * depending on whether a HomeworkTrap can share the cell with the passed
	 * in boardable. If the passed in element is a player, then the HomeworkTrap
	 * will remove itself from the board and set the players delay.
	 * @return true or false, depending if the passed in element can share the square with the passed in element
	 */
	@Override
	public boolean share(Boardable elem) 
	{
		if(elem instanceof Player)
		{
			System.out.println("You've hit a homework trap!!");
			board.removeElement(this); //This section will remove the element from the board and then set the players delay to 5 seconds
			((Player) elem).setDelay(5000);
			return true;
		}//if(elem instanceof Player)
		if(elem instanceof Jarvis)
		{
			return true;
		}//if(elem instanceof Jarvis)
		else
		{
			return false;
		}//else
	}//share(Boardable elem)
	
	/**
	 * This method will return the string representation of a HomeworkTrap
	 * which is just " ". 
	 * @return " ", because this is how a HomeworkTrap is represented
	 */
	public String toString()
	{
		return " ";
	}//toString()
}//HomeworkTrap
