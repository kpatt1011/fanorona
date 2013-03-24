import java.util.*;

interface MinimaxNode
{
    void populateChildren (int depth);

    ArrayList<? extends MinimaxNode> getChildren ();

    public Vector<Vector<Point>> getGameBoard ();

    boolean isLeaf ();

    int value ();

    public boolean isMaxNode ();
    public boolean isMinNode ();
}

class MinNode implements MinimaxNode
{
    // DATA MEMBERS
	private ArrayList<MaxNode> children;
    private Vector<Vector<Point>> gameBoard = new Vector<Vector<Point>>();
	
    // PUBLIC FUNCTIONS
	public MinNode (Vector<Vector<Point>> newGameBoard)
	{
		gameBoard = newGameBoard;
	}
	
    public void populateChildren (int depth)
    {
        // get all possible board states from current moves
        // iterate across all of them and addChild()
        // if depth > 0, call populateChildren(depth - 1) on all children
    }

    public ArrayList<MaxNode> getChildren ()
    {
        return children;
    }

    public Vector<Vector<Point>> getGameBoard ()
    {
        return gameBoard;
    }
	
	public boolean isLeaf ()
	{
		return children.isEmpty();
	}
	
	public int value ()
	{
		if (isLeaf())
		{
			// board evaluation function goes here
			return 0;
		}
		else
		{
			int value = children.get(0).value();
			for (int i = 0; i < children.size(); i++)
			{
				value = Math.min(value, children.get(i).value());
			}
			
			return value;
		}
	}

    public boolean isMaxNode ()
    {
        return false;
    }

    public boolean isMinNode ()
    {
        return true;
    }

    // HELPER FUNCTIONS
	private void addChild (MaxNode newChild)
	{
		children.add(newChild);
	}

}

class MaxNode implements MinimaxNode
{
    // DATA MEMBERS
	private ArrayList<MinNode> children;
    private Vector<Vector<Point>> gameBoard = new Vector<Vector<Point>>();
	
    // PUBLIC FUNCTIONS
	public MaxNode (Vector<Vector<Point>> newGameBoard)
	{
        gameBoard = newGameBoard;
    }
	
    public void populateChildren (int depth)
    {
        // get all possible board states from current moves
        // iterate across all of them and addChild()
        // if depth > 0, call populateChildren(depth - 1) on all children
    }
	
    public ArrayList<MinNode> getChildren ()
    {
        return children;
    }
	
    public Vector<Vector<Point>> getGameBoard ()
    {
        return gameBoard;
    }
	
	public boolean isLeaf ()
	{
		return children.isEmpty();
	}
	
	public int value ()
	{
		if (isLeaf())
		{
			// board evaluation function goes here
			return 0;
		}
		else
		{
			int value = children.get(0).value();
			for (int i = 0; i < children.size(); i++)
			{
				value = Math.max(value, children.get(i).value());
			}
			
			return value;
		}
	}

    public boolean isMaxNode ()
    {
        return true;
    }

    public boolean isMinNode ()
    {
        return false;
    }

    // HELPER FUNCTIONS
	private void addChild (MinNode newChild)
	{
		children.add(newChild);
	}
}
