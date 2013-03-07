import static org.junit.Assert.*;
import org.junit.Test;

public class FanoronaGameBoardTest {

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

}
