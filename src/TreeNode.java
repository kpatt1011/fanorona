import java.util.*;

class MinNode
{
	private ArrayList<MaxNode> children;
	
	public MinNode ()
	{
		
	}
	
	public void addChild (MaxNode newChild)
	{
		children.add(newChild);
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
}

class MaxNode
{
	private ArrayList<MinNode> children;
	
	public MaxNode ()
	{
		
	}
	
	public void addChild (MinNode newChild)
	{
		children.add(newChild);
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
}