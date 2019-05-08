package com.example.nadya.homelightcontroller;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class SetTimerActivity extends AppCompatActivity {

    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference myRef= mRef.child("lampu");

    final DatabaseReference timerOnHour = myRef.child("lamp1").child("onHour");
    final DatabaseReference timerOnMin = myRef.child("lamp1").child("onMin");
    final DatabaseReference timerOffHour = myRef.child("lamp1").child("offHour");
    final DatabaseReference timerOffMin = myRef.child("lamp1").child("offMin");
//    final DatabaseReference timerStatus = myRef.child("lamp1").child("timerStatus");
    final DatabaseReference timerStatusOn = myRef.child("lamp1").child("timerStatusOn");
    final DatabaseReference timerStatusOff = myRef.child("lamp1").child("timerStatusOff");

    EditText timeOn, timeOff;
//    Button setTimerOnOff, resetTimer, setTimerOn, setTimerOff;
    ToggleButton setTimerOn, setTimerOff;
    private int onHour, onMin, offHour, offMin;

    int getOnHour, getOnMin, getOffHour, getOffMin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_timer);

        timeOn = findViewById(R.id.et_setTimerOn);
        timeOff = findViewById(R.id.et_setTimerOff);
        setTimerOn = findViewById(R.id.toggle_on);
//        setTimerOn.setChecked(false);
        setTimerOff = findViewById(R.id.toggle_off);
//        setTimerOff.setChecked(false);

        timerOnHour.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getOnHour = dataSnapshot.getValue(int.class);
                Log.d("file", "onHour is: " + getOnHour);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        timerOnMin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getOnMin = dataSnapshot.getValue(int.class);
                Log.d("file", "onMin is: " + getOnMin);
                timeOn.setText(convertDate(getOnHour)+ ":" + convertDate(getOnMin));
                Log.d("file", "getOnHour is: " + getOnHour + " getOnMin is: " + getOnMin);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        timerOffHour.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getOffHour = dataSnapshot.getValue(int.class);
                Log.d("file", "onHour is: " + getOffHour);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        timerOffMin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getOffMin = dataSnapshot.getValue(int.class);
                Log.d("file", "onMin is: " + getOffMin);
                timeOff.setText(convertDate(getOffHour)+ ":" + convertDate(getOffMin));
                Log.d("file", "getOnHour is: " + getOffHour + " getOnMin is: " + getOffMin);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        timerStatusOn.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String statOn = dataSnapshot.getValue(String.class);
                if(statOn.equals("ON")){
                    setTimerOn.setChecked(true);
                }else if(statOn.equals("OFF")){
                    setTimerOn.setChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        timerStatusOff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String statOff = dataSnapshot.getValue(String.class);
                if(statOff.equals("ON")){
                    setTimerOff.setChecked(true);
                }else if(statOff.equals("OFF")){
                    setTimerOff.setChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        timeOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                onHour = c.get(Calendar.HOUR_OF_DAY);
                onMin = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(SetTimerActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        timeOn.setText(convertDate(hour) + ":" + convertDate(minute));
                    }
                }, onHour, onMin, true);
                timePickerDialog.show();
            }
        });

        timeOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                offHour = c.get(Calendar.HOUR_OF_DAY);
                offMin = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(SetTimerActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        timeOff.setText(convertDate(hour) + ":" + convertDate(minute));
                    }
                }, offHour, offMin, true);
                timePickerDialog.show();
            }
        });

        setTimerOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(setTimerOn.isChecked()){
                    if(!TextUtils.isEmpty(timeOn.getText())) {
                        String valueonhour = timeOn.getText().toString().substring(0,2);
                        int val1 = Integer.parseInt(valueonhour);
                        timerOnHour.setValue(Integer.parseInt(convertDate(val1)));
                        String valueonmin = timeOn.getText().toString().substring(3);
                        int val2 = Integer.parseInt(valueonmin);
                        timerOnMin.setValue(Integer.parseInt(convertDate(val2)));
                        setTimerOn.setChecked(true);
                        timerStatusOn.setValue("ON");
                        Toast.makeText(SetTimerActivity.this, "TIMER ON DIPASANG", Toast.LENGTH_SHORT).show();
                    }else if (TextUtils.isEmpty(timeOn.getText())){
                        setTimerOn.setChecked(false);
                        AlertDialog alertDialog = new AlertDialog.Builder(SetTimerActivity.this).create();
                        alertDialog.setTitle("Reminder");
                        alertDialog.setMessage("Please input the timer value");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        alertDialog.show();
                    }
                }else{
                    setTimerOn.setChecked(false);
                    timerStatusOn.setValue("OFF");
                    Toast.makeText(SetTimerActivity.this, "TIMER ON DIMATIKAN", Toast.LENGTH_SHORT).show();
                }
            }
        });

        setTimerOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(setTimerOff.isChecked()){
                    if(!TextUtils.isEmpty(timeOff.getText())) {
                        String valueonhour = timeOff.getText().toString().substring(0,2);
                        int val1 = Integer.parseInt(valueonhour);
                        timerOffHour.setValue(Integer.parseInt(convertDate(val1)));
                        String valueonmin = timeOff.getText().toString().substring(3);
                        int val2 = Integer.parseInt(valueonmin);
                        timerOffMin.setValue(Integer.parseInt(convertDate(val2)));
                        setTimerOff.setChecked(true);
                        timerStatusOff.setValue("ON");
                        Toast.makeText(SetTimerActivity.this, "TIMER OFF DIPASANG", Toast.LENGTH_SHORT).show();
                    }else if (TextUtils.isEmpty(timeOff.getText())){
                        setTimerOff.setChecked(false);
                        AlertDialog alertDialog = new AlertDialog.Builder(SetTimerActivity.this).create();
                        alertDialog.setTitle("Reminder");
                        alertDialog.setMessage("Please input the timer value");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        alertDialog.show();
                    }
                }else{
                    setTimerOff.setChecked(false);
                    timerStatusOff.setValue("OFF");
                    Toast.makeText(SetTimerActivity.this, "TIMER OFF DIMATIKAN", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public String convertDate(long input) {
        if (input >= 10) {
            return String.valueOf(input);
        } else {
            return "0" + String.valueOf(input);
        }
    }
}
