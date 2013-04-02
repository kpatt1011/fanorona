/*

Authored By: Benjamin Martin

*/
public final class Pair<T1, T2>
{
	public final T1 first;
	public final T2 second;

	public Pair( T1 first, T2 second)
	{
		this.first = first;
		this.second = second;
	}

	public Pair( Pair<T1, T2> pair)
	{
		this.first = pair.first;
		this.second = pair.second;
	}

	public boolean equals(Pair<T1, T2> other)
	{
		if ( this == other )
			return true;
		if ( !(other instanceof Pair) )
			return false;
		Pair<T1, T2> otherP = other;
		return ( (otherP.first).equals(this.first) && (otherP.second).equals(this.second) );

	}

	public String toString()
	{
		return "(" + first.toString() + "," + second.toString() + ")";
	}
}
