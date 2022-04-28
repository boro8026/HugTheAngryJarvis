import java.util.Random;
import java.util.Scanner;

/**
 * This class will run the "Hug the Angry Jarvis" game
 * It will do this by creating the board (with user's input) and creating a 
 * Jarvis and player within the board.
 * @author Brandon Borowiak
 */
public class Game 
{
	/**
	 * This variable will be used to gather the user's desired size of the board
	 */
	private static Scanner userInput = new Scanner(System.in);
	
	public static void main(String[] args) 
	{
		int widthInput;
		int heightInput;
		int[] jarvisLocation;
		
		System.out.println("What would you like the height of the board to be?");
		heightInput = getUsersInput();
		
		System.out.println("What would you like the width of the board to be?");
		widthInput = getUsersInput();
		
		//Creates the different entities for the game
		Board board = new Board(heightInput,widthInput);
		
		Jarvis angryJarvis = new Jarvis(board);
		jarvisLocation = getJarvisStartingPosition(heightInput,widthInput);
		board.placeElement(angryJarvis,jarvisLocation[0],jarvisLocation[1]);
		
		Player p1 = new Player(board);
		board.placeElement(p1,0,0);
		
		//Creates the different threads between the player and jarvis
		Thread playerThread = new Thread(p1);
		Thread jarvisThread = new Thread(angryJarvis);
		
		board.printBoard();
		
		long start = System.currentTimeMillis();//start time for the game
			
		//Starts both threads of the objects
		jarvisThread.start();
		playerThread.start();
		
		while(!board.beenHugged()) {}// This is used just to continue the game until the player has caught Jarvis
		
		long stop = System.currentTimeMillis();//end time for the game
		
		//Final printing
		printFinish(start,stop);
	}//main

	/**
	 * This method will gather the user's input for the size of the height
	 * and width of the board.
	 * @return integer, of the height or width of the board
	 */
	private static int getUsersInput()
	{
		String enteredAmount = userInput.nextLine();
		while(!Game.validNumber(enteredAmount))
		{
			System.out.println("The value you entered is not a valid number. Height and width must be between 1 and 100 (inclusive)");
			System.out.println("Please enter another value.");
			enteredAmount = userInput.nextLine();
		}//while(!DrawingDriver.validNumber(enteredAmount))
		
		return Integer.parseInt(enteredAmount);
	}//getUsersInput()
	
	/**
	 * This method will check to see if the input string contains only digits. This will
	 * check if the string is empty and if the string contains a number longer than the
	 * max for an integer.
	 * @param strNumber, a string to be checked if it only contains digits
	 * @return true or false depending if strNumber passes the test for being an integer
	 */
	private static boolean isNumber(String strNumber)
	{
		if(strNumber.length() == 0)
		{
			return false;
		}//if(strNumber.length() == 0)
		
		for(int index = 0; index < strNumber.length(); index++)
		{
			if(strNumber.charAt(index) < '0'|| strNumber.charAt(index) > '9')// checks to see if each value character is a digit
			{
				return false;
			}//if(strNumber.charAt(index) < '0'|| strNumber.charAt(index) > '9')
			
		}//for(int index = 0; index < strNumber.length(); index++)
		
		if(strNumber.length() > 9)
		{
			return false;
		}//if(strNumber.length() > 9)
		
		return true;
	}//isNumber(String strNumber)
	
	/**
	 * This method will check to see if the inputed string
	 * is a valid number or not
	 * @param strNumber, a string to be checked if it is a number
	 * @return true or false, depending if the inputed string is a valid number or not
	 */
	private static boolean validNumber(String strNumber)
	{
		int number;
		if(isNumber(strNumber))
		{
			number = Integer.parseInt(strNumber);
			if(number > 100 || number < 1)
			{
				return false;
			}//if(number > 100 || number < 1)
			return true;
		}//if(isNumber(strNumber))
		return false;
	}//validNumber(String strNumber)
	
	/**
	 * This method will take the height and width of the board and generate two numbers that are within
	 * the bounds of those parameters. These numbers will then be returned in an integer array
	 * with the first being the height location and the second being the width location
	 * @param height, the height of the board
	 * @param width, the width of the board
	 * @return location, int array of the random height and random width locations
	 */
	private static int[] getJarvisStartingPosition(int height, int width)
	{
		int[] location = new int[2];
		Random rand = new Random();
		
		location[0] = rand.nextInt(height);
		location[1] = rand.nextInt(width);
		
		return location;
	}//getJarvisStartingPosition(int height, int width)
	
	
	/**
	 * This method will tell the user that they have hugged Jarvis. It is passed
	 * a start and stop time to figure out the grade the player has received.
	 * @param start, the time of when the user started the game
	 * @param stop, the time of when the user completed the game
	 */
	private static void printFinish(long start, long stop)
	{
		System.out.println();
		System.out.println("You have soothed the angry Jarvis");
	
		if(stop - start < 30000) 
		{
			System.out.println("Your grade: A");
		}//if under 30 seconds
	
		if(stop- start < 60000 && stop - start >= 30000) 
		{
			System.out.println("Your grade: B");
		}//if under a minute and above 30 seconds
	
		if(stop - start < 90000 && stop - start >= 60000) 
		{
			System.out.println("Your grade: C");
		}//if under a minute and 30 seconds and above a minute
	
		if(stop - start < 120000 && stop - start >= 90000) 
		{
			System.out.println("Your grade: D");
		}//if under two minutes and above a minute and 30 seconds
	
		if(stop - start >= 120000) 
		{
			System.out.println("Your grade: F. You suck, and probably wont be playing again");
		}//if above two minutes
	}//printFinish(long start, long stop)
	
	/**
	 * This method will make sure that the userInput Scanner
	 * will be closed
	 */
	public void finalize()
	{
		userInput.close();
	}//finalize()
}//Game
