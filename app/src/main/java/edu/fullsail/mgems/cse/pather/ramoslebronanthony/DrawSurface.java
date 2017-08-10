package edu.fullsail.mgems.cse.pather.ramoslebronanthony;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
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
    private NavCell m_TileMap[][];
    private ArrayList<NavCell> m_path = null;
    private NavCell m_CellStart;
    private NavCell m_CellEnd;
    private Bitmap m_StartIcon;
    private Bitmap m_EndIcon;
    private Bitmap m_Shakey;
    private int m_MapCollumn;
    private int m_MapRows;
    private float m_ScreenHeight;
    private float m_ScreenWidth;
    private float m_CellSize = 100;

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
        //Init Game Stuff Here
        m_StartIcon = BitmapFactory.decodeResource(getResources(), R.drawable.start);
        m_EndIcon = BitmapFactory.decodeResource(getResources(), R.drawable.end);
        m_Shakey = BitmapFactory.decodeResource(getResources(), R.drawable.shakey);
        //Make sure onDraw is called when I touch screen
        setWillNotDraw(false);
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        //Draw the field Here
        super.onDraw(canvas);
        int midRow = m_MapCollumn / 2;
        canvas.drawColor(Color.WHITE);
        for (int j=0; j<m_MapRows; j++)
        {
            for (int i=0; i<m_MapCollumn; i++)
            {
                if(m_TileMap[j][i].m_Weight == 0)
                {
                    p.setARGB(255,0,0,0);
                    p.setStyle(Paint.Style.FILL);
                    canvas.drawRect(m_TileMap[j][i].getM_Bounds(), p);
                    continue;
                }

                p.setColor(Color.BLACK);
                p.setStyle(Paint.Style.STROKE);
                canvas.drawCircle(m_TileMap[j][i].getM_Bounds().centerX(), m_TileMap[j][i].getM_Bounds().centerY(), 5, p);
                canvas.drawRect(m_TileMap[j][i].getM_Bounds(), p);

                if(m_TileMap[j][i].m_Weight > 0 && m_TileMap[j][i].m_Weight < 1)
                {
                    p.setStyle(Paint.Style.FILL);
                    p.setARGB(255, (int)(255*m_TileMap[j][i].m_Weight), (int)(255*m_TileMap[j][i].m_Weight),
                            (int)(255*m_TileMap[j][i].m_Weight));
                    canvas.drawRect(m_TileMap[j][i].getM_Bounds(), p);
                }
            }
        }

        if(m_path != null)
        {
            for (int i = 0; i < m_path.size(); i++)
            {
                p.setColor(Color.GREEN);
                p.setStyle(Paint.Style.FILL);
                canvas.drawCircle(m_path.get(i).getM_Bounds().centerX(), m_path.get(i).getM_Bounds().centerY(), 5, p);
                canvas.drawRect(m_path.get(i).getM_Bounds(), p);
            }
        }
        if(m_CellStart != null && m_Shakey != null)
        {
            //How To draw the Pin Icons
            canvas.drawBitmap(m_StartIcon,m_CellStart.getM_Bounds().left + 12,
                    m_CellStart.getM_Bounds().top - (int)(m_CellSize/1.5), p);
        }
        else if(m_CellStart != null && m_StartIcon != null)
        {
            //How To draw the Shakey Icon
            canvas.drawBitmap(m_Shakey,m_TileMap[0][0].getM_Bounds().left - 20,
                    m_TileMap[0][0].getM_Bounds().top - 150, p);
        }

        if(m_CellEnd != null && m_EndIcon != null)
        {
            canvas.drawBitmap(m_EndIcon,m_CellEnd.getM_Bounds().left + 12,
                    m_CellEnd.getM_Bounds().top - (int)(m_CellSize/1.5), p);
        }

        if(m_path != null)
        {
            m_CellStart = m_CellEnd;
        }
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder)
    {
        //System.out.println("surfaceCreating");
        Canvas canvas = null;
        try
        {
            canvas = surfaceHolder.lockCanvas();
            m_ScreenHeight = canvas.getHeight();
            m_ScreenWidth = canvas.getWidth();

            InitMap();

            invalidate();
            synchronized (surfaceHolder)
            {
                System.out.println("Creating Screen");
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
            AStarPathSearch pather = new AStarPathSearch();
            int j,i;
            try
            {
                for (j = 0; j<m_MapRows; j++)
                {
                    for (i = 0; i<m_MapCollumn; i++)
                    {
                        if(m_TileMap[j][i].getM_Bounds().contains((int)(motionEvent.getX()), (int)(motionEvent.getY())))
                        {
                            m_CellEnd = m_TileMap[j][i];
                            break;
                        }
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                System.out.println("Looping Error");
            }

            try
            {
                if(pather.Init(m_TileMap, m_MapRows, m_MapCollumn))
                {
                    pather.Enter(m_CellStart, m_CellEnd);
                    pather.update(100);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                System.out.println("Error With Path Search");
            }
            if(pather.IsDone())
            {
                m_path = pather.Solution();
                invalidate();
            }
        }
        return false;
    }

    void InitMap()
    {
        m_MapCollumn = (int)Math.ceil((double)m_ScreenWidth / (double)m_CellSize);
        m_MapRows = (int)Math.ceil((double)m_ScreenHeight / (double)m_CellSize);

        m_TileMap = new NavCell[m_MapRows][m_MapCollumn];
        for (int j = 0; j < m_MapRows; j++)
        {
            for (int i = 0; i < m_MapCollumn; i++)
            {
                m_TileMap[j][i] = new NavCell();
                m_TileMap[j][i].setM_Bounds(new Rect((int)(i*m_CellSize), (int)(j*m_CellSize),
                        (int)((i*m_CellSize) + m_CellSize), (int)((j*m_CellSize) + m_CellSize)));
            }
        }

        m_CellStart = m_TileMap[m_MapRows/4][m_MapCollumn/2];
        m_CellEnd = null;

        int midRow = m_MapRows / 2;
        for (int j=0; j<m_MapRows; j++)
            for (int i=0; i<m_MapCollumn; i++)
            {
                if(j == midRow && i>0 && i<m_MapCollumn-1)
                {
                    m_TileMap[j][i].m_Weight = 0;
                }
                else
                {
                    m_TileMap[j][i].m_Weight = 1;
                }
            }

    }

    public void update()
    {

    }



}
