import java.lang.reflect.*;
import java.util.*;

public final class Point
{
	public enum State { isOccupiedByBlack, isOccupiedByWhite, isEmpty}

	private State state;

	public boolean isOccupied() { return state != State.isEmpty; }
	public State getState() { return state; }

	public Point() { state = State.isEmpty; }

	public Point( State state) { this.state = state; }
}
