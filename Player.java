/**
 * This class holds the player object, which the user will use to
 * play the catch the angry jarvis game. The player will interact with
 * the game through a move method
 */
import java.io.IOException;
import java.util.Scanner;

public class Player extends Mobile 
{
	/**
	 * This instance variable will gain the users input on which direction
	 * he or she would like to move.
	 */
	private Scanner input = new Scanner(System.in);
	
	/**
	 * This instance variable is used to delay the user's thread
	 * if the player hits a homework trap on the board
	 */
	private long delayTime;
	
	/**
	 * Constructor, calls the mobile super constructor, and places the player at 0,0 on the board
	 * @param board
	 */
	public Player(Board board)
	{
		super(board);
		board.placeElement(this, 0, 0);
	}//Player(Board board)
	
	/**
	 * This method holds the functionality of the stylus moving through
	 * the board. Using "q,w,e,a,d,z,x,c" will move the stylus in a certain
	 * direction. The method will return true if there was a valid move inputted
	 * by the user, and false otherwise.
	 * @return true or false, depending if the users input is a valid move or not
	 */
	protected void move()
	{
		String direction = input.nextLine();
		switch(direction)
		{
		case "w"://Move up
			board.move(Direction.UP,this);
			break;
			
		case "x"://Move down
			board.move(Direction.DOWN, this);
			break;
			
		case "a"://Move left
			board.move(Direction.LEFT, this);
			break;
			
		case "d"://Move right
			board.move(Direction.RIGHT, this);
			break;
			
		case "q"://Move up and left
			board.move(Direction.UP_LEFT, this);
			break;
			
		case "e"://Move up and right
			board.move(Direction.UP_RIGHT, this);
			break;
			
		case "z"://Move down and left
			board.move(Direction.DOWN_LEFT, this);
			break;
			
		case "c"://Move down and right
			board.move(Direction.DOWN_RIGHT, this);
			break;
		}//switch(direction)
		board.printBoard();
	}//move()
	
	/**
	 * This will continue the player interacting with the game until the
	 * player has caught the angry jarvis
	 */
	public void run()
	{
		while(!board.beenHugged())
		{
			try 
			{
				this.delay();
			}//try
			catch (IOException e){}
			
			this.move();
		}//while(!board.gbeenHugged())
	}//run()
	
	/**
	 * This method will set the delayTime instance variable
	 * to the passed in long variable
	 * @param time, amount of time that the player will be delayed for
	 */
	public void setDelay(long time)
	{
		this.delayTime = time;
	}//setDelay(long time)
	
	/**
	 * This method will delay the player, so when the player
	 * hits a homework trap, they won't be able to move. The delay
	 * time will be set back to zero after the delay has been served
	 * @throws IOException
	 */
	private void delay() throws IOException
	{
		try
		{
			Thread.sleep(this.delayTime);
			while(System.in.available() > 0)
			{
				int buffer = System.in.available();
				byte x[] = new byte[buffer];
				System.in.read(x);
			}//while(System.in.available() > 0)
		}//try
		
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}//catch(InterruptedException e)
		
		this.delayTime = 0;
	}//delay()
	
	/**
	 * This method will check to see if the player can share the cell
	 * will the passed in element. The player can never share the cell,
	 * so false is always returned
	 * @return false because the player can never share a cell
	 */
	public boolean share(Boardable elem)
	{
		return false;
	}//share(Boardable elem)
	
	/**
	 * This method will return the visibility of the player. The player
	 * is always visible so true is returned.
	 * @return True, because the player is always visible
	 */
	public boolean isVisible()
	{
		return true;
	}//isVisible()
	
	/**
	 * This method returns the string of the player
	 * object. The player is represented with a *.
	 * @return "*", this is the players emblem
	 */
	public String toString()
	{
		return "*";
	}//toString()
}//Player
