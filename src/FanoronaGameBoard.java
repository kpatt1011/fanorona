import java.util.*;

/*

Current TODO

DONE

pieces can move anywhere when unable to capture
pieces can only make legal moves
	withdrawal
	advancement
	stop from moving in previous direction
capture pieces and remove from board
piece can capture
	propogate in line
test if any possible capture during piaka move
pass function

FUTURE SPRINTS:::

50 turn limit
check win/draw conditions
turn number
networked play

*/

class FanoronaGameBoard
{

	private LinkedList< LinkedList<Point> > gameBoard = new LinkedList< LinkedList<Point> >();
	private int totalTurns = 0;
	private Player currentPlayer = Player.One;
	private boolean playerMovingAgain = false;
	private List<Move> playerMovesThisTurn = new LinkedList<Move>();
	private Coordinate playerLastPieceMoved = null;
	private Pair<Integer,Integer> playerLastDirectionMoved = null;
	public static final int BOARD_WIDTH = 9;
	public static final int BOARD_LENGTH = 5;
	private static final int BOARD_CENTER_WIDTH = (int) Math.ceil(BOARD_WIDTH  / 2);
	private static final int BOARD_CENTER_LENGTH = (int) Math.ceil(BOARD_LENGTH / 2);

	public enum Player { One, Two }
	public enum MoveResult { Failure, Success, Success_EndOfTurn }
	public enum Type { Approach, Withdraw, Paika, Undetermined, Invalid }

	/******************/
	/***CONSTRUCTORS***/
	/******************/

