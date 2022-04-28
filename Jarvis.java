import java.util.Random;

/**
 * This class holds the functionality of the Jarvis within the
 * "Hug the Angry Jarvis" game. Jarvis will randomly move about the
 * board and place HomeworkTraps every 6 moves
 * @author Brandon Borowiak
 */
public class Jarvis extends Mobile 
{
	/**
	 * This instance variable will be used to gather a random direction
	 * for Jarvis to move.
	 */
	private Random rand = new Random();
	
	/**
	 * This instance variable will keep track of how many moves
	 * since the last homeworkTrap was placed
	 */
	private int movesForHomeworkTrap;
	
	/**
	 * Constructor, imposes the mobile constructor
	 * @param board
	 */
	public Jarvis(Board board)
	{
		super(board);
		this.movesForHomeworkTrap = 0;
	}//Jarvis(Board board)
	
	/**
	 * This private method will lay a homework adjacent to
	 * jarvis. If the current direction is not a possible location,
	 * then the direction will then go clock-wise around jarvis until he finds
	 * a possible cell
	 */
	private synchronized void layTrap()
	{
		int row = board.getRow(this);
		int col = board.getColumn(this);
		int attemptedSpots = 0;
		int randomDirection = rand.nextInt(8);
		boolean hasPlaced = false;
		
		while(!hasPlaced && attemptedSpots < 8)//checks to see if a trap has been placed and if all the directions have been checked
		{
			if(randomDirection == 0)//place up and left
			{
				hasPlaced = board.placeElement(new HomeworkTrap(board), row - 1, col - 1);
			}//if(randomDirection == 0)
			
			if(randomDirection == 1)//place up
			{
				hasPlaced = board.placeElement(new HomeworkTrap(board), row - 1, col);
			}//if(randomDirection == 1)
			
			if(randomDirection == 2)//place up and right
			{
				hasPlaced = board.placeElement(new HomeworkTrap(board), row - 1, col + 1);
			}//if(randomDirection == 2)
			
			if(randomDirection == 3)// place right
			{
				hasPlaced= board.placeElement(new HomeworkTrap(board), row, col + 1);
			}//if(randomDirection == 3)
			
			if(randomDirection == 4)//place down and right
			{
				hasPlaced = board.placeElement(new HomeworkTrap(board), row + 1, col + 1);
			}//if(randomDirection == 4)
			
			if(randomDirection == 5)//place down
			{
				hasPlaced = board.placeElement(new HomeworkTrap(board), row + 1, col);
			}//if(randomDirection == 5)
			
			if(randomDirection == 6)//place down and left
			{
				hasPlaced = board.placeElement(new HomeworkTrap(board), row + 1, col - 1);
			}//if(randomDirection == 6)
			
			if(randomDirection == 7)//place left
			{
				hasPlaced = board.placeElement(new HomeworkTrap(board), row, col - 1);
			}//if(randomDirection == 7)
			
			attemptedSpots = attemptedSpots + 1;
			randomDirection = randomDirection + 1;
			
			if(randomDirection == 8)//makes sure the direction remains a viable direction
			{
				randomDirection = 0;
			}//if(randomDirection == 8)
			
		}//while(!hasPlaced && attemptedSpots < 8)
	}//layTrap()
	
