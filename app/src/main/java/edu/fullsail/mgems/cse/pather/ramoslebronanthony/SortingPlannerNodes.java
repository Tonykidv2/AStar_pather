package edu.fullsail.mgems.cse.pather.ramoslebronanthony;

import java.util.Comparator;

/**
 * Created by TheNinjaFS1 on 8/10/17.
 */

public class SortingPlannerNodes implements Comparator<PlannerNode> {

    @Override
    public int compare(PlannerNode plannerNode, PlannerNode t1)
    {
        if(plannerNode.m_FinalCost < t1.m_FinalCost)
            return -1;
        if(plannerNode.m_FinalCost > t1.m_FinalCost)
            return 1;


        return 0;
    }
}
