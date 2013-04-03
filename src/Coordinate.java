/*

Authored By: Benjamin Martin

*/
public final class Coordinate
{
	public final Pair<Integer, Integer> pair;
	public final Integer x;
	public final Integer y;


	public Coordinate(Integer x, Integer y)
	{
		this.pair = new Pair<Integer, Integer>(x.intValue(),y.intValue());
		this.x = x.intValue();
		this.y = y.intValue();
	}

	public Coordinate(Pair<Integer, Integer> pair)
	{
		this(pair.first, pair.second);
	}

	public Coordinate(Coordinate coord)
	{
		this(coord.x, coord.y);
	}

	public Coordinate(String s)
	{
		String[] numbers = new String[2];
		int middle = s.indexOf(',');

		numbers[0] = s.substring(1, middle);
		numbers[1] = s.substring(middle + 1, s.length() - 1);

		Integer X = Integer.parseInt(numbers[0]);
		Integer Y = Integer.parseInt(numbers[1]);

		this.pair = new Pair<Integer, Integer>(X.intValue(), Y.intValue());
		this.x = X.intValue();
		this.y = Y.intValue();
	}

	public boolean equals(Coordinate other)
	{
		return pair.equals(other.pair);
	}

	public String toString()
	{
		return pair.toString();
	}

}