	/**
	 * This method will randomly move Jarvis in a certain direction. It does
	 * this by generating a random direction and if that cell is not a viable square,
	 * will start checking clock-wise from that position
	 */
	protected void move()
	{
		int currDirection;
		boolean hasMoved = false;
		int numberAttempts = 1;
		
		currDirection = rand.nextInt(8);
		while(!hasMoved && numberAttempts < 9)//makes sure that jarvis hasn't moved, and will check to see if all directions have been tried
		{
			if(currDirection > 7)//This will make sure that the currDirection will always be a viable direction
			{
				currDirection = 0;
			}//if(currDirection > 7)
			
			switch(currDirection)
			{
			case 0://move up and left
				if(!board.move(Direction.UP_LEFT, this))//if he doesn't move, updates the variables
				{
					numberAttempts++;
					currDirection++;
				}//if(!board.move(Direction.UP_LEFT, this))
				else
				{
					hasMoved = true;
				}//else
				break;
				
			case 1://move up
				if(!board.move(Direction.UP, this))
				{
					numberAttempts++;
					currDirection++;
				}//if(!board.move(Direction.UP, this))
				else
				{
					hasMoved = true;
				}//else
				break;
				
			case 2://move up and right
				if(!board.move(Direction.UP_RIGHT, this))
				{
					numberAttempts++;
					currDirection++;
				}//if(!board.move(Direction.UP_RIGHT, this))
				else
				{
					hasMoved = true;
				}//else
				break;
				
			case 3://move right
				if(!board.move(Direction.RIGHT, this))
				{
					numberAttempts++;
					currDirection++;
				}//if(!board.move(Direction.RIGHT, this))
				else
				{
					hasMoved = true;
				}//else
				break;
				
			case 4://move down and right
				if(!board.move(Direction.DOWN_RIGHT, this))
				{
					numberAttempts++;
					currDirection++;
				}//if(!board.move(Direction.DOWN_RIGHT, this))
				else
				{
					hasMoved = true;
				}//else
				break;
				
			case 5://move down
				if(!board.move(Direction.DOWN, this))
				{
					numberAttempts++;
					currDirection++;
				}//if(!board.move(Direction.DOWN, this))
				else
				{
					hasMoved = true;
				}//else
				break;
				
			case 6://move down and left
				if(!board.move(Direction.DOWN_LEFT, this))
				{
					numberAttempts++;
					currDirection++;
				}//if(!board.move(Direction.DOWN_LEFT, this))
				else
				{
					hasMoved = true;
				}//else
				break;
				
			case 7://move left
				if(!board.move(Direction.LEFT, this))
				{
					numberAttempts++;
					currDirection++;
				}//if(!board.move(Direction.LEFT, this))
				else
				{
					hasMoved = true;
				}//else
				break;
			}//switch(currDirection)
		}//while(!hasMoved && numberAttempts < 9)
		
		this.movesForHomeworkTrap = this.movesForHomeworkTrap + 1;//updates how many moves since last homeworkTrap
		
		if(movesForHomeworkTrap == 6)//if the turns is six, then will lay a trap and set the moves back to zero
		{
			layTrap();
			this.movesForHomeworkTrap = 0;
		}//if(movesForHomeworkTrap == 6)
	}//move()
	
	/**
	 * This method will keep the Jarvis moving throughout the board at the same time as
	 * the other objects are. This is an implemented method within Runnable
	 */
	public void run()
	{
		while(!board.beenHugged())
		{
			this.move();
			try
			{
				Thread.sleep(500);
			}//try
			
			catch(InterruptedException e)
			{
				System.out.println("Some odd error occurred when trying to delay the player");
			}//catch(InterruptedException e)
			
		}//while(!board.beenHugged())
	}//run()
	
	/**
	 * This method will check to see if the passed in element is able to
	 * share a cell with a jarvis. The method will return true and false
	 * depending on the passed in boardable.
	 * @return True or False depending on the passed in boardable
	 */
	public boolean share(Boardable elem)
	{
		if(elem instanceof Player)
		{
			board.setHugged(true);
			return true;
		}//if(elem instanceof Player)
		if(elem instanceof HomeworkTrap)
		{
			return true;
		}//if(elem instanceof HomeworkTrap)
		else
		{
			return false;
		}//else
	}//share
	
	/**
	 * This method will return the visibility of Jarvis.
	 * Jarvis will always be invisible, so false will always be returned
	 * @return false, Jarvis is always invisible
	 */
	public boolean isVisible()
	{
		return false;
	}//isVisible()
	
	/**
	 * This method will return the string of the Jarvis object. Jarvis is represented
	 * as a "?"
	 * @return "?", this is how Jarvis is represented
	 */
	public String toString()
	{
		return "?";
	}//toString()
}//Jarvis
