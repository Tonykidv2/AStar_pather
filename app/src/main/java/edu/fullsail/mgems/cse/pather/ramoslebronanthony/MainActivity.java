package edu.fullsail.mgems.cse.pather.ramoslebronanthony;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener{

    AlertDialog.Builder _dialog;
    AlertDialog.Builder _dialog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Button minvButton;
        Button mcredButton;


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Making sure the listener is looking at the button
        mcredButton = ((Button)findViewById(R.id.credits));
        mcredButton.setOnTouchListener(this);

        //Making sure the listener is looking at the button
        minvButton = ((Button)findViewById(R.id.options));
        minvButton.setOnTouchListener(this);

        _dialog = new AlertDialog.Builder(this);
        _dialog.setTitle("Made by");
        _dialog.setMessage("Anthony Ramoslebron\nMGMS | APM\n8/12/2017");
        _dialog.setPositiveButton(" OK ", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface _dialog, int id)
            {
                _dialog.dismiss();
            }
        });
        _dialog.setNegativeButton(" Find Source Code ", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface _dialog, int id)
            {
                Intent _intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Tonykidv2/AStar_pather"));
                startActivity(_intent);
                _dialog.dismiss();
            }
        });

        _dialog2 = new AlertDialog.Builder(this);
        _dialog2.setTitle(" Choose an option ");
        CharSequence[] list = {" Change impassable blocks ", " Restart "};
        _dialog2.setItems(list, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface _dialog, int id)
            {
                ((DrawSurface)findViewById(R.id.DrawSurface)).AlterMap(id);
                _dialog.dismiss();
            }
        });
        _dialog2.setPositiveButton(" Cancel ", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface _dialog, int id)
            {
                _dialog.dismiss();
            }
        });

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN
                && view.getId() == R.id.credits)
        {
            _dialog.show();
        }
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN
                && view.getId() == R.id.options)
        {
            _dialog2.show();
        }
        return false;
    }

    void printThis(int i)
    {
        System.out.println(i);
    }
}
