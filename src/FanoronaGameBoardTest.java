import static org.junit.Assert.*;
import org.junit.Test;
import java.util.*;

public class FanoronaGameBoardTest {

	@Test
	public void test_autoPlay()
	{
		/*
		System.out.printf(
		"\n" +
		"Best Viewed with MonoSpace 12pt Font\n" +
		"/////////////////////////////////////////\n" +
		"/////////////////////////////////////////\n" +
		"///////////////////AUTO//////////////////\n" +
		"/////////////////////////////////////////\n" +
		"/////////////////////////////////////////\n" +
		"\n"
		);

		FanoronaGameBoard fgb = new FanoronaGameBoard();

		for (int i = 0; i < 40; i++)
		{
			List<FanoronaGameBoard.Move> moves = fgb.getAllPossibleMoves();

			if (moves.size() != 0)
				printBoardMove(fgb, moves.get(0), true);
		}
		*/
	}

	@Test
	public void test_BoardSizes()
	{
		/*
		for (int i = 1; i < 13; i += 2)
			for (int j = 1; j < 13; j += 2)
			{
				FanoronaGameBoard fgb = new FanoronaGameBoard(i,j);
				List<String> strings = buildBoardStringList(fgb);
				String board = "";
				for (String str : strings)
					board += str + "\n";
				System.out.println( board );
			}
		*/
	}

	@Test
	public void test_VariableBoardSizeRun()
	{
		int BoardWidth = 9;
		int BoardLength = 5;
		FanoronaGameBoard fgb = new FanoronaGameBoard(BoardWidth, BoardLength);

		while (fgb.isGameOver() == false)
		{
			randomAI(fgb);
			actualAI(fgb, 2);
		}

		System.out.println("Game Ended on Turn: " + fgb.getTurnNumber() + "/" + fgb.MAX_TURNS);
		System.out.println( "Winner is: " + fgb.playerToString(fgb.getWinner()));

	}

	@Test
	public void test_autoPlayRandom()
	{
/*
		System.out.printf(
		"\n" +
		"Best Viewed with MonoSpace 12pt Font\n" +
		"/////////////////////////////////////////\n" +
		"/////////////////////////////////////////\n" +
		"///////////////AUTO RANDOM///////////////\n" +
		"/////////////////////////////////////////\n" +
		"/////////////////////////////////////////\n" +
		"\n"
		);

		FanoronaGameBoard fgb = new FanoronaGameBoard();
		Random rng = new Random();
		int i = 0;

		while (true)
		{
			List<FanoronaGameBoard.Move> moves = fgb.getAllPossibleMoves();

			if (moves.size() == 0)
				break;

			printBoardMove(fgb, moves.get(rng.nextInt(100) % moves.size()), true);
			i++;
		}
		System.out.println("Total Turns: " + i);
*/
	}

	@Test
	public void test_AI()
	{
/*
		FanoronaGameBoard fgb = new FanoronaGameBoard();
		FanoronaGameBoard.MoveResult result;
		Random rng = new Random();
		int i = 0;

		while (fgb.isGameOver() == false)
		{
			randomAI(fgb);
			i++;

			if (fgb.isGameOver())
				break;

			actualAI(fgb,2);
			i++;
		}

		System.out.println("Total Turns: " + i);
		System.out.println("Max Turns: " + fgb.MAX_TURNS);
		System.out.println( "Winner is: " + fgb.playerToString(fgb.getWinner()));
*/
	}

	//Test that the getBoard function.
	// TODO: make unmodifiable
	@Test
	public void test_get()
	{
		FanoronaGameBoard fgb = new FanoronaGameBoard();

		LinkedList<LinkedList<Point>> board = fgb.getBoard();

		for (int i = 0; i < board.size(); i++)
		{
			LinkedList<Point> row = board.get(i);

			for (int j = 0; j < row.size(); j++)
				assertTrue( board.get(i).get(j).equals(board.get(i).get(j)) );
		}
	}

	///////////////////////////////////////////
	///////////////////////////////////////////
	///////////////////////////////////////////
	///////////////////////////////////////////

	//print all valid moves
	private void printAllValidMoves(FanoronaGameBoard fgb)
	{
		List<FanoronaGameBoard.Move> moves = fgb.getAllPossibleMoves();

		for (FanoronaGameBoard.Move move : moves)
			System.out.println(move.toString());
	}

	//print valid moves for a point
	private void printValidMoves(FanoronaGameBoard fgb, Coordinate coord)
	{
		List<FanoronaGameBoard.Move> moves = fgb.getPossibleMoves(coord);

		for (FanoronaGameBoard.Move move : moves)
			System.out.println(move.toString());
	}

