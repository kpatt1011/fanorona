public final class Coordinate
{
	public final Pair<Integer, Integer> pair;
	public final Integer x;
	public final Integer y;


	public Coordinate(Integer x, Integer y)
	{
		this.pair = new Pair<Integer, Integer>(x,y);
		this.x = pair.first;
		this.y = pair.second;
	}

	public Coordinate(Pair<Integer, Integer> pair)
	{
		this.pair = pair;
		this.x = pair.first;
		this.y = pair.second;
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
