package edu.fullsail.mgems.cse.pather.ramoslebronanthony;

import android.graphics.Point;
import android.graphics.Rect;



/**
 * Created by TheNinjaFS1 on 8/7/17.
 */

//Used for Drawing
public class NavCell {

    private Rect m_Bounds;
    private Point m_Center;

    NavCell (Rect _bounds, Point _center)
    {
        m_Bounds = _bounds;
        m_Center = _center;
    }

    Rect getM_Bounds()
    {
        return m_Bounds;
    }
    Point getM_Center()
    {
        return m_Center;
    }
}
