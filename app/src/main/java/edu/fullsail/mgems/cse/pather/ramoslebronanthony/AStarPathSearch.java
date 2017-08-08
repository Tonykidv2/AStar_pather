package edu.fullsail.mgems.cse.pather.ramoslebronanthony;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by TheNinjaFS1 on 8/7/17.
 */

public class AStarPathSearch {

    ArrayList<SearchNode> m_Tiles;
    HashMap<NavCell, SearchNode> m_SearchGraph;

    Queue<PlannerNode> m_OpenList;
    HashMap<NavCell, PlannerNode> m_VisitedList;


    SearchNode m_Start;
    SearchNode m_End;

    boolean m_FoundIt;

    PriorityQueue<PlannerNode> m_OpenEnhancer;


    void Initaililizer(ArrayList<NavCell> _tileList, int Row, int Column)
    {

    }

    void Enter (NavCell Start, NavCell End)
    {

    }

    boolean IsDone()
    {
        return m_FoundIt;
    }

    void update(long _timeslice)
    {

    }

    ArrayList<NavCell> Solution()
    {
        ArrayList<NavCell> result = new ArrayList<NavCell>();



        return result;
    }

    void Exit()
    {
        m_Tiles.clear();
        m_SearchGraph.clear();
        m_OpenEnhancer.clear();
        m_OpenList.clear();
        m_VisitedList.clear();

    }
}
