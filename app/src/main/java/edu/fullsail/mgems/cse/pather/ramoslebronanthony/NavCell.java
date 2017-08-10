package edu.fullsail.mgems.cse.pather.ramoslebronanthony;

import android.graphics.Point;
import android.graphics.Rect;



/**
 * Created by TheNinjaFS1 on 8/7/17.
 */

//Used for Drawing
public class NavCell {

    private Rect m_Bounds;

    float m_Weight;

    /*NavCell (Rect _bounds, Point _center, float _weight)
    {
        m_Weight = _weight;
        m_Bounds = _bounds;
        m_Center = _center;
    }*/

    Rect getM_Bounds()
    {
        return m_Bounds;
    }

    void setM_Bounds(Rect _rect)
    {
        m_Bounds = _rect;
    }
}
