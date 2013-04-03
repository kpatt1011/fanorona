
import java.util.*;
import java.io.*;

class CommandLineInterface
{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    FanoronaGameBoard gameBoard;

    public static void main (String[] args)
    {
        boolean AI = false;
        CommandLineInterface cli = new CommandLineInterface();
        System.out.println("\n     WELCOME TO FANORONA!\n");

        // obtain length
        System.out.print("Enter the board length that you wish to play: ");
        String strBoardLength = cli.readLine();
        int boardLength = Integer.parseInt(strBoardLength);
        while (boardLength % 2 == 0 || boardLength < 3 || boardLength > 13)
        {
            System.out.println("INVALID BOARD LENGTH. Length must be odd and in between 3 and 13 inclusive.");
            System.out.print("Enter the board length that you wish to play: ");
            strBoardLength = cli.readLine();
            boardLength = Integer.parseInt(strBoardLength);
        }
        System.out.println();

        // obtain height
        System.out.print("Enter the board height that you wish to play: ");
        String strBoardHeight = cli.readLine();
        int boardHeight = Integer.parseInt(strBoardHeight);
        while (boardHeight % 2 == 0 || boardHeight < 3 || boardHeight > 13)
        {
            System.out.println("INVALID BOARD HEIGHT. Height must be odd and in between 3 and 13 inclusive.");
            System.out.print("Enter the board height that you wish to play: ");
            strBoardHeight = cli.readLine();
            boardHeight = Integer.parseInt(strBoardHeight);
        }
        System.out.println();

        // activate AI
        System.out.print("Do you wish to play against the AI? (y/n): ");
        String isAI = cli.readLine();
        if (isAI.equals("y") || isAI.equals("Y"))
        {
            AI = true;
        }

        // give help then begin game
        System.out.println("\nThe format for the commands are the same as the format for the server commands listed in Keyser's API.");
        System.out.println("E.G. If you wish to make a paika move from (0,0) to (1,1) type in the following: P 0 0 1 1\n");

        System.out.println("     BEGINNING GAME\n");

        cli.gameBoard = new FanoronaGameBoard (boardLength, boardHeight);

		//build the column numbers string
        String ColumnNumbers = new String ("  ");
		for (int i = 0; i < boardLength; i++)
        {
            ColumnNumbers += Integer.toString(i) + " ";
        }
		ColumnNumbers = ColumnNumbers.substring(0, ColumnNumbers.length() - 1);

        while (cli.gameBoard.isGameOver() == false)
        {
            // print the board
            List<String> boardList = cli.buildBoardStringList(cli.gameBoard);
            System.out.println(ColumnNumbers);
            for (int i = 0; i < boardList.size(); i++)
            {
                System.out.println(boardList.get(i));
            }

            if (cli.gameBoard.getCurrentPlayer() == FanoronaGameBoard.Player.One)
            {
                System.out.print("\nPlayer One: ");
            }
            else
            {
                System.out.print("\nPlayer Two: ");
            }
            System.out.print("Make your move: ");

            FanoronaGameBoard.Move userMove = cli.interpretMove(cli.readLine());

            if (userMove == null)
            {
                continue;
            }

            FanoronaGameBoard.MoveResult moveResult = cli.gameBoard.move(userMove);

            if (moveResult == FanoronaGameBoard.MoveResult.Failure)
            {
                System.out.println("INVALID MOVE. Be sure to look at the rules of Fanorona to know valid moves.");
                continue;
            }

            if (moveResult == FanoronaGameBoard.MoveResult.Success)
            {
                System.out.println("You may chain moves.");
                continue;
            }

            System.out.println("End of your turn.");
            System.out.println("-------------------------");

            if (cli.gameBoard.isGameOver() == true)
            {
                break;
            }
            
            if (AI)
            {
                System.out.println("AI is thinking...");

                int depth = 1;
                MinimaxNode testRoot;
                if (cli.gameBoard.getCurrentPlayer() == FanoronaGameBoard.Player.Two)
                {
                    testRoot = new MaxNode (cli.gameBoard);
                }
                else
                {
                    testRoot = new MinNode (cli.gameBoard);
                }
                MinimaxTree testTree = new MinimaxTree (testRoot, depth);

                //testTree.getIdealMove();
                //FanoronaGameBoard.Move AImove = null;
                FanoronaGameBoard.Move AImove = cli.gameBoard.new Move(testTree.getIdealMove());

                moveResult = cli.gameBoard.move(AImove);

                if (moveResult == FanoronaGameBoard.MoveResult.Success)
                {
                    cli.gameBoard.pass();
                }
            }
        }

        System.out.println("GAME OVER");
        System.out.println("The winner is:");
        if (cli.gameBoard.getWinner() == FanoronaGameBoard.Player.One)
        {
            System.out.println("Player One!");
        }
        else
        {
            System.out.println("Player Two!");
        }
    }

