package edu.fullsail.mgems.cse.pather.ramoslebronanthony;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.constraint.solver.widgets.WidgetContainer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.util.AttributeSet;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by TheNinjaFS1 on 8/2/17.
 */

public class DrawSurface extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener
{

    Paint p = new Paint();

    public DrawSurface(Context context) {
        super(context);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        init(context);
    }
    public DrawSurface(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        init(context);
    }
    public DrawSurface(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        init(context);
    }

    private void init(Context _contex)
    {
        //Init Game Stuff Herr


        //Make sure onDraw is called when I touch screen
        setWillNotDraw(false);
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        //Draw the field Here
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder)
    {
        //System.out.println("surfaceCreating");
        Canvas canvas = null;
        try
        {




            synchronized (surfaceHolder)
            {
                System.out.println("drawing");
                //onDraw(canvas);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("errorCreating");
        }
        finally
        {
            if (canvas != null)
            {
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2)
    {
        System.out.println("drawing");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder)
    {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent)
    {

        //System.out.println("Touching Surface View");
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
        {
            //DoSomething Here


            invalidate();
        }
        return false;
    }


    public void update()
    {

    }



}
