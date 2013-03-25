import java.util.*;

interface MinimaxNode
{
    void populateChildren (int depth);

    ArrayList<? extends MinimaxNode> getChildren ();

    public LinkedList<LinkedList<Point>> getGameBoard ();

    boolean isLeaf ();

    public int evaluateValue (int parentVal);
    public int getValue (int parentVal);
    public int getValue ();

    public boolean isMaxNode ();
    public boolean isMinNode ();

    public void print ();
}

class MinNode implements MinimaxNode
{
    // DATA MEMBERS
	private ArrayList<MaxNode> children = new ArrayList<MaxNode>();
    private LinkedList<LinkedList<Point>> gameBoard = new LinkedList<LinkedList<Point>>();
    private int value;
	
    // PUBLIC FUNCTIONS
	public MinNode (LinkedList<LinkedList<Point>> newGameBoard)
	{
		gameBoard = newGameBoard;
        value = Integer.MAX_VALUE;
	}

	public MinNode (LinkedList<LinkedList<Point>> newGameBoard, int depth)
	{
		gameBoard = newGameBoard;
        value = Integer.MAX_VALUE;
        populateChildren(depth);
	}
	
    public void populateChildren (int depth)
    {
        // get all possible board states from current moves
        // iterate across all of them and addChild()
        // if depth > 0, call populateChildren(depth - 1) on all children
        if (depth > 0)
        {
            for (int i = 0; i < 3; i++)
            {
                LinkedList<LinkedList<Point>> blankBoard = new LinkedList<LinkedList<Point>>();
                MaxNode newNode = new MaxNode(blankBoard, depth-1); 

                children.add(newNode);
            }
        }
    }

    public ArrayList<MaxNode> getChildren ()
    {
        return children;
    }

    public LinkedList<LinkedList<Point>> getGameBoard ()
    {
        return gameBoard;
    }
	
	public boolean isLeaf ()
	{
		return children.isEmpty();
	}

	public int evaluateValue (int parentVal)
	{
		if (isLeaf())
		{
			// board evaluation function goes here
            value = 0;
			return value;
		}
		else
		{
			value = children.get(0).getValue(parentVal);
			for (int i = 1; i < children.size(); i++)
			{
                value = Math.min(value, children.get(i).getValue(parentVal));
                // alpha beta pruning
                if (value < parentVal)
                {
                    return Integer.MIN_VALUE;
                }
			}
			
			return value;
		}
	}

    public int getValue ()
    {
        evaluateValue(value);
        return value;
    }

    public int getValue (int parentVal)
    {
        if (value == Integer.MAX_VALUE)
        {
            evaluateValue(parentVal);
        }
        return value;
    }

    public boolean isMaxNode ()
    {
        return false;
    }

    public boolean isMinNode ()
    {
        return true;
    }

    public void print ()
    {
        if (isLeaf())
        {
            System.out.format("%d", value);
        }
        else
        {
            System.out.format("%d(", value);
            for (int i = 0; i < children.size(); i++)
            {
                children.get(i).print();
                if (i != children.size()-1)
                {
                    System.out.print(", ");
                }
            }
            System.out.print(")");
        }
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
	private ArrayList<MinNode> children = new ArrayList<MinNode>();
    private LinkedList<LinkedList<Point>> gameBoard = new LinkedList<LinkedList<Point>>();
    private int value;
	
    // PUBLIC FUNCTIONS
	public MaxNode (LinkedList<LinkedList<Point>> newGameBoard)
	{
        gameBoard = newGameBoard;
        value = Integer.MIN_VALUE;
    }
	
	public MaxNode (LinkedList<LinkedList<Point>> newGameBoard, int depth)
	{
        gameBoard = newGameBoard;
        value = Integer.MIN_VALUE;
        populateChildren(depth);
    }
	
    public void populateChildren (int depth)
    {
        // get all possible board states from current moves
        // iterate across all of them and addChild()
        // if depth > 0, call populateChildren(depth - 1) on all children
        if (depth > 0)
        {
            for (int i = 0; i < 3; i++)
            {
                LinkedList<LinkedList<Point>> blankBoard = new LinkedList<LinkedList<Point>>();
                MinNode newNode = new MinNode(blankBoard, depth-1); 

                children.add(newNode);
            }
        }
    }
	
    public ArrayList<MinNode> getChildren ()
    {
        return children;
    }
	
    public LinkedList<LinkedList<Point>> getGameBoard ()
    {
        return gameBoard;
    }
	
	public boolean isLeaf ()
	{
		return children.isEmpty();
	}

	public int evaluateValue (int parentVal)
	{
		if (isLeaf())
		{
			// board evaluation function goes here
            value = 0;
			return value;
		}
		else
		{
			value = children.get(0).getValue(parentVal);
			for (int i = 1; i < children.size(); i++)
			{
                value = Math.max(value, children.get(i).getValue(parentVal));
                // alpha beta pruning
                if (value > parentVal)
                {
                    return Integer.MAX_VALUE;
                }
			}
			
			return value;
		}
	}

    public int getValue ()
    {
        evaluateValue(value);
        return value;
    }

    public int getValue (int parentVal)
    {
        if (value == Integer.MIN_VALUE)
        {
            evaluateValue(parentVal);
        }
        return value;
    }
	
    public boolean isMaxNode ()
    {
        return true;
    }

    public boolean isMinNode ()
    {
        return false;
    }

    public void print ()
    {
        if (isLeaf())
        {
            System.out.format("%d", value);
        }
        else
        {
            System.out.format("%d(", value);
            for (int i = 0; i < children.size(); i++)
            {
                children.get(i).print();
                if (i != children.size()-1)
                {
                    System.out.print(", ");
                }
            }
            System.out.print(")");
        }
    }

    // HELPER FUNCTIONS
	private void addChild (MinNode newChild)
	{
		children.add(newChild);
	}
}