	private FanoronaGameBoard.MoveResult printBoardMove(FanoronaGameBoard fgb, FanoronaGameBoard.Move move, boolean showLines)
	{


		List<String> before = new LinkedList<String>();
		List<String> after = new LinkedList<String>();
		String show = new String();
		String ColumnNumbers = new String("  ");
		FanoronaGameBoard.Player player = fgb.getCurrentPlayer();

		//print the player and all available moves
		System.out.printf("\t\t\t\tPlayer: " + fgb.playerToString(player) + "\n\tOptions:\n");
		printAllValidMoves(fgb);
		System.out.println();

		//build the column numbers string
		for (int i = 0; i < fgb.BOARD_WIDTH; i++)
			ColumnNumbers += Integer.toString(i) + " ";
		ColumnNumbers = ColumnNumbers.substring(0, ColumnNumbers.length() - 1);


		//build before move board
		if (showLines)
			before = buildBoardStringList(fgb);
		else
			for (int i = 0; i < fgb.BOARD_LENGTH; i++)
				before.add( Integer.toString(i) + " " + fgb.toString(i));

		//play the move
		FanoronaGameBoard.MoveResult result = fgb.move(move);

		//build after move board
		if (showLines)
			after = buildBoardStringList(fgb);
		else
			for (int i = 0; i < fgb.BOARD_LENGTH; i++)
				after.add(Integer.toString(i) + " " + fgb.toString(i));


		//move info string
		String moveStr = " -> " + move.toString() + " -> ";

		//build spacer string
		String spacer = new String();
		for (int i = 0; i < moveStr.length(); i++)
			spacer += " ";

		//link the strings together
		show += ColumnNumbers + spacer + ColumnNumbers + "\n";
		for(int i = 0; i < before.size(); i++)
		{
			if (i == fgb.BOARD_LENGTH/2)
				show += before.get(i) + moveStr + after.get(i) + "\n";
			else
				show += before.get(i) + spacer + after.get(i) + "\n";
		}

		//show everything
		System.out.printf( "\t\t\tResult: " + fgb.moveResultToString(result) + "\n");
		System.out.println(show);
		System.out.println("------------------------------------------------------------------------");
		System.out.println();

		return result;
	}

	//Build a game board with lines indicating strength
	private List<String> buildBoardStringList(FanoronaGameBoard fgb)
	{
		List<String> l = new LinkedList<String>();
		for (int i = 0; i < fgb.BOARD_LENGTH; i++)
		{
			//add connections to the normal string
			l.add( Integer.toString(i) + "x" + fgb.toString(i));
			l.set(l.size()-1, l.get(l.size()-1).replace(' ', '-'));
			l.set(l.size()-1, l.get(l.size()-1).replace('x', ' '));

			//build the alternating strong, weak connections
			String s = "  ";
			for (int j = 0; j < l.get(i).length() - 2; j++)
			{
				int sizeDifference = (Math.abs(fgb.BOARD_LENGTH - fgb.BOARD_WIDTH));
				boolean isShiftSize = sizeDifference == 2 || sizeDifference == 6 || sizeDifference == 10;
				switch((j % 4))
				{
					case 0:
						s += "|"; break;
					case 2:
						s += "|"; break;
					case 1:
						if (Util.isEven(i) && isShiftSize)
							s += "/";
						else if (Util.isEven(i))
							s += "\\";
						else if (isShiftSize)
							s += "\\";
						else
							s += "/";
						break;
					case 3:
						if (Util.isEven(i) && isShiftSize)
							s += "\\";
						else if (Util.isEven(i))
							s += "/";
						else if (isShiftSize)
							s += "/";
						else
							s += "\\";
						break;
					default:
						s += "e"; break;
				}
			}
			l.add(s);
		}
		l.remove(l.size()-1);
		return l;
	}

	void actualAI(FanoronaGameBoard fgb, int depth)
	{
		FanoronaGameBoard.MoveResult result = FanoronaGameBoard.MoveResult.Success;
		while (result == FanoronaGameBoard.MoveResult.Success)
		{
            MinimaxNode testRoot;
            if (fgb.getCurrentPlayer() == FanoronaGameBoard.Player.Two)
            {
                testRoot = new MaxNode (fgb);
            }
            else
            {
                testRoot = new MinNode (fgb);
            }
			MinimaxTree testTree = new MinimaxTree (testRoot, depth);

			result = printBoardMove(fgb, testTree.getIdealMove(), true);
		}
	}

	void randomAI(FanoronaGameBoard fgb)
	{
		Random rng = new Random();
		FanoronaGameBoard.MoveResult result = FanoronaGameBoard.MoveResult.Success;
		boolean pass = false;

		while ( result == FanoronaGameBoard.MoveResult.Success )
		{
			List<FanoronaGameBoard.Move> moves = fgb.getAllPossibleMoves();
			FanoronaGameBoard.Move move;
			while (true)
			{
				int index = rng.nextInt(100) % moves.size();
				move = moves.get(index);

				//take the last possible move
				if (moves.size() == 1)
					break;

				//there are a lot of sacrifice options
				// rarely is it a good choice
				if ( move.isSacrifice() )
				{
					//take a sacrifice 3% of the time
					if ( rng.nextInt(30) == 0 )
						break;
					else
						moves.remove(index);
				}
				//pass 2% of the time
				else if ( rng.nextInt(50) == 0 )
				{
					pass = true;
					break;
				}
				//otherwise take it
				else
					break;
			}
			//System.out.println("RNG IS PLAYER: " + fgb.playerToString(fgb.getCurrentPlayer()));

			if (pass)
			{
				System.out.println("Player " + fgb.playerToString(fgb.getCurrentPlayer()) + " Passes");
				System.out.println("------------------------------------------------------------------------");
				fgb.pass();
				break;
			}
			else
				result = printBoardMove(fgb, move, true);
		}
	}



}
