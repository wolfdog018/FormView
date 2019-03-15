package com.example.formview;

import android.app.TimePickerDialog;
import android.database.Cursor;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    Datahelp dbhelp;
    TextView eid, ename, timetext;
    Button button, viewBut, buttonfortp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbhelp = new Datahelp(this);

        eid = findViewById(R.id.edit_id);
        ename = findViewById(R.id.edit_sname);
        button = findViewById(R.id.button);
        viewBut = findViewById(R.id.viewbut);
        buttonfortp = findViewById(R.id.buttonfortp);
        timetext = findViewById(R.id.timetext);


        buttonfortp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timepicker = new Timepicker();
                timepicker.show(getSupportFragmentManager(), "timepicker");

            }
        });



        setButton();
        viewButton();
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        timetext.setText( + hourOfDay + " : " + minute);

    }

    public void setButton(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isinser = dbhelp.insert(eid.getText().toString(), ename.getText().toString(), timetext.getText().toString());

                if(isinser = true){ Toast.makeText(MainActivity.this,"OK",Toast.LENGTH_LONG).show(); }
                else {Toast.makeText(MainActivity.this,"Not Ok",Toast.LENGTH_LONG).show();}
            }
        });
    }

    public void viewButton(){
        viewBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = dbhelp.getView();
                if (res.getCount() == 0){
                    show("Error","Nothing");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("ID : " +res.getString(0)+"\n");
                    buffer.append("Name : " +res.getString(1)+"\n");
                    buffer.append("Time : " +res.getString(2)+"\n\n\n\n");

                }
                show("Data",buffer.toString());

            }
        });

    }

    public void show(String title, String mssg){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(mssg);
        builder.show();
    }

}
