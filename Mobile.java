/**
 * This abstracts class is to ensure that all of the child classes
 * holds the methods of both interfaces: Boardable and runnable. It also
 * holds a constructor that allows all child classes to have access to.
 * @author Brandon Borowiak
 */
public abstract class Mobile implements Boardable, Runnable 
{
	/**
	 * This instance variable will be used in all the child classes
	 * and holds board used for the angry jarvis game.
	 */
	protected Board board;
	
	/**
	 * Constructor will be used in a super call from all the child classes
	 * @param board
	 */
	public Mobile(Board board)
	{
		this.board = board;
	}//Mobile(Board board)
	
	@Override
	public abstract void run();

	@Override
	public abstract boolean isVisible(); 

	@Override
	public abstract boolean share(Boardable elem);

	/**
	 * This method will be used to move all the different Mobile child classes
	 * on the board
	 */
	protected abstract void move();
}