    CommandLineInterface ()
    {

    }

    public String readLine ()
    {
        String retStr = null;
        try
        {
            retStr = br.readLine();
        }
        catch (IOException e)
        {
            System.out.println("IOError! Aborting...\n");
            System.exit(-1);
        }

        return retStr;
    } 

    public FanoronaGameBoard.Move interpretMove(String input)
    {
        // split the string into chunks
        String[] tokens = input.split("[ ]+");
        FanoronaGameBoard.Move move = null;
        if (tokens[0].equals("P"))
        {
            if (tokens.length != 5)
            {
                System.out.println("INVALID NUMBER OF ARGUMENTS. The paika move takes four arguments.");
                System.out.println("Be sure to consult Keyser's API on how to make moves.");

                return null;
            }

            Coordinate start = new Coordinate (Integer.parseInt(tokens[1]), 
                Integer.parseInt(tokens[2]));
            Coordinate end = new Coordinate (Integer.parseInt(tokens[3]), 
                Integer.parseInt(tokens[4]));

            move = gameBoard.new Move (start, end, FanoronaGameBoard.Type.Paika);

            System.out.println("PAIKA!");
        }
        else if (tokens[0].equals("S"))
        {
            if (tokens.length != 3)
            {
                System.out.println("INVALID NUMBER OF ARGUMENTS. The sacrifice move takes two arguments.");
                System.out.println("Be sure to consult Keyser's API on how to make moves.");

                return null;
            }

            Coordinate start = new Coordinate (Integer.parseInt(tokens[1]), 
                Integer.parseInt(tokens[2]));

            move = gameBoard.new Move (start, start, FanoronaGameBoard.Type.Sacrifice);

            System.out.println("SACRIFICE!");
        }
        else if (tokens[0].equals("W"))
        {
            if (tokens.length != 5)
            {
                System.out.println("INVALID NUMBER OF ARGUMENTS. The withdraw move takes four arguments.");
                System.out.println("Be sure to consult Keyser's API on how to make moves.");

                return null;
            }

            Coordinate start = new Coordinate (Integer.parseInt(tokens[1]), 
                Integer.parseInt(tokens[2]));
            Coordinate end = new Coordinate (Integer.parseInt(tokens[3]), 
                Integer.parseInt(tokens[4]));

            move = gameBoard.new Move (start, end, FanoronaGameBoard.Type.Withdraw);

            System.out.println("WITHDRAW!");
        }
        else if (tokens[0].equals("A"))
        {
            if (tokens.length != 5)
            {
                System.out.println("INVALID NUMBER OF ARGUMENTS. The approach move takes four arguments.");
                System.out.println("Be sure to consult Keyser's API on how to make moves.");

                return null;
            }

            Coordinate start = new Coordinate (Integer.parseInt(tokens[1]), 
                Integer.parseInt(tokens[2]));
            Coordinate end = new Coordinate (Integer.parseInt(tokens[3]), 
                Integer.parseInt(tokens[4]));

            move = gameBoard.new Move (start, end, FanoronaGameBoard.Type.Approach);

            System.out.println("APPROACH!");
        }
        else
        {
            System.out.println("INVALID COMMAND. Available commands are P, A, W, and S.");
            System.out.println("Be sure to consult Keyser's API on how to make moves.");

            return null;
        }

        return move;
    }

    /////////////////////////////////////
    // PRINTING FUNCTIONS
    /////////////////////////////////////
    
    	//print all valid moves
	public void printAllValidMoves(FanoronaGameBoard fgb)
	{
		List<FanoronaGameBoard.Move> moves = fgb.getAllPossibleMoves();

		for (FanoronaGameBoard.Move move : moves)
			System.out.println(move.toString());
	}

	//print valid moves for a point
	public void printValidMoves(FanoronaGameBoard fgb, Coordinate coord)
	{
		List<FanoronaGameBoard.Move> moves = fgb.getPossibleMoves(coord);

		for (FanoronaGameBoard.Move move : moves)
			System.out.println(move.toString());
	}

	public FanoronaGameBoard.MoveResult printBoardMove(FanoronaGameBoard fgb, FanoronaGameBoard.Move move, boolean showLines)
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
	public List<String> buildBoardStringList(FanoronaGameBoard fgb)
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
}
