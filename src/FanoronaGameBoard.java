import java.util.Vector;

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
					gameBoard.get(i).add(new Point(Point.State.isOccupiedByBlack));
				}
				//The center row is populated with alternating color tokens
				//  and the center space is empty
				else if ( i == BOARD_CENTER_LENGTH )
				{
					//The first half alternates black white
					if ( j < BOARD_CENTER_WIDTH )
					{
						if ( Util.isEven(j) )
							gameBoard.get(i).add(new Point(Point.State.isOccupiedByBlack));
						else
							gameBoard.get(i).add(new Point(Point.State.isOccupiedByWhite));
					}
					//The center is empty
					else if ( j == BOARD_CENTER_WIDTH)
					{
						gameBoard.get(i).add(new Point(Point.State.isEmpty));
					}
					//The last half alternates black white
					else
					{
						if ( Util.isOdd(j) )
							gameBoard.get(i).add(new Point(Point.State.isOccupiedByBlack));
						else
							gameBoard.get(i).add(new Point(Point.State.isOccupiedByWhite));
					}
				}
				//The bottom rows are populated with white tokens
				else
				{
					gameBoard.get(i).add(new Point(Point.State.isOccupiedByWhite));
				}
			}
		}
	}

	//Move the specified piece (if possible) to the specified location
	//Capture any relevant pieces during the process
	public boolean movePiece(int oldX, int oldY, int newX, int newY )
	{

		if ( isValidMove(oldX, oldY, newX, newY) )
		{
			gameBoard.get(newX).set(newY, gameBoard.get(oldX).get(oldY));
			gameBoard.get(oldX).set(oldY, new Point());

			return true;
		}

		return false;
	}

	public boolean isValidMove(int oldX, int oldY, int newX, int newY )
	{
		//TODO: create valid move logic
		return true;
	}

	//prints out the board as an ascii matrix
	public String toString()
	{
		String string = new String();

		for (int i = 0; i < BOARD_LENGTH; i++ )
		{
			Vector<Point> row = gameBoard.get(i);

			for (int j = 0; j < BOARD_WIDTH; j++ )
			{
				if ( row.get(j).getState() == Point.State.isEmpty )
					string += "E ";
				else if ( row.get(j).getState() == Point.State.isOccupiedByWhite )
					string += "W ";
				else //if ( row.get(j).getState() == Point.State.isOccupiedByBlack )
					string += "B ";
			}
			string = string.substring(0, string.length() - 1);
			string += "\n";
		}
		return string;
	}


}
