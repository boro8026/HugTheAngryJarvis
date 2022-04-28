/**
 * This board class will hold the functionality of a board made
 * up of cells created by the nested class cells. Cells will hold 
 * multiple functionality for each cell of the board. A boardable interface is
 * what will each cell will contain.
 * 
 * @author Brandon Borowiak
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Board 
{
	private class Cell
	{
		/**
		 * This instance variable will hold an int referring to the row
		 * that this Cell will be located in the board
		 */
		private int row;
		
		/**
		 * This instance variable will hold an integer referring to the 
		 * column that this Cell will be located in the board
		 */
		private int col;
		
		/**
		 * This instance variable will contain a boolean which will hold
		 * the visibility of the cell (Whether or not the cell will be a "#" or not).
		 */
		private boolean isVisible;
		
		/**
		 * This instance variable will hold elements within each cell
		 */
		private List<Boardable> elements = new ArrayList<Boardable>();
		
		/**
		 * Constructor, creates the cell object with its' row and column and 
		 * initializing the isVisible instance variable to false.
		 * @param row the row within the board this cell sits
		 * @param col the column within the board this cell sits
		 */
		public Cell(int row, int col)
		{
			this.row = row;
			this.col = col;
			this.isVisible = false;
		}//Cell(int row, int col)
		
		/**
		 * This method will add an element to the cell. If
		 * the new element is visible, then the entire cell will become visible.
		 * @param elem
		 */
		public void addElement(Boardable elem)
		{
			elements.add(elem);
			if(elem.isVisible())
			{
				this.isVisible = true;
			}//if(elem.isVisible())
		}//addElement(Boardable elem)
		
		/**
		 * This method will remove an element from the instance variable:
		 * elements. Only if element is in elements
		 * @param elem, Boardable to be removed from the ArrayList
		 */
		public void removeElement(Boardable elem)
		{
			elements.remove(elem);
		}//removeElement(Boardable elem)
		
		/**
		 * This method will return a string based on a multiple of possibilities
		 * of a cell. If the cell is not visible, a "#" is returned. If the cell
		 * is visible and there are no elements in the cell, a " " is returned. Otherwise,
		 * the final elements of the instance variable will be returned.
		 * @return result, a string holding the possibilities to be printed
		 */
		public String toString()
		{
			String result;
			if(!this.isVisible)
			{
				result = "#";
			}//if(!this.isVisible)
			else if(elements.size() == 0 && this.isVisible)
			{
				result = " ";
			}//else if(elements.size() == 0 && this.isVisible)
			else
			{
				result = elements.get(elements.size() - 1).toString();
			}//else
			return result;
		}//toString()
	}//Cell Class
	
	/**
	 * This instance variable holds a two-dimensional array of cell.
	 * This will be the board that is created.
	 */
	public Cell[][] board;
	
	/**
	 * This instance variable holds the height of the board to be created
	 */
	private int height;
	
	/**
	 * This instance variable holds the width of the board to be created
	 */
	private int width;
	
	/**
	 * This instance variable will hold a certain cell, with a key as a certain type
	 * of element within that cell.
	 */
	private HashMap<Boardable,Cell> elementPlace = new HashMap<Boardable,Cell>();
	
	/**
	 * This instanve varible holds the boolean of whether or not Jarvis has been hugged
	 */
	private boolean hugged;
	
	/**
	 * Constructor, will create the board. Making sure that the height and width are within 1 - 100, and
	 * throwing an IllegalArgumentException if they are not. This constructor will create the size of 
	 * the board, and then will call a method to fill the board of cells
	 * @param height, the height of the board
	 * @param width, the width of the board
	 */
	public Board(int height, int width)
	{
		if(height < 1 || height > 100)
		{
			throw new IllegalArgumentException("The height must be between 1 and 100 (inclusive)");
		}//if(height < 1 || height > 100)
		this.height = height;
		
		if(width < 1 || width > 100)
		{
			throw new IllegalArgumentException("The width must be between 1 and 100 (inclusive)");
		}//if(widht < 1 || width > 100)
		this.width = width;
		
		board = new Cell[this.height][this.width];
		fillBoard();
	}//Board, Constructor
	
	/**
	 * Accessor method, returns the height instance variable
	 * @return the value of the height instance variable
	 */
	public int getHeight()
	{
		return this.height;
	}//getHeight()
	
	/**
	 * Accessor method, returns the width instance variable
	 * @return the value of the width instance variable
	 */
	public int getWidth()
	{
		return this.width;
	}//getWidth()
	
	
	/**
	 * This method will fill the board with cells according to the heigh and 
	 * width instance variables
	 */
	private void fillBoard()
	{
		for(int heightIndex = 0; heightIndex < this.height; heightIndex++)
		{
			for(int widthIndex = 0; widthIndex < this.width; widthIndex++)
			{
				board[heightIndex][widthIndex] = new Cell(heightIndex,widthIndex);
			}//for(int widthIndex = 0; widthIndex < this.width; widthIndex++)
			
		}//for(int heightIndex = 0; heightIndex < this.height; heightIndex++)
		
	}//fillBoard()
	
	
	/**
	 * This method will holds the functionality of the movement of an element
	 * through a board. It does this based on the different directions set in the
	 * Direction enum. This method will throw an IllegalArgumentException if the 
	 * passed element is not on the board. It will return false if the the move is not
	 * valid for the current cell, and it will return true if the element was, and did,
	 * move.
	 * @param dir, the direction that the passed element is to move
	 * @param elem, the element within the board that is to be moved
	 * @return true or false, based on whether the movement was v
	 */
	public synchronized boolean move(Direction dir, Boardable elem)
	{
		if(!elementPlace.containsKey(elem)) //This if will make sure that the elem is on the board
		{
			throw new IllegalArgumentException("That element is not on the board.");
		}//if(!elementPlace.containsKey(elem))
		
		Cell currCell = elementPlace.get(elem);//finds the current cell the element is in
		
		if(!validMove(dir,currCell))
		{
			return false;
		}//if(!validMove(dir,currCell))
		
		if(dir == Direction.UP)
		{
			if(checkSharing(board[currCell.row-1][currCell.col],elem))//Makes sure the element is able to move onto all the elements in the cell
			{
				currCell.removeElement(elem);
				elementPlace.remove(elem);
				placeElement(elem,currCell.row - 1, currCell.col);
				return true;
			}//if(checkSharing(board[currCell.row-1][currCell.col],elem))
		}//if(dir == Direction.UP)
		
		if(dir == Direction.DOWN)
		{
			if(checkSharing(board[currCell.row+1][currCell.col],elem))
			{
				currCell.removeElement(elem);
				elementPlace.remove(elem);
				placeElement(elem,currCell.row + 1, currCell.col);
				return true;
			}//if(checkSharing(board[currCell.row+1][currCell.col],elem))
		}//if(dir == Direction.DOWN)
		
		if(dir == Direction.LEFT)
		{
			if(checkSharing(board[currCell.row][currCell.col - 1],elem))
			{
				currCell.removeElement(elem);
				elementPlace.remove(elem);
				placeElement(elem, currCell.row, currCell.col - 1);
				return true;
			}//if(checkSharing(board[currCell.row][currCell.col - 1],elem))
		}//if(dir == Direction.LEFT)
		
		if(dir == Direction.RIGHT)
		{
			if(checkSharing(board[currCell.row][currCell.col + 1],elem))
			{
				currCell.removeElement(elem);
				elementPlace.remove(elem);
				placeElement(elem, currCell.row, currCell.col + 1);
				return true;
			}//if(checkSharing(board[currCell.row][currCell.col + 1],elem))
		}//if(dir == Direction.RIGHT)
		
		if(dir == Direction.UP_LEFT)
		{
			if(checkSharing(board[currCell.row-1][currCell.col -1],elem))
			{
				currCell.removeElement(elem);
				elementPlace.remove(elem);
				placeElement(elem, currCell.row - 1, currCell.col - 1);
				return true;
			}//if(checkSharing(board[currCell.row-1][currCell.col -1],elem))
		}//if(dir == Direction.UP_LEFT)
		
		if(dir == Direction.UP_RIGHT)
		{
			if(checkSharing(board[currCell.row-1][currCell.col + 1],elem))
			{
				currCell.removeElement(elem);
				elementPlace.remove(elem);
				placeElement(elem, currCell.row - 1, currCell.col + 1);
				return true;
			}//if(checkSharing(board[currCell.row-1][currCell.col + 1],elem))
		}//if(dir == Direction.UP_RIGHT)
		
		if(dir == Direction.DOWN_LEFT)
		{
			if(checkSharing(board[currCell.row+1][currCell.col-1],elem))
			{
				currCell.removeElement(elem);
				elementPlace.remove(elem);
				placeElement(elem, currCell.row + 1, currCell.col - 1);
				return true;
			}//if(checkSharing(board[currCell.row+1][currCell.col-1],elem))
		}//if(dir == Direction.DOWN_LEFT)
		
		if(dir == Direction.DOWN_RIGHT)
		{
			if(checkSharing(board[currCell.row+1][currCell.col+1],elem))
			{
				currCell.removeElement(elem);
				elementPlace.remove(elem);
				placeElement(elem, currCell.row + 1, currCell.col + 1);
				return true;
			}//if(checkSharing(board[currCell.row+1][currCell.col+1],elem))
		}//if(dir == Direction.DOWN_RIGHT)
		
		return false;
	}//move(Direction dir, Boardable elem)

	/**
	 * This method will place a passed in Boardable element at a 
	 * passed in row and column. If the row and column are out of bounds on
	 * the board, then false is returned. otherwise, the passed boardable
	 * will be placed in the row and column, and true is returned
	 * @param elem, boardable, an element that is to be placed at a location
	 * @param row, integer holding the row position to add the element
	 * @param col, integer hold the column position to add the element
	 * @return true or false, depending if the element was able to be added to the board
	 */
	public synchronized boolean placeElement(Boardable elem, int row, int col)
	{
		if(row < 0 || row > this.height-1)
		{
			return false;
		}//if(row < 0 || row > this.height-1)
		
		if(col < 0 || col > this.width-1)
		{
			return false;
		}//if(col < 0 || col > this.width-1)
		if(!checkSharing(board[row][col], elem))
		{
			return false;
		}
		board[row][col].addElement(elem);
		elementPlace.put(elem,board[row][col]);
		return true;
	}//placeElement(Boardable elem, int row, int col)
	
	
	/**
	 * This method will print the board by going through each cell and printing
	 * the toString() of each cell
	 */
	public synchronized void printBoard()
	{
		System.out.println();
		for(int index = 0; index < this.height; index++)
		{
			for(int index2 = 0; index2 < this.width; index2++)
			{
				if(index2 == this.width - 1)
				{
					System.out.println(board[index][index2]);
				}//if(index2 == this.width - 1)
				
				else
				{
					System.out.print(board[index][index2]);
				}//else
				
			}//for(int index2 = 0; index2 < this.width; index2++)
			
		}//for(int index = 0; index < this.height; index++)
	}//printBoard()
	
	
	/**
	 * This method will return false if the movement on the current Cell will
	 * go out of bounds of the board. It checks for every possible movement from the
	 * direction enum. It will return true if the movement is possible.
	 * @param dir, the direction that an element wishes to be moved
	 * @param currCell, the current cell that the element is sitting in
	 * @return true or false depending if it is a valid movement or not
	 */
	private synchronized boolean validMove(Direction dir, Cell currCell)
	{
		if(dir == Direction.UP)
		{
			if(currCell.row == 0)
			{
				return false;
			}//if(currCell.row == 0)
		}//if(dir == Direction.UP)
		
		if(dir == Direction.DOWN)
		{
			if(currCell.row == this.height - 1)
			{
				return false;
			}//if(currCell.row == this.height - 1)
		}//if(dir== Direction.DOWN)
		
		if(dir == Direction.LEFT)
		{
			if(currCell.col == 0)
			{
				return false;
			}//if(currCell.col == 0)
		}//if(dir == Direction.LEFT)
		
		if(dir == Direction.RIGHT)
		{
			if(currCell.col == this.width - 1)
			{
				return false;
			}
		}//if(dir == Direction.RIGHT)
		
		if(dir == Direction.UP_LEFT)
		{
			if(currCell.row == 0 || currCell.col == 0)
			{
				return false;
			}//if(currCell.row == 0 || currCell.col == 0)
		}//if(dir == Direction.UP_LEFT
		
		if(dir == Direction.UP_RIGHT)
		{
			if(currCell.row == 0 || currCell.col == this.width - 1)
			{
				return false;
			}//if(currCell.row == 0 || currCell.col == this.width - 1)
		}//if(dir == Direction.UP_RIGHT)
		
		if(dir == Direction.DOWN_LEFT)
		{
			if(currCell.row == this.height - 1 || currCell.col == 0)
			{
				return false;
			}//if(currCell.row == this.height - 1 || currCell.col == 0)
		}//if(dir == Direction.DOWN_LEFT)
		if(dir == Direction.DOWN_RIGHT)
		{
			if(currCell.row == this.height - 1 || currCell.col == this.width - 1)
			{
				return false;
			}//if(currCell.row == this.height - 1 || currCell.col == this.width - 1)
		}//if(dir == Direction.DOWN_RIGHT)
		
		return true;
	}//validMove(Direction dir, Cell currCell)
	
	/**
	 * This method will remove a passed in element from the board.
	 * Only if one exists within the board. If the element is removed,
	 * true is returned. Otherwise, false is returned.
	 * @param elem, the element that is wished to be removed from the board
	 * @return true or false, depending on whether the element is successfully removed
	 */
	public synchronized boolean removeElement(Boardable elem)
	{
		if(!elementPlace.containsKey(elem))
		{
			return false;
		}//if(!elementPlace.containsKey(elem))
		Cell currCell = elementPlace.get(elem);
		currCell.removeElement(elem);
		elementPlace.remove(elem);
		return true;
	}//removeELement(Boardable elem)
	
	/**
	 * This method will take a passed in element and return the row
	 * where that element is located on the board.
	 * @param elem, the element that is to be located in the board
	 * @return the row of which the element is in on the board
	 */
	public synchronized int getRow(Boardable elem)
	{
		if(!elementPlace.containsKey(elem))
		{
			throw new IllegalArgumentException("That element is not on the board");
		}//if(!elementPlace.containsKey(elem))
		return elementPlace.get(elem).row;
	}//getRow(Boardable elem)
	
	/**
	 * This method will take a passed in element and return the column
	 * where that element is located on the board.
	 * @param elem, the element that is to be located in the board
	 * @return the row of which the element is in on the board
	 */
	public synchronized int getColumn(Boardable elem)
	{
		if(!elementPlace.containsKey(elem))
		{
			throw new IllegalArgumentException("That element is not on the board");
		}//if(!elementPlace.containsKey(elem))
		return elementPlace.get(elem).col;
	}//getColumn(Boardable elem)
	
	
	/**
	 * This method will set the instance variable hugged to the 
	 * passed in boolean
	 * @param hugged, the desired boolean that hugged is to be set to
	 */
	public synchronized void setHugged(boolean hugged)
	{
		this.hugged = hugged;
	}//setHugged(boolean hugged)
	
	
	/**
	 * This method will return the value of the instance variable: hugged.
	 * @return hugged instance variable
	 */
	public synchronized boolean beenHugged()
	{
		return this.hugged;
	}//beenHugged()
	
	/**
	 * This method will check all of the different elements within a cell,
	 * and if the passed in element is able to move onto all of the elements,
	 * it will return true, otherwise, false is returned.
	 * @param newCell, the cell holding all of the elements to be checked
	 * @param elem, boardable element to check if the sharing is possible
	 * @return true or false depending on whether the element can share the cell or not
	 */
	private synchronized boolean checkSharing(Cell newCell, Boardable elem)
	{
		for(int index = 0; index < newCell.elements.size(); index++)
		{
			if(!newCell.elements.get(index).share(elem))
			{
				return false;
			}//if(!newCell.elements.get(index).share(elem))
			
		}//for(int index = 0; index < newCell.elements.size(); index++)
		return true;
	}//checkSharing(Cell newCell, Boardable elem)
	
}//Board
