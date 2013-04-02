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

	public boolean equals(Coordinate other)
	{
		return pair.equals(other.pair);
	}

	public String toString()
	{
		return pair.toString();
	}

}
