import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Vector;

public class FanoronaGameBoardTest {

	//Test that a board can be initialized
	@Test
	public void test_create()
	{
		FanoronaGameBoard fgb = new FanoronaGameBoard();

		String s = fgb.toString();

		String testString =
		"B B B B B B B B B\n" +
		"B B B B B B B B B\n" +
		"B W B W E B W B W\n" +
		"W W W W W W W W W\n" +
		"W W W W W W W W W\n";

		/*
		System.out.println("--------------");
		System.out.print(s);
		System.out.println("--------------");
		System.out.print(testString);
		System.out.println("--------------");
		*/

		assertTrue(s.equals(testString));
	}

	//Test that a piece can be moved nievely
	@Test
	public void test_move()
	{
		FanoronaGameBoard fgb = new FanoronaGameBoard();

		fgb.movePiece(new Coordinate(3,4), new Coordinate(2,4));

		String testString =
		"B B B B B B B B B\n" +
		"B B B B B B B B B\n" +
		"B W B W W B W B W\n" +
		"W W W W E W W W W\n" +
		"W W W W W W W W W\n";

		assertTrue(fgb.toString().equals(testString));

		fgb.movePiece(new Coordinate(1,4), new Coordinate(2,4));

		testString =
		"B B B B B B B B B\n" +
		"B B B B E B B B B\n" +
		"B W B W B B W B W\n" +
		"W W W W E W W W W\n" +
		"W W W W W W W W W\n";

		assertTrue(fgb.toString().equals(testString));

	}

	//Test that the getBoard function.
	// TODO: make unmodifiable
	@Test
	public void test_get()
	{
		FanoronaGameBoard fgb = new FanoronaGameBoard();

		Vector<Vector<Point>> board = fgb.getBoard();

		for (int i = 0; i < board.size(); i++)
		{
			Vector<Point> row = board.get(i);

			for (int j = 0; j < row.size(); j++)
				assertTrue( board.get(i).get(j).equals(board.get(i).get(j)) );
		}
	}

}
