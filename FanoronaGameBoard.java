import java.util;
import java.util.Vector;

class FanoronaGameBoard
{

	private Vector< Vector<Token> > gameBoard;
	private int totalTurns;
	private int currentPlayer;
	private static final int BOARD_WIDTH;
	private static final int BOARD_LENGTH;
	private static final int BOARD_CENTER_WIDTH;
	private static final int BOARD_CENTER_LENGTH;

	public FanoronaGameBoard()
	{
		BOARD_WIDTH  = 9;
		BOARD_LENGTH = 5;
		BOARD_CENTER_WIDTH  = Math.ceil(BOARD_WIDTH  / 2);
		BOARD_CENTER_LENGTH = Math.ceil(BOARD_LENGTH / 2);

		for ( int i = 0; i < BOARD_LENGTH ; i++ )
			for ( int j = 0; i < BOARD_WIDTH; j++)
			{
				//The top rows are populated with black tokens
				if ( i < BOARD_CENTER_LENGTH )
				{
					gameBoard[i].pushBack(new Point(State.isOccupiedByBlack));
				}
				//The center row is populated with alternating color tokens
				//  and the center space is empty
				else if ( i == BOARD_CENTER_LENGTH )
				{
					//The first half alternates black white
					if ( j < BOARD_CENTER_WIDTH )
					{
						if ( Util.isEven(j) )
							gameBoard[i].pushBack(new Point(State.isOccupiedByBlack));
						else
							gameBoard[i].pushBack(new Point(State.isOccupiedByWhite));
					}
					//The center is empty
					else if ( j == BOARD_CENTER_WIDTH)
					{
						gameBoard[i].pushBack(new Point(State.isEmpty));
					}
					//The last half alternates black white
					else
					{
						if ( Util.isOdd(j) )
							gameBoard[i].pushBack(new Point(State.isOccupiedByBlack));
						else
							gameBoard[i].pushBack(new Point(State.isOccupiedByWhite));
					}
				}
				//The bottom rows are populated with white tokens
				else
				{
					gameBoard[i].pushBack(new Point(State.isOccupiedByWhite));
				}
			}
	}

	public boolean movePiece(int oldX, int oldY, int newX, int newY )
	{

		if ( isValidMove(oldX, oldY, newX, newY) )
		{
			Piece oldPiece = gameBoard[x][y];
			gameBoard[x][y] = Piece();
			gameBoard[x][y] = oldPiece;
		}
		else
		{
			return false
		}
	}

	public boolean isValidMove(int oldX, int oldY, int newX, int newY )
	{
		return true;
	}


}
