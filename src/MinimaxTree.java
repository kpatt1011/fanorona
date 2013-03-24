import java.util.*;

class MinimaxTree
{
    // DATA MEMBERS
    private MinimaxNode root;

    // PUBLIC FUNCTIONS
    public MinimaxTree (MinimaxNode newRoot)
    {
        root = newRoot;
    }

    public MinimaxTree (MinimaxNode newRoot, int depth)
    {
        root = newRoot;
        populateTree(depth);
    }

    void populateTree (int depth)
    {
        root.populateChildren (depth);
    }

    Vector<Vector<Point>> getIdealMove ()
    {
        ArrayList<? extends MinimaxNode> rootChildren = root.getChildren();
        
        int currentValue = rootChildren.get(0).value();
        int maxIndex = 0;
        int minIndex = 0;
        int maxValue = currentValue;
        int minValue = currentValue;
        for (int i = 1; i < rootChildren.size(); i++)
        {
            currentValue = rootChildren.get(i).value();

            if (currentValue > maxValue)
            {
                maxValue = currentValue;
                maxIndex = i;
            }
            if (currentValue < minValue)
            {
                minValue = currentValue;
                minValue = i;
            }
        }

        if (root.isMaxNode())
        {
            return rootChildren.get(maxValue).getGameBoard();
        }
        else
        {
            return rootChildren.get(minValue).getGameBoard();
        }
    }
}