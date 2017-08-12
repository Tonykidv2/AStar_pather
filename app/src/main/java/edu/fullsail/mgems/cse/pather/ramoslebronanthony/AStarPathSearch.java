package edu.fullsail.mgems.cse.pather.ramoslebronanthony;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by TheNinjaFS1 on 8/7/17.
 */

public class AStarPathSearch {

    private ArrayList<SearchNode> m_Tiles;
    private HashMap<NavCell, SearchNode> m_SearchGraph;
    private HashMap<NavCell, PlannerNode> m_VisitedList;
    private PriorityQueue<PlannerNode> m_OpenEnhancer;

    private SearchNode m_Start;
    private SearchNode m_End;

    private boolean m_FoundIt;
    private float hWeight = 1.2f;

    boolean Init(NavCell _tileList[][], int Row, int Column)
    {
        //This is not C++ anymore I got to call new now
        m_Tiles = new ArrayList<SearchNode>();
        m_SearchGraph = new HashMap<NavCell, SearchNode>();

        int r = 0;
        int c = 0;
        //Creating my SearchGraph
        //This is where I find all available neighbors for each cell
        try
        {
            for (r = 0; r < Row; r++) {
                for (c = 0; c < Column; c++) {

                    SearchNode newNode = new SearchNode();
                    newNode.m_Neighbors = new ArrayList<NavCell>();
                    newNode.m_Cell = _tileList[r][c];

                    //Finding Neighbors depending where they are on the grid
                    if (r == 0 && c == 0)
                    {
                        newNode.m_Neighbors.add(_tileList[r][c + 1]);
                        newNode.m_Neighbors.add(_tileList[r + 1][c]);
                    }
                    else if (c == Column - 1 && r == Row - 1)
                    {
                        newNode.m_Neighbors.add(_tileList[r][c - 1]);
                        newNode.m_Neighbors.add(_tileList[r - 1][c]);
                    }
                    else if (r == 0 && c == Column - 1) {
                        newNode.m_Neighbors.add(_tileList[r][c - 1]);
                        newNode.m_Neighbors.add(_tileList[r + 1][c]);
                    }
                    else if (r == Row - 1 && c == 0) {
                        newNode.m_Neighbors.add(_tileList[r][c + 1]);
                        newNode.m_Neighbors.add(_tileList[r - 1][c]);
                    }
                    else if (c == 0) {
                        newNode.m_Neighbors.add(_tileList[r + 1][c]);
                        newNode.m_Neighbors.add(_tileList[r - 1][c]);
                        newNode.m_Neighbors.add(_tileList[r][c + 1]);
                    }
                    else if (r == 0) {
                        newNode.m_Neighbors.add(_tileList[r + 1][c]);
                        newNode.m_Neighbors.add(_tileList[r][c + 1]);
                        newNode.m_Neighbors.add(_tileList[r][c - 1]);
                    }
                    else if (r == Row - 1) {
                        newNode.m_Neighbors.add(_tileList[r - 1][c]);
                        newNode.m_Neighbors.add(_tileList[r][c + 1]);
                        newNode.m_Neighbors.add(_tileList[r][c - 1]);
                    }
                    else if (c == Column - 1) {
                        newNode.m_Neighbors.add(_tileList[r + 1][c]);
                        newNode.m_Neighbors.add(_tileList[r - 1][c]);
                        newNode.m_Neighbors.add(_tileList[r][c - 1]);
                    }
                    else {
                        newNode.m_Neighbors.add(_tileList[r][c + 1]);
                        newNode.m_Neighbors.add(_tileList[r][c - 1]);
                        newNode.m_Neighbors.add(_tileList[r + 1][c]);
                        newNode.m_Neighbors.add(_tileList[r - 1][c]);
                    }

                    m_Tiles.add(newNode);
                }
            }
        }
        catch (Exception e)
        {
            //Something went wrong I'll print in the debugger where I goofed
            e.printStackTrace();
            System.out.println("Error Init in Astar at " + r + " " + c);
            return false;
        }
        for (int i = 0; i < m_Tiles.size(); i++)
        {
            m_SearchGraph.put(m_Tiles.get(i).m_Cell, m_Tiles.get(i));
        }
        return true;
    }