	public FanoronaGameBoard()
	{
		for ( int i = 0; i < BOARD_LENGTH ; i++ )
		{
			gameBoard.add(new LinkedList<Point>());

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

	//deep copy constructor
	public FanoronaGameBoard(FanoronaGameBoard fgb)
	{
		totalTurns = fgb.totalTurns;
		currentPlayer = fgb.currentPlayer;
		playerMovingAgain = fgb.playerMovingAgain;

		if (fgb.playerLastPieceMoved != null)
			playerLastPieceMoved = new Coordinate(playerLastPieceMoved);

		if (fgb.playerLastDirectionMoved != null)
			playerLastDirectionMoved = new
			     Pair<Integer,Integer>
			     (
			      playerLastDirectionMoved.first.intValue() ,
			      playerLastDirectionMoved.second.intValue()
			     );

		for (Move move : playerMovesThisTurn)
			playerMovesThisTurn.add(new Move(move));

		for ( int i = 0; i < BOARD_LENGTH ; i++ )
		{
			gameBoard.add(new LinkedList<Point>());
			for ( int j = 0; j < BOARD_WIDTH; j++)
				gameBoard.get(i).add(new Point(new Coordinate(j,i), getPointAt(j, i).getState()));
		}

	}

	/*************************/
	/**BOARD STATE FUNCTIONS**/
	/*************************/

	//Return the current player making a move
	public Player getCurrentPlayer()
	{
		return currentPlayer;
	}

	//Return the player not making a move
	public Player getWaitingPlayer()
	{
		if (currentPlayer == Player.One)
			return Player.Two;
		else
		
			return Player.One;
	}

	//return unmodifiable version of board
	public LinkedList< LinkedList<Point> > getBoard()
	{
		return gameBoard;
	}

	//return the point at point x, y on the game board
	public Point getPointAt(int x, int y)
	{
		return gameBoard.get(y).get(x);
	}

	//return the point at the given coordinate on the game board
	public Point getPointAt(Coordinate coordinate)
	{
		return getPointAt(coordinate.x, coordinate.y);
	}

	//check if a given point on the board is empty
	// true if it is unoccupied
	public boolean isEmptyAt(Coordinate position)
	{
		return getPointAt(position).isOccupied();
	}

	//return a list of moves the current player has made this turn
	public List<Move> getMovesTakenThisTurn()
	{
		return playerMovesThisTurn;
	}

	//Pass to the next player
	public void pass()
	{
		playerMovingAgain = false;
		playerMovesThisTurn.clear();
		playerLastDirectionMoved = null;
		playerLastPieceMoved = null;
		currentPlayer = getWaitingPlayer();
	}

	/********************/
	/**CAPTURE FUNCTION**/
	/********************/

	//capture all pieces in a line with respect to the capture direction
	private void capture(Move move)
	{
		Coordinate start = move.start;
		Coordinate end = move.end;
		Pair<Integer,Integer> direction = move.getDirectionMoved();
		Coordinate nextPiece = null;
		Point.State pieceColor = null;

		//capture all pieces in a line

		if ( move.isApproach() )
			nextPiece = new Coordinate(end.x + direction.first, end.y + direction.second);
		else
			nextPiece = new Coordinate(start.x - direction.first, start.y - direction.second);

		if ( isOnBoard(nextPiece) )
			pieceColor = getPointAt(nextPiece).getState();

		while ( isOnBoard(nextPiece) && getPointAt(nextPiece).getState() == pieceColor )
		{
			getPointAt(nextPiece).setState( Point.State.isEmpty );

			if ( move.isApproach() )
				nextPiece = new Coordinate(nextPiece.x + direction.first, nextPiece.y + direction.second);
			else
				nextPiece = new Coordinate(nextPiece.x - direction.first, nextPiece.y - direction.second);
		}

		//make the move, capturing the first piece
		getPointAt(end).setState( getPointAt(start).getState() );
		getPointAt(start).setState( Point.State.isEmpty );

	}

	/*****************/
	/**MOVE FUNCTION**/
	/*****************/

	//Backwards compatible wrapper
	// hope to phase out soon
	public MoveResult movePiece(Move move)
	{
		return  move(move);
	}

	//Move the specified piece (if possible) to the specified location
	//Capture any relevant pieces during the process
	public MoveResult move(Move move)
	{

		if ( move.isValid() )
		{
			capture(move);

			//assume the player can go again
			playerMovingAgain = true;
			playerLastDirectionMoved = move.getDirectionMoved();
			playerMovesThisTurn.add(move);
			playerLastPieceMoved = move.end;

			if ( captureMoveExists(move.end) == false)
			{
				pass();
				return MoveResult.Success_EndOfTurn;
			}

			return MoveResult.Success;
		}
		return MoveResult.Failure;
	}

	/**********************/
	/**GET MOVE FUNCTIONS**/
	/**********************/

	//Returns a list of all possible moves to make this turn
	// Will contain only Capture moves or only Paika moves
	public List<Move> getAllPossibleMoves()
	{
		Point.State token;

		if (currentPlayer == Player.One)
			token = Point.State.isOccupiedByWhite;
		else
			token = Point.State.isOccupiedByBlack;

		List<Move> possibleMoves = new LinkedList<Move>();

		//get possible moves for each token the player owns
		for (int i = 0; i < BOARD_LENGTH; i++)
			for (int j = 0; j < BOARD_WIDTH; j++)
				if (getPointAt(j,i).getState() == token)
					possibleMoves.addAll(getPossibleMoves(getPointAt(j,i).getCoordinate()));

		//if a capture move is available, remove all non capture moves
		for ( Move move : possibleMoves)
		{
			if (move.isCapture())
			{
				ListIterator<Move> itr = possibleMoves.listIterator();
				while (itr.hasNext())
					if (itr.next().isCapture() == false)
						itr.remove();
				break;
			}
		}

		return possibleMoves;
	}

	//Returns a list of all possible moves from a given point
	// Will contain only Capture moves or only Paika moves
	public List<Move> getPossibleMoves(Coordinate start)
	{
		//get adjacent coordinates and make moves to them
		List<Coordinate> ends = getAdjacentCoordinates(start);
		List<Move> moves = new LinkedList<Move>();

		for (Coordinate end : ends)
			moves.add(this.new Move(start, end));

		ListIterator<Move> itr = moves.listIterator();

		//remove points resulting in invalid moves
		while( itr.hasNext() )
		{
			Move move = itr.next();
			if ( move.isValid() == false)
				itr.remove();
			else if ( move.isUndetermined() )
			{
				//if undecided withdraw or approach, add one for each and remove undecided
				itr.add(this.new Move(move.start, move.end, Type.Withdraw));
				move.setCaptureType(Type.Approach);
			}
		}

		//if a capture move is available, remove all non capture moves
		for ( Move move : moves)
		{
			if (move.isCapture())
			{
				itr = moves.listIterator();
				while (itr.hasNext())
					if (itr.next().isCapture() == false)
						itr.remove();
				break;
			}
		}

		return moves;
	}

	//return a list of adjaced coordinates on the board not occupied by other pieces
	private List<Coordinate> getAdjacentCoordinates(Coordinate start)
	{
		List<Coordinate> ends = new LinkedList<Coordinate>();

		ends.add(new Coordinate(start.x + 1, start.y    ));
		ends.add(new Coordinate(start.x    , start.y + 1));
		ends.add(new Coordinate(start.x - 1, start.y    ));
		ends.add(new Coordinate(start.x    , start.y - 1));
		ends.add(new Coordinate(start.x + 1, start.y - 1));
		ends.add(new Coordinate(start.x - 1, start.y + 1));
		ends.add(new Coordinate(start.x + 1, start.y + 1));
		ends.add(new Coordinate(start.x - 1, start.y - 1));

		ListIterator<Coordinate> itr = ends.listIterator();

		while ( itr.hasNext() )
		{
			Coordinate coord = itr.next();

			if ( isOnBoard(coord) == false || getPointAt(coord).isOccupied())
				itr.remove();
		}

		return ends;
	}

	/***********************/
	/**MOVE TEST FUNCTIONS**/
	/***********************/

	//check that a capture move exists for the current player
	// returns true if so
	public boolean captureMoveExists()
	{
		List<Move> possibleMoves = getAllPossibleMoves();

		if (possibleMoves.size() == 0)
			return false;

		return ( possibleMoves.get(0).isCapture() );
	}

	//check that a capture move exists for the current player
	// returns true if so
	private boolean captureMoveExists(Coordinate start)
	{
		List<Move> possibleMoves = getPossibleMoves(start);

		if (possibleMoves.size() == 0)
			return false;

		return ( possibleMoves.get(0).isCapture() );
	}

	//check that the given coordinate is on the game board
	// returns true if it is
	private boolean isOnBoard(Coordinate coordinate)
	{
		if (coordinate.x >= BOARD_WIDTH || coordinate.x < 0)
			return false;
		if (coordinate.y >= BOARD_LENGTH || coordinate.y < 0)
			return false;
		return true;
	}

	/************************/
	/****BOARD EVALUATION****/
	/************************/

	public int boardEvaluation()
	{
		 /* negative advantage means white is winning, 0 is neutral, positive means black is winning
			NOTE: this function is called on the current gameboard, with the assumption that the gameboard
		*/
		int advantage=0;
		//iterates through each vector<point> object in the gameboard vector
		ListIterator<LinkedList<Point>>  rowIterator= gameBoard.listIterator(); 
		//store the points of each black and white piece for further operations 
		LinkedList<Point> blackPieces=new LinkedList<Point>(); 
		LinkedList<Point> whitePieces=new LinkedList<Point>(); 
		
		/*first level of evaluation: find all remaining pieces on the board and 
		  determine advantage based on number of
		  remaining players for each team*/
		while(rowIterator.hasNext())
		{
			LinkedList<Point> currentRow = (LinkedList<Point>) rowIterator.next();
			ListIterator<Point> columnIterator= currentRow.listIterator(); //iterates through each point object in the current column
			while(columnIterator.hasNext())
			{
				Point currentPoint = columnIterator.next();
				String value = currentPoint.toString("W", "B", "E");
				switch(value)
				{
					case "B":
						advantage= advantage + 1;
						blackPieces.add(currentPoint);
						break;
					case "W":
						advantage= advantage - 1;
						whitePieces.add(currentPoint);
						break;
					default:
						break;
				}
			}
		}
		/*second level of evaluation: assign advantage points for total number
		  of moves available for each piece on each team. additional advantage multiplier
		  is given for available moves if they are capture moves */
		for(Point p : blackPieces)
		{
		 Coordinate currentPieceCoordinate=p.getCoordinate();
		 List<Move> moves = getPossibleMoves(currentPieceCoordinate);
		 int blackMoveAdvantage = moves.size();
		 if(blackMoveAdvantage != 0 && moves.get(0).isCapture())
		 {
		  advantage=advantage + (3 * blackMoveAdvantage);
		 } else{
		  advantage=advantage + blackMoveAdvantage;
		 }
		}
		
		for(Point p : whitePieces)
		{
		 Coordinate currentPieceCoordinate=p.getCoordinate();
		 List<Move> moves = getPossibleMoves(currentPieceCoordinate);
		 int whiteMoveAdvantage = moves.size();
		 if(whiteMoveAdvantage != 0 && moves.get(0).isCapture())
		 {
		  advantage=advantage - (3 * whiteMoveAdvantage);
		 } else{
		  advantage=advantage - whiteMoveAdvantage;
		 }
		}
		
		return advantage;
	}

	/******************/
	/*******MISC*******/
	/******************/

	//return the current board as an ASCII matrix
	public String toString()
	{
		String string = new String();

		//for (int i = 0; i < BOARD_LENGTH; i++ )
		//	string += toString(i) + "\n";

		return string;
	}

	//return a row of the current board as an ASCII matrix
	public String toString(int rowNumber)
	{
		String string = new String();

		LinkedList<Point> row = gameBoard.get(rowNumber);

		for (int j = 0; j < BOARD_WIDTH; j++ )
			string += row.get(j).toString() + " ";

		string = string.substring(0, string.length() - 1);

		return string;
	}

	public String typeToString(Type type)
	{
		switch (type)
		{
			case Approach: return "Approach";
			case Withdraw: return "Withdraw";
			case Paika: return "Paika";
			case Undetermined: return "Undetermined";
			case Invalid: return "Invalid";
			default: return "Unknown";
		}
	}

	public String playerToString(Player player)
	{
		switch (player)
		{
			case One: return "One";
			case Two: return "Two";
			default: return "Unknown";
		}
	}

	public String moveResultToString(MoveResult result)
	{
		switch (result)
		{
			case Failure: return "Failure";
			case Success: return "Success";
			case Success_EndOfTurn: return "Success: End of Turn";
			default: return "Unknown";
		}
	}

	/*******************************/
	/*******************************/
	/******   MOVE SUBCLASS   ******/
	/*******************************/
	/*******************************/

	public class Move
	{
		public final Coordinate start;
		public final Coordinate end;

		private Type type;

		//////////
		//Public//
		//////////

		public Move(Coordinate start, Coordinate end)
		{
			this.start = start;
			this.end = end;

			if ( isValidMovement() == false)
			{
				type = Type.Invalid;
				return;
			}

			boolean approach = isValidApproach();
			boolean withdraw = isValidWithdraw();

			if (approach && withdraw)
				type = Type.Undetermined;
			else if (approach)
				type = Type.Approach;
			else if (withdraw)
				type = Type.Withdraw;
			else
				type = Type.Paika;
		}

		public Move(Coordinate start, Coordinate end, Type type)
		{
			this(start, end);
			setCaptureType(type);
		}

		public Move(Move move)
		{
			this.start = new Coordinate(move.start);
			this.end = new Coordinate(move.end);
			this.type = move.type;
		}

		public boolean isCapture()
		{ return (isApproach() || isWithdraw()); }

		public boolean isApproach()
		{ return type == Type.Approach; }

		public boolean isWithdraw()
		{ return type == Type.Withdraw; }

		public boolean isPaika()
		{ return type == Type.Paika; }

		public boolean isUndetermined()
		{ return type == Type.Undetermined; }

		public boolean isValid()
		{ return type != Type.Invalid; }

		public Type getMoveType()
		{ return type; }

		public boolean setCaptureType(Type _type)
		{
			if (type == Type.Undetermined)
				type = _type;
			else
				return false;

			return true;
		}

		///////////
		//Private//
		///////////

		//check that the mechanics of the move are valid
		// returns true if so
		private boolean isValidMovement()
		{
			//Can not move a piece without a piece
			if (getPointAt(start).isOccupied() == false)
				return false;

			//Can not move into occupied Space
			if (getPointAt(end).isOccupied())
				return false;

			//Certain points only have certain directions they can move
			if (isValidDirectionalMovement() == false)
				return false;

			//Do not leave the board x and y coordinates
			if (isOnBoard(end) == false)
				return false;

			if (playerMovingAgain)
			{
				//must move the last piece used
				if ( playerLastPieceMoved.equals(start) == false)
					return false;

				//must not move in the direction moved last time
				if ( getDirectionMoved().equals(playerLastDirectionMoved) )
					return false;

				//must not move into a space already visted
				for ( Move move : playerMovesThisTurn)
					if (end.equals(move.start))
						return false;
			}

			return true;
		}

		//check that a capture move by approach is valid
		// returns true if so
		private boolean isValidApproach()
		{
			Pair<Integer,Integer> momentum = getDirectionMoved();
			Coordinate targetCoord = new Coordinate(end.x + momentum.first, end.y + momentum.second);

			return isValidCapture(targetCoord);
		}

		//check that a capture move by withdrawal is valid
		// returns true if so
		private boolean isValidWithdraw()
		{
			Pair<Integer,Integer> momentum = getDirectionMoved();
			Coordinate targetCoord = new Coordinate(start.x - momentum.first, start.y - momentum.second);

			return isValidCapture(targetCoord);
		}

		private boolean isValidCapture(Coordinate target)
		{
			//if moved to the edge of the board, no capture was done
			if (isOnBoard(target) == false)
				return false;

			Point targetPoint = getPointAt(target);

			//Cannot capture if no piece at the target location
			if (targetPoint.isOccupied() == false)
				return false;

			switch(getPointAt(start).getState())
			{
				//if the capturing piece is white, can only capture black
				case isOccupiedByWhite:
					return (targetPoint.getState() == Point.State.isOccupiedByBlack);
				//if the capturing piece is black, can only capture white
				case isOccupiedByBlack:
					return (targetPoint.getState() == Point.State.isOccupiedByWhite);
				//some unknown state, assume bad
				default:
					return false;
			}
		}

		//get the direction of a move
		// returns an integer pair denoting the direction moved
		// (0,1) indicates move south
		// (1,0) indicates move east
		// (-1,-1) indicates move north west
		private Pair<Integer, Integer> getDirectionMoved()
		{
			int momentumX = end.x - start.x;
			int momentumY = end.y - start.y;
			return new Pair<Integer, Integer>(new Integer(momentumX), new Integer(momentumY));
		}

		//check that the direction moved is valid
		// returns true if it is
		private boolean isValidDirectionalMovement()
		{
			Pair<Integer,Integer> directionMoved = getDirectionMoved();

			//weak points can only move in the cardinal directions
			if ( Util.isOdd(start.x + start.y) )
			{
				return
						directionMoved.equals(new Pair<Integer,Integer>(1,0))
					||	directionMoved.equals(new Pair<Integer,Integer>(0,1))
					|| 	directionMoved.equals(new Pair<Integer,Integer>(-1,0))
					||	directionMoved.equals(new Pair<Integer,Integer>(0,-1));
			}

			return true;
		}

		/******************/
		/*******MISC*******/
		/******************/

		public String toString()
		{
			return "[" + start.toString() +" -> " + end.toString() + ", " + typeToString(type) + "]";
		}

	}

}
