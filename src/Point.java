import java.lang.reflect.*;
import java.util.*;

public final class Point
{
	public enum State { isOccupiedByBlack, isOccupiedByWhite, isEmpty}

	private State state;
	private final Coordinate coordinate;

	public boolean isOccupied()
	{
		return state != State.isEmpty;
	}

	public State getState()
	{
		return state;
	}

	public void setState(State state)
	{
		this.state = state;
	}

	public Coordinate getCoordinate()
	{
		return coordinate;
	}

	public Point(Coordinate coordinate)
	{
		this.coordinate = coordinate;
		this.state = State.isEmpty;
	}

	public Point(Coordinate coordinate, State state)
	{
		this(coordinate);
		this.state = state;
	}

	public Point(Point point)
	{
		this.coordinate = new Coordinate(point.coordinate);
		this.state = point.state;
	}

	public boolean equals(Object that)
	{
		if ( this == that )
			return true;
		if ( !(that instanceof Point) )
			return false;
		return this.state == ((Point) that).state;
	}

	public String toString()
	{
		switch(state)
		{
			case isOccupiedByBlack: return "●";
			case isOccupiedByWhite: return "○";
			case isEmpty:           return "▫";
			default:                return "?";
		}
	}

	public String toString(String w, String b, String e)
	{
		switch(state)
		{
			case isOccupiedByBlack: return b;
			case isOccupiedByWhite: return w;
			case isEmpty:           return e;
			default:                return "?";
		}
	}

}