    void Enter (NavCell Start, NavCell End)
    {
        m_FoundIt = false;

        //Search in my searchgraph for my start and end goal
        for (int i = 0; i < m_Tiles.size(); i++)
        {
            if(m_Tiles.get(i).m_Cell == Start)
            {
                m_Start = m_Tiles.get(i);
            }
            if(m_Tiles.get(i).m_Cell == End)
            {
                m_End = m_Tiles.get(i);
            }
        }

        //Creating my first plannerNode to be put in the priority queue
        PlannerNode starter = new PlannerNode();
        starter.m_Parent = null;
        starter.m_Vertex = m_Start;

        //So this is how you create a Priority Queue in Java with a Comparator
        m_OpenEnhancer = new PriorityQueue<PlannerNode>(1, new SortingPlannerNodes());
        m_OpenEnhancer.add(starter);

        m_VisitedList = new HashMap<NavCell, PlannerNode>();
        m_VisitedList.put(m_Start.m_Cell, m_OpenEnhancer.peek());

        //.peek() is like .front() for unordered map
        m_OpenEnhancer.peek().m_HeuristicCost = Estimate(m_Start.m_Cell, m_End.m_Cell);
        m_OpenEnhancer.peek().m_GivenCost = 0;
        m_OpenEnhancer.peek().m_FinalCost = m_OpenEnhancer.peek().m_HeuristicCost * hWeight;
    }

    float Estimate(NavCell start, NavCell goal)
    {
        double x = (goal.getM_Bounds().centerX() - start.getM_Bounds().centerX());
        x *= x;
        double y = (goal.getM_Bounds().centerY() - start.getM_Bounds().centerY());
        y *= y;

        return (float)Math.sqrt(x + y);
    }

    boolean IsDone()
    {
        return m_FoundIt;
    }

    void update(long _timeslice)
    {
        long slicer = _timeslice;

        while (true)
        {
            PlannerNode current = m_OpenEnhancer.peek();

            m_OpenEnhancer.remove();

            //I found my Goal Cool!!!!
            if(current.m_Vertex == m_End)
            {
                m_VisitedList.put(current.m_Vertex.m_Cell, current);
                m_FoundIt = true;
                slicer = 0;
                return;
            }

            int size = current.m_Vertex.m_Neighbors.size();
            for (int i = 0; i < size; i++)
            {

                if(current.m_Vertex.m_Neighbors.get(i).m_Weight == 0)
                    continue;

                float tempGivenCost = current.m_GivenCost + Estimate(current.m_Vertex.m_Cell, current.m_Vertex.m_Neighbors.get(i))
                        * current.m_Vertex.m_Neighbors.get(i).m_Weight;

                if(!m_VisitedList.containsKey(current.m_Vertex.m_Neighbors.get(i)))
                {
                    SearchNode successor = m_SearchGraph.get(current.m_Vertex.m_Neighbors.get(i));
                    PlannerNode successorNode = new PlannerNode();

                    successorNode.m_Vertex = successor;
                    successorNode.m_Parent = current;
                    successorNode.m_GivenCost = tempGivenCost;
                    successorNode.m_HeuristicCost = Estimate(successor.m_Cell, m_End.m_Cell);
                    successorNode.m_FinalCost = successorNode.m_GivenCost + successorNode.m_HeuristicCost
                            * hWeight;

                    m_VisitedList.put(current.m_Vertex.m_Neighbors.get(i), successorNode);
                    m_OpenEnhancer.add(successorNode);
                }

                else
                {
                    if( tempGivenCost < m_VisitedList.get(current.m_Vertex.m_Neighbors.get(i)).m_GivenCost)
                    {
                        PlannerNode Succesor = m_VisitedList.get(current.m_Vertex.m_Neighbors.get(i));
                        m_OpenEnhancer.remove(Succesor);
                        Succesor.m_GivenCost = tempGivenCost;
                        Succesor.m_FinalCost = Succesor.m_GivenCost + Succesor.m_HeuristicCost * hWeight;
                        Succesor.m_Parent = current;
                        m_OpenEnhancer.add(Succesor);
                    }
                }
            }
        }
    }

    ArrayList<NavCell> Solution()
    {
        ArrayList<NavCell> result = null;

        //Looking to see if we even visited the Goal Cell
        PlannerNode walker = m_VisitedList.get(m_End.m_Cell);

        if(walker != null)
        {
            result = new ArrayList<NavCell>();
            result.add(walker.m_Vertex.m_Cell);
            walker = walker.m_Parent;
            while(walker != null)
            {
                result.add(walker.m_Vertex.m_Cell);
                walker = walker.m_Parent;
            }
        }

        //Actually returns the path backwards
        return result;
    }

    void Exit()
    {
        m_Tiles.clear();
        m_SearchGraph.clear();
        m_OpenEnhancer.clear();
        m_VisitedList.clear();

    }
}
