/*

Authored By: Benjamin Martin

*/
import java.lang.reflect.*;
import java.util.*;

public final class Point
{
	public enum State { isOccupiedByBlack, isOccupiedByWhite, isOccupiedBySacrifice, isEmpty }

	private State state;
	private final Coordinate coordinate;

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

	public boolean equals(Object that)
	{
		if ( this == that )
			return true;
		if ( !(that instanceof Point) )
			return false;
		return this.state == ((Point) that).state;
	}

	//easily recognizable Unicode Shapes
	public String toString()
	{
		switch(state)
		{
			case isOccupiedByBlack: 	return "●";
			case isOccupiedByWhite: 	return "○";
			case isOccupiedBySacrifice:	return "◍";
			case isEmpty:           	return "▫";
			default:                	return "?";
		}
	}

	//Text interchange character override
	public String toString(String white, String black, String sacrifice, String empty)
	{
		switch(state)
		{
			case isOccupiedByBlack: 	return black;
			case isOccupiedByWhite: 	return white;
			case isOccupiedBySacrifice:	return sacrifice;
			case isEmpty:           	return empty;
			default:                	return "?";
		}
	}

}
