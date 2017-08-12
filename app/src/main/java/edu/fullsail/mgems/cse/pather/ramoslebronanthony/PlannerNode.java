package edu.fullsail.mgems.cse.pather.ramoslebronanthony;

import java.util.Comparator;

/**
 * Created by TheNinjaFS1 on 8/7/17.
 */

public class PlannerNode {

    //For Advance Path Searching
    //For PathSearch Class
    public SearchNode m_Vertex;
    public PlannerNode m_Parent;


    public float m_HeuristicCost;
    public float m_GivenCost;
    public float m_FinalCost;

}
