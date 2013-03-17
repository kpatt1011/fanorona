import java.util.Vector;
import java.util.Collections;
import java.util.List;
import java.util.LinkedList;


/*

Current TODO

check space is empty
user can move pieces

FUTURE SPRINTS:::

50 turn limit
capture pieces and remove from board
check win/draw conditions
turn number
piece can capture
pieces can only make legal moves
pieces can move anywhere when unable to capture

*/

class FanoronaGameBoard
{

	private Vector< Vector<Point> > gameBoard = new Vector< Vector<Point> >();
	private int totalTurns = 0;
	private int currentPlayer = 1;
	private static final int BOARD_WIDTH = 9;
	private static final int BOARD_LENGTH = 5;
	private static final int BOARD_CENTER_WIDTH = (int) Math.ceil(BOARD_WIDTH  / 2);
	private static final int BOARD_CENTER_LENGTH = (int) Math.ceil(BOARD_LENGTH / 2);

	public FanoronaGameBoard()
	{
		for ( int i = 0; i < BOARD_LENGTH ; i++ )
		{
			gameBoard.add(new Vector<Point>());

			for ( int j = 0; j < BOARD_WIDTH; j++)
			{
				//The top rows are populated with black tokens
				if ( i < BOARD_CENTER_LENGTH )
				{
					gameBoard.get(i).add(new Point(new Coordinate(j,i), Point.State.isOccupiedByBlack));
				}
				//The center row is populated with alternating color tokens
				//  and the center space is empty
				else if ( i == BOARD_CENTER_LENGTH )
				{
					//The first half alternates black white
					if ( j < BOARD_CENTER_WIDTH )
					{
						if ( Util.isEven(j) )
							gameBoard.get(i).add(new Point(new Coordinate(j,i), Point.State.isOccupiedByBlack));
						else
							gameBoard.get(i).add(new Point(new Coordinate(j,i), Point.State.isOccupiedByWhite));
					}
					//The center is empty
					else if ( j == BOARD_CENTER_WIDTH)
					{
						gameBoard.get(i).add(new Point(new Coordinate(j,i), Point.State.isEmpty));
					}
					//The last half alternates black white
					else
					{
						if ( Util.isOdd(j) )
							gameBoard.get(i).add(new Point(new Coordinate(j,i), Point.State.isOccupiedByBlack));
						else
							gameBoard.get(i).add(new Point(new Coordinate(j,i), Point.State.isOccupiedByWhite));
					}
				}
				//The bottom rows are populated with white tokens
				else
				{
					gameBoard.get(i).add(new Point(new Coordinate(j,i), Point.State.isOccupiedByWhite));
				}
			}
		}
	}

	//Move the specified piece (if possible) to the specified location
	//Capture any relevant pieces during the process
	public boolean movePiece(Coordinate start, Coordinate end )
	{
		if ( isValidMove(start, end) )
		{
			getPointAt(end).setState( getPointAt(start).getState() );
			getPointAt(start).setState( Point.State.isEmpty );

			return true;
		}
		return false;
	}

	public boolean captureMoveExists()
	{
	    //TODO: check if a capture move exists, if so force piece to make a capture
	    return false;
	}

	public boolean isValidMove(Coordinate start, Coordinate end )
	{
		//TODO: create valid move logic
		if(!captureMoveExists())
        {
        //if the starting x coordinate is 0,2,4,6 or 8, can move diagonal or up down left right
		if(start.x % 2 ==0 || start.x==0)
        {
        if((end.x)-1 == start.x || (end.x)+1==start.x || (end.x)==(start.x))
        {
        if((end.y)-1 == start.y ||(end.y)+1 == start.y || (end.y)== start.y  )
        {
        if((end.x)>=0 && (end.x)<9 && (end.y)>=0 && (end.y)<5)
        {
         return true;
        }
        }
        }
        }
		//if the starting x coordinate is 1,3,5,or 7 can only move up down left right
		if((start.x)%2 ==1)
        {
        if((end.x)-1==(start.x) || (end.x)+1==(start.x))
        {
        if((end.y)==(start.y))
        {
        if((end.x)>=0 && (end.x)<9 && (end.y)>=0 && (end.y)<5)
        {
         return true;
        }
        }
        } else if((end.x) == (start.x))
        {
        if((end.y)-1==(start.y) || (end.y)+1==(start.y))
        {
        if((end.x)>=0 && (end.x)<9 && (end.y)>=0 && (end.y)<5)
        {
         return true;
        }
        }
        }
        }
		return false;
        }
	}

	//ideally, return unmodifiable version of board
	public Vector< Vector<Point> > getBoard()
	{
		return gameBoard;
	}

	public Point getPointAt(int x, int y)
	{
		return gameBoard.get(x).get(y);
	}

	public Point getPointAt(Coordinate coordinate)
	{
		return getPointAt(coordinate.x, coordinate.y);
	}

	public boolean isEmptyAt(Coordinate position)
	{
		return getPointAt(position).isOccupied();
	}

	//prints out the board as an ASCII matrix
	public String toString()
	{
		String string = new String();

		for (int i = 0; i < BOARD_LENGTH; i++ )
		{
			Vector<Point> row = gameBoard.get(i);

			for (int j = 0; j < BOARD_WIDTH; j++ )
			{
				string += row.get(j).toString() + " ";
			}

			string = string.substring(0, string.length() - 1);

			string += "\n";
		}
		return string;
	}


}
