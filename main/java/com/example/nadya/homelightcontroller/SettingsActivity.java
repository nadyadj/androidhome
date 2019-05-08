package com.example.nadya.homelightcontroller;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SettingsActivity extends AppCompatActivity {

    EditText etTeras, etBedroom, etLiving, etCloset;
    Button btnSet, btnClear;

    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference myRef= mRef.child("lampu");
    DatabaseReference history= mRef.child("history");

//    FirebaseDatabase database = FirebaseDatabase.getInstance();
//    DatabaseReference myRef = database.getReference();

    final DatabaseReference watt1 = myRef.child("lamp1").child("watt");
    final DatabaseReference watt2 = myRef.child("lamp2").child("watt");
    final DatabaseReference watt3 = myRef.child("lamp3").child("watt");
    final DatabaseReference watt4 = myRef.child("lamp4").child("watt");

    Date c = Calendar.getInstance().getTime();
    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
    String formattedDate = df.format(c);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        etTeras = findViewById(R.id.et_watt1);
        etBedroom = findViewById(R.id.et_watt2);
        etLiving = findViewById(R.id.et_watt3);
        etCloset = findViewById(R.id.et_watt4);
        btnSet = findViewById(R.id.btn_set);
        btnClear = findViewById(R.id.btn_clear);

        watt1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long value = dataSnapshot.getValue(Long.class);
                Log.d("file", "Value is: " + value);
                etTeras.setText(value.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("file", "Failed to read value.", databaseError.toException());
            }
        });

        watt2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long value = dataSnapshot.getValue(Long.class);
                Log.d("file", "Value is: " + value);
                etBedroom.setText(value.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("file", "Failed to read value.", databaseError.toException());
            }
        });

        watt3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long value = dataSnapshot.getValue(Long.class);
                Log.d("file", "Value is: " + value);
                etLiving.setText(value.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("file", "Failed to read value.", databaseError.toException());
            }
        });

        watt4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long value = dataSnapshot.getValue(Long.class);
                Log.d("file", "Value is: " + value);
                etCloset.setText(value.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("file", "Failed to read value.", databaseError.toException());
            }
        });

        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value1 = etTeras.getText().toString();
                long val1 = Long.parseLong(value1);
                String value2 = etBedroom.getText().toString();
                long val2 = Long.parseLong(value2);
                String value3 = etLiving.getText().toString();
                long val3 = Long.parseLong(value3);
                String value4 = etCloset.getText().toString();
                long val4 = Long.parseLong(value4);

                if(val1 <= 0 || val2 <= 0 || val3 <= 0 || val4 <=0 || val1 > 100 || val2 >  100 || val3 > 100 || val4 > 100){
                    AlertDialog alertDialog = new AlertDialog.Builder(SettingsActivity.this).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("Range value for watt: 0 - 100");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    alertDialog.show();
                }else{
                    watt1.setValue(val1);
                    watt2.setValue(val2);
                    watt3.setValue(val3);
                    watt4.setValue(val4);
                    Toast.makeText(SettingsActivity.this, "DONE!", Toast.LENGTH_SHORT).show();
                }
            }
        });



        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SettingsActivity.this);
                alertDialog.setTitle("WARNING");
                alertDialog.setMessage("All of your history data will be cleared. Are you sure?");
                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SettingsActivity.this);
                        alertDialog.setTitle("WARNING");
                        alertDialog.setMessage("Are you sure?");
                        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                history.removeValue();
                            }
                        });
                        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        alertDialog.create().show();
                    }
                });
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alertDialog.create().show();
            }
        });
    }
}
