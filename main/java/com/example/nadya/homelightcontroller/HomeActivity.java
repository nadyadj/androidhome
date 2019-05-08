package com.example.nadya.homelightcontroller;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class HomeActivity extends AppCompatActivity {
    
    GridLayout mainGrid;
    TextView tvTeras, tvBedroom, tvLiving, tvCloset;

    EditText dimLamp2, dimLamp3;
    ImageButton btn_off;

    String sd, ed, day;
    long dateDiff, dateDiff2, dateDiff3, dateDiff4;

    Date dates;
    Date datee;
    Date dateOn1, dateOn2, dateOn3, dateOn4;

    public long dateOnLamp1, dateOnLamp2, dateOnLamp3, dateOnLamp4;
    public long dateOffLamp1, dateOffLamp2, dateOffLamp3, dateOffLamp4;

    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference myRef= mRef.child("lampu");
    DatabaseReference history = mRef.child("history");
//    FirebaseDatabase database = FirebaseDatabase.getInstance().getReference();
//    DatabaseReference myRef = database.child("lampu");
    final DatabaseReference statusLamp1 = myRef.child("lamp1").child("status");
    final DatabaseReference statusLamp2 = myRef.child("lamp2").child("status");
    final DatabaseReference statusLamp3 = myRef.child("lamp3").child("status");
    final DatabaseReference statusLamp4 = myRef.child("lamp4").child("status");

    final DatabaseReference dimmerLamp2 = myRef.child("lamp2").child("dimmer");
    final DatabaseReference dimmerLamp3 = myRef.child("lamp3").child("dimmer");

    final DatabaseReference pirStatus = myRef.child("lamp4").child("pirStatus");

    long duration1, duration2, duration3, duration4;

    Date c = Calendar.getInstance().getTime();
    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
    String formattedDate = df.format(c);
    SimpleDateFormat mf = new SimpleDateFormat("MMMM");
    String formattedMonth = mf.format(c);

    final DatabaseReference durationonLamp1 = history.child(formattedDate).child("duration1");
    final DatabaseReference durationonLamp2 = history.child(formattedDate).child("duration2");
    final DatabaseReference durationonLamp3 = history.child(formattedDate).child("duration3");
    final DatabaseReference durationonLamp4 = history.child(formattedDate).child("duration4");
    final DatabaseReference month = history.child(formattedDate).child("month");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mainGrid = findViewById(R.id.mainGrid);
        tvTeras = findViewById(R.id.tv_teras);
        tvBedroom = findViewById(R.id.tv_bed);
        tvLiving = findViewById(R.id.tv_living);
        tvCloset = findViewById(R.id.tv_closet);
        btn_off = findViewById(R.id.btn_off);

        setSingleEvent(mainGrid);

        durationonLamp1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    duration1 = dataSnapshot.getValue(long.class);
                    Log.d("file", "dur value lamp1: " + duration1);
                }else {
                    history.child(formattedDate).child("duration1").setValue(0);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        durationonLamp2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    duration2 = dataSnapshot.getValue(long.class);
                    Log.d("file", "dur value lamp2: " + duration2);
                }else {
                    history.child(formattedDate).child("duration2").setValue(0);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        durationonLamp3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    duration3 = dataSnapshot.getValue(long.class);
                    Log.d("file", "dur value lamp3: " + duration3);
                }else {
                    history.child(formattedDate).child("duration3").setValue(0);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        durationonLamp4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    duration4 = dataSnapshot.getValue(long.class);
                    Log.d("file", "dur value lamp4: " + duration4);
                }else {
                    history.child(formattedDate).child("duration4").setValue(0);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        month.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("month").exists()) {
                    history.child(formattedDate).child("month").setValue(formattedMonth);
                }else {
                    history.child(formattedDate).child("month").setValue(formattedMonth);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        statusLamp1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Log.d("file", "Value is: " + value);
                tvTeras.setText(value);
                if(value.equals("ON")){
//                    dateOn1 = Calendar.getInstance().get(Calendar.DATE);
//                    startTime = System.currentTimeMillis();
                    dateOnLamp1 = Calendar.getInstance().getTimeInMillis();
                    dateOn1 = Calendar.getInstance().getTime();
                    Log.d("file", "dateon :" + dateOnLamp1);
                    Log.d("file", "dateon getTime :" + dateOn1);
//                                startDate = Calendar.getInstance().getTime();
//                    Log.d("file", "startTime: " + startTime);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); // Set your date format
                    sd = sdf.format(dateOnLamp1);
                    Log.d("file", "startDate: " + sd);
                }else if (value.equals("OFF")) {
//                    dateOff1 = Calendar.getInstance().get(Calendar.DATE);
//                    endTime = System.currentTimeMillis();
                    dateOffLamp1 = Calendar.getInstance().getTimeInMillis();
                    Log.d("file", "dateoff :" + dateOffLamp1);
//                                endDate = Calendar.getInstance().getTime();
//                    Log.d("file", "endTime: " + endTime);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); // Set your date format
                    ed = sdf.format(dateOffLamp1);
                    Log.d("file", "endDate: " + ed);
                    if(sd == null){
                        Log.d("file", "its null");
                    }
                    else if (sd !=null){
//                        //Example for checking between days
//                        String date2 = "3/5/2019";
//                        String date1 = "1/5/2019";
//                        SimpleDateFormat sdff = new SimpleDateFormat("dd/MM/yyyy");
//                        try {
//                            dates = sdff.parse(date1);
//                            datee = sdff.parse(date2);
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
//                        dateDiff = datee.getTime() - dates.getTime();
                        dateDiff = dateOffLamp1 - dateOnLamp1;
                        datediff1();
                    }
                }
//                    Log.d("file", "dateoff: " + dateOff1 + "endtime: " + endTime);
//                    if (dateOff1==dateOn1){
//
//
//                    }else {
//                        SimpleDateFormat dfor = new SimpleDateFormat("dd-M-yyyy");
//                        String fd = dfor.format(c);
//                        Log.d("file", "Ini kalau beda tanggal");
//                        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm");
//                        String dateString = fd.toString() + " 23:59";
//                        Log.d("file", "dateString " + dateString);
//                        try{
//                            //formatting the dateString to convert it into a Date
//                            Date date = sdf.parse(dateString);
//                            Log.d("file","Given Time in milliseconds : "+date.getTime());
//                            Calendar calendar = Calendar.getInstance();
//                            //Setting the Calendar date and time to the given date and time
//                            calendar.setTime(date);
//                            Log.d("file", "Given Time in milliseconds : "+calendar.getTimeInMillis());
//
//                            timeIntervalBef = calendar.getTimeInMillis() - startTime;
//                            calendar.add(Calendar.DATE, -1);
//                            String prevday = df.format(calendar.getTime());
//                            Log.d("file", "prevday:" + prevday);
//
//                            durationOn = duration1 + timeIntervalBef;
//                            history.child(prevday).child("duration1").setValue(durationOn);
//
//                            timeIntervalAf = endTime - calendar.getTimeInMillis();
//                            durationOn = duration1 + timeIntervalAf;
//                            history.child(formattedDate).child("duration1").setValue(durationOn);
//                        }catch(ParseException e){
//                            e.printStackTrace();
//                        }
//                    }
            }
            public void datediff1(){
//                long days = TimeUnit.DAYS.convert(dateDiff, TimeUnit.MILLISECONDS);
                long days = dateDiff / (24*60*60*1000);
                dateDiff = dateDiff % (24*60*60*1000);
                long hours = dateDiff / (60*60*1000);
                dateDiff = dateDiff % (60*60*1000);
                long minutess = dateDiff / (60*1000);
                dateDiff = dateDiff % (60*1000);
                long seconds = dateDiff / 1000;
                Log.d("file", "days diff: " + days + " hours dif: " + hours + " mins diff: " + minutess + " seconds diff: " + seconds);

                if (days == 0){
                    Log.d("file", "days = 0");
                    timeInterval = dateOffLamp1 - dateOnLamp1;
                    Log.d("file", "time interval:" + timeInterval + " endtime: " + dateOffLamp1 + " starttime: " + dateOnLamp1);
                    durationOn = duration1 + timeInterval;
                    history.child(formattedDate).child("duration1").setValue(durationOn);
                }else if(days >= 1){
                    Log.d("file", "days > 0");
                    for (int i = 0; i<=days; i++){
                        Calendar calendar = Calendar.getInstance();
                        //Setting the Calendar date and time to the given date and time
                        calendar.setTime(Calendar.getInstance().getTime());
                        Log.d("file", "Given Time in milliseconds : "+calendar.getTimeInMillis());
                        calendar.add(Calendar.DATE, -i);
                        String prevday = df.format(calendar.getTime());
                        Log.d("file", "prev days:" + prevday);
                        if (i == 0){
//                            long millis = dateOffLamp1;
//                            Log.d("file", "millis rusak:" + millis);
//
//                            String dateStop = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
//                                    TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
//                                    TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
                            String dateStart = "00:00:00";
//                            Log.d ("file", "datestop:" + dateStop);
                            SimpleDateFormat sdff = new SimpleDateFormat("HH:mm:ss");
                            try {
                                dates = sdff.parse(dateStart);
//                                datee = sdff.parse(dateStop);
                                Log.d("file", "dates: " + dates + " datee: " + datee);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

//                            dateDiff = datee.getTime() - dates.getTime();
                            dateDiff = dateOffLamp1 - dates.getTime();

                            Log.d("file", "dateDiff: " + dateDiff);
                            long diffSeconds = dateDiff / 1000 % 60;
                            long diffMinutes = dateDiff / (60 * 1000) % 60;
                            long diffHours = dateDiff / (60 * 60 * 1000) % 24;
                            Log.d("file", " hours dif: " + diffHours + " mins diff: " + diffMinutes + " seconds diff: " + diffSeconds);

                            long durmil = (diffHours*60*60*1000) + (diffMinutes*60*1000) + (diffSeconds*1000);
                            Log.d("file", "milliseconds: " + durmil);

                            durationOn = duration1 + durmil;
                            history.child(prevday).child("duration1").setValue(durationOn);
                        } else if(i == days){
                            long millis = dateOnLamp1;
                            Log.d("file", "millis rusak:" + millis);
                            String dateStart = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                                    TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                                    TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
                            Log.d("file", "dateStart: " + dateStart);

                            String dateStop = "00:00:00";
                            SimpleDateFormat sdff = new SimpleDateFormat("HH:mm:ss");
                            try {
                                dates = sdff.parse(dateStart);
                                datee = sdff.parse(dateStop);
                                Log.d("file", "datee: " + datee + " dates: " + dates);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
//                            dateDiff = datee.getTime() - dateOnLamp1;
//                            dateDiff = dateOnLamp1 - datee.getTime();
                            dateDiff = dates.getTime() - datee.getTime();
                            Log.d("file", "dateDiff: " + dateDiff);
                            long diffSeconds = dateDiff / 1000 % 60;
                            long diffMinutes = dateDiff / (60 * 1000) % 60;
                            long diffHours = dateDiff / (60 * 60 * 1000) % 24;
                            Log.d("file", " hours dif: " + diffHours + " mins diff: " + diffMinutes + " seconds diff: " + diffSeconds);

                            long durmil = (diffHours*60*60*1000) + (diffMinutes*60*1000) + (diffSeconds*1000);
                            Log.d("file", "milliseconds: " + durmil);

                            durationOn = duration1 + durmil;
                            history.child(prevday).child("duration1").setValue(durationOn);
                        } else{
                            history.child(prevday).child("duration1").setValue(24*60*60*100);
                        }
                    }
                }
            }
            public void checkdate() {
//                if(sd == null){
//                    Log.d("file", "its null");
//                }
//                else if (ed !=null){
//                    if(sd.compareTo(ed) == 0) {
//                        timeInterval = endTime - startTime;
//                        Log.d("file", "interval: " + timeInterval);
//                        durationOn = duration1 + timeInterval;
//                        Log.d("file", "dur: " + duration1 + " durOn: " + durationOn);
//
//                        history.child(formattedDate).child("duration1").setValue(durationOn);
//                        Log.d("file", "duration on: " + durationOn);
//                        Log.d("file", "ini sama tanggal");
//                    }
//                    else{
//                        daynow = Calendar.getInstance().getTime();
//                        Log.d("file", "startTime: " + daynow);
//                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); // Set your date format
//                        day = sdf.format(daynow);
////                        for(day = sd; day <= ed; day++){
////                            if(sd.compareTo(day) == 0) {
////                                //this is for first day
////                                SimpleDateFormat dfor = new SimpleDateFormat("dd-M-yyyy");
////                                String fd = dfor.format(daynow);
////                                SimpleDateFormat sdft = new SimpleDateFormat("dd-M-yyyy hh:mm");
////                                String dateString = fd.toString() + " 23:59";
////                                Log.d("file", "dateString " + dateString);
////                                try {
////                                    //formatting the dateString to convert it into a Date
////                                    Date date = sdft.parse(dateString);
////                                    Log.d("file", "Given Time in milliseconds : " + date.getTime());
////                                    Calendar calendar = Calendar.getInstance();
////                                    //Setting the Calendar date and time to the given date and time
////                                    calendar.setTime(date);
////                                    Log.d("file", "Given Time in milliseconds : " + calendar.getTimeInMillis());
////
////                                    timeIntervalBef = calendar.getTimeInMillis() - startTime;
////                                    durationOn = duration1 + timeIntervalBef;
////                                    history.child(formattedDate).child("duration1").setValue(durationOn);
////                                }catch (ParseException e){
////                                    e.printStackTrace();
////                                }
////                            } else if(ed.compareTo(day) == 0) {
////                                //this is for last day
////                                SimpleDateFormat dfor = new SimpleDateFormat("dd-M-yyyy");
////                                String fd = dfor.format(daynow);
////                                SimpleDateFormat sdft = new SimpleDateFormat("dd-M-yyyy hh:mm");
////                                String dateString = fd.toString() + " 00:00";
////                                Log.d("file", "dateString " + dateString);
////                                try {
////                                    //formatting the dateString to convert it into a Date
////                                    Date date = sdft.parse(dateString);
////                                    Log.d("file", "Given Time in milliseconds : " + date.getTime());
////                                    Calendar calendar = Calendar.getInstance();
////                                    //Setting the Calendar date and time to the given date and time
////                                    calendar.setTime(date);
////                                    Log.d("file", "Given Time in milliseconds : " + calendar.getTimeInMillis());
////
////                                    timeIntervalAf = endTime - calendar.getTimeInMillis();
////                                    durationOn = duration1 + timeIntervalAf;
////                                    history.child(formattedDate).child("duration1").setValue(durationOn);
////                                }catch (ParseException e){
////                                    e.printStackTrace();
////                                }
////                            } else{
////                                //this is for days between
////                                durationOn = 86400000;
////                                history.child(formattedDate).child("duration1").setValue(durationOn);
////                            }
////                        }
//                        Log.d("file", "ini beda tanggal");
//                    }
//                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("file", "Failed to read value.", databaseError.toException());
            }
        });

        statusLamp2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Log.d("file", "Value is: " + value);
                tvBedroom.setText(value);
                if(value.equals("ON")){
                    dateOnLamp2 = Calendar.getInstance().getTimeInMillis();
                    dateOn2 = Calendar.getInstance().getTime();
                    Log.d("file", "dateon :" + dateOnLamp2);
                    Log.d("file", "dateon getTime :" + dateOn2);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); // Set your date format
                    sd = sdf.format(dateOnLamp2);
                    Log.d("file", "startDate: " + sd);
                }else if (value.equals("OFF")) {
                    dateOffLamp2 = Calendar.getInstance().getTimeInMillis();
                    Log.d("file", "dateoff :" + dateOffLamp2);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); // Set your date format
                    ed = sdf.format(dateOffLamp2);
                    Log.d("file", "endDate: " + ed);
                    if(sd == null){
                        Log.d("file", "its null");
                    }
                    else if (sd !=null){
                        dateDiff2 = dateOffLamp2 - dateOnLamp2;
                        datediff2();
                    }
                }
            }
            public void datediff2(){
                long days = dateDiff2 / (24*60*60*1000);
                dateDiff2 = dateDiff2 % (24*60*60*1000);
                long hours = dateDiff2 / (60*60*1000);
                dateDiff2 = dateDiff2 % (60*60*1000);
                long minutess = dateDiff2 / (60*1000);
                dateDiff2 = dateDiff2 % (60*1000);
                long seconds = dateDiff2 / 1000;
                Log.d("file", "days diff: " + days + " hours dif: " + hours + " mins diff: " + minutess + " seconds diff: " + seconds);

                if (days == 0) {
                    Log.d("file", "days = 0");
                    timeInterval = dateOffLamp2 - dateOnLamp2;
                    Log.d("file", "time interval:" + timeInterval + " endtime: " + dateOffLamp2 + " starttime: " + dateOnLamp2);
                    durationOn = duration2 + timeInterval;
                    history.child(formattedDate).child("duration2").setValue(durationOn);

                }else if(days >= 1){
                    Log.d("file", "days > 0");
                    for (int i = 0; i<=days; i++){
                        Calendar calendar = Calendar.getInstance();
                        //Setting the Calendar date and time to the given date and time
                        calendar.setTime(Calendar.getInstance().getTime());
                        Log.d("file", "Given Time in milliseconds : "+calendar.getTimeInMillis());
                        calendar.add(Calendar.DATE, -i);
                        String prevday = df.format(calendar.getTime());
                        Log.d("file", "prev days:" + prevday);
                        if (i == 0){
                            String dateStart = "00:00:00";
                            SimpleDateFormat sdff = new SimpleDateFormat("HH:mm:ss");
                            try {
                                dates = sdff.parse(dateStart);
                                Log.d("file", "dates: " + dates + " datee: " + datee);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            dateDiff2 = dateOffLamp2 - dates.getTime();

                            Log.d("file", "dateDiff: " + dateDiff2);
                            long diffSeconds = dateDiff2 / 1000 % 60;
                            long diffMinutes = dateDiff2 / (60 * 1000) % 60;
                            long diffHours = dateDiff2 / (60 * 60 * 1000) % 24;
                            Log.d("file", " hours dif: " + diffHours + " mins diff: " + diffMinutes + " seconds diff: " + diffSeconds);

                            long durmil = (diffHours*60*60*1000) + (diffMinutes*60*1000) + (diffSeconds*1000);
                            Log.d("file", "milliseconds: " + durmil);

                            durationOn = duration2 + durmil;
                            history.child(prevday).child("duration2").setValue(durationOn);
                        } else if(i == days){
                            long millis = dateOnLamp2;
                            Log.d("file", "millis rusak:" + millis);
                            String dateStart = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                                    TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                                    TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
                            Log.d("file", "dateStart: " + dateStart);

                            String dateStop = "00:00:00";
                            SimpleDateFormat sdff = new SimpleDateFormat("HH:mm:ss");
                            try {
                                dates = sdff.parse(dateStart);
                                datee = sdff.parse(dateStop);
                                Log.d("file", "datee: " + datee + " dates: " + dates);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            dateDiff2 = dates.getTime() - datee.getTime();
                            Log.d("file", "dateDiff: " + dateDiff2);
                            long diffSeconds = dateDiff2 / 1000 % 60;
                            long diffMinutes = dateDiff2 / (60 * 1000) % 60;
                            long diffHours = dateDiff2 / (60 * 60 * 1000) % 24;
                            Log.d("file", " hours dif: " + diffHours + " mins diff: " + diffMinutes + " seconds diff: " + diffSeconds);

                            long durmil = (diffHours*60*60*1000) + (diffMinutes*60*1000) + (diffSeconds*1000);
                            Log.d("file", "milliseconds: " + durmil);

                            durationOn = duration2 + durmil;
                            history.child(prevday).child("duration2").setValue(durationOn);
                        } else{
                            history.child(prevday).child("duration2").setValue(24*60*60*100);
                        }
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("file", "Failed to read value.", databaseError.toException());
            }
        });

        statusLamp3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Log.d("file", "Value is: " + value);
                tvLiving.setText(value);
                if(value.equals("ON")){
                    dateOnLamp3 = Calendar.getInstance().getTimeInMillis();
                    dateOn3 = Calendar.getInstance().getTime();
                    Log.d("file", "dateon :" + dateOnLamp3);
                    Log.d("file", "dateon getTime :" + dateOn3);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); // Set your date format
                    sd = sdf.format(dateOnLamp3);
                    Log.d("file", "startDate: " + sd);
                }else if (value.equals("OFF")) {
                    dateOffLamp3 = Calendar.getInstance().getTimeInMillis();
                    Log.d("file", "dateoff :" + dateOffLamp3);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); // Set your date format
                    ed = sdf.format(dateOffLamp3);
                    Log.d("file", "endDate: " + ed);
                    if(sd == null){
                        Log.d("file", "its null");
                    }
                    else if (sd !=null){
                        dateDiff3 = dateOffLamp3 - dateOnLamp3;
                        datediff3();
                    }
                }
            }
            public void datediff3(){
                long days = dateDiff3 / (24*60*60*1000);
                dateDiff3 = dateDiff3 % (24*60*60*1000);
                long hours = dateDiff3 / (60*60*1000);
                dateDiff3 = dateDiff3 % (60*60*1000);
                long minutess = dateDiff3 / (60*1000);
                dateDiff3 = dateDiff3 % (60*1000);
                long seconds = dateDiff3 / 1000;
                Log.d("file", "days diff: " + days + " hours dif: " + hours + " mins diff: " + minutess + " seconds diff: " + seconds);

                if (days == 0){
                    Log.d("file", "days = 0");
                    timeInterval = dateOffLamp3 - dateOnLamp3;
                    Log.d("file", "time interval:" + timeInterval + " endtime: " + dateOffLamp3 + " starttime: " + dateOnLamp3);
                    durationOn = duration3 + timeInterval;
                    history.child(formattedDate).child("duration3").setValue(durationOn);
                }else if(days >= 1) {
                    Log.d("file", "days > 0");
                    for (int i = 0; i <= days; i++) {
                        Calendar calendar = Calendar.getInstance();
                        //Setting the Calendar date and time to the given date and time
                        calendar.setTime(Calendar.getInstance().getTime());
                        Log.d("file", "Given Time in milliseconds : " + calendar.getTimeInMillis());
                        calendar.add(Calendar.DATE, -i);
                        String prevday = df.format(calendar.getTime());
                        Log.d("file", "prev days:" + prevday);
                        if (i == 0) {
                            String dateStart = "00:00:00";
                            SimpleDateFormat sdff = new SimpleDateFormat("HH:mm:ss");
                            try {
                                dates = sdff.parse(dateStart);
                                Log.d("file", "dates: " + dates + " datee: " + datee);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            dateDiff3 = dateOffLamp3 - dates.getTime();

                            Log.d("file", "dateDiff: " + dateDiff3);
                            long diffSeconds = dateDiff3 / 1000 % 60;
                            long diffMinutes = dateDiff3 / (60 * 1000) % 60;
                            long diffHours = dateDiff3 / (60 * 60 * 1000) % 24;
                            Log.d("file", " hours dif: " + diffHours + " mins diff: " + diffMinutes + " seconds diff: " + diffSeconds);

                            long durmil = (diffHours * 60 * 60 * 1000) + (diffMinutes * 60 * 1000) + (diffSeconds * 1000);
                            Log.d("file", "milliseconds: " + durmil);

                            durationOn = duration3 + durmil;
                            history.child(prevday).child("duration3").setValue(durationOn);
                        } else if (i == days) {
                            long millis = dateOnLamp3;
                            Log.d("file", "millis rusak:" + millis);
                            String dateStart = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                                    TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                                    TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
                            Log.d("file", "dateStart: " + dateStart);

                            String dateStop = "00:00:00";
                            SimpleDateFormat sdff = new SimpleDateFormat("HH:mm:ss");
                            try {
                                dates = sdff.parse(dateStart);
                                datee = sdff.parse(dateStop);
                                Log.d("file", "datee: " + datee + " dates: " + dates);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            dateDiff3 = dates.getTime() - datee.getTime();
                            Log.d("file", "dateDiff: " + dateDiff3);
                            long diffSeconds = dateDiff3 / 1000 % 60;
                            long diffMinutes = dateDiff3 / (60 * 1000) % 60;
                            long diffHours = dateDiff3 / (60 * 60 * 1000) % 24;
                            Log.d("file", " hours dif: " + diffHours + " mins diff: " + diffMinutes + " seconds diff: " + diffSeconds);

                            long durmil = (diffHours * 60 * 60 * 1000) + (diffMinutes * 60 * 1000) + (diffSeconds * 1000);
                            Log.d("file", "milliseconds: " + durmil);

                            durationOn = duration3 + durmil;
                            history.child(prevday).child("duration3").setValue(durationOn);
                        } else {
                            history.child(prevday).child("duration3").setValue(24 * 60 * 60 * 100);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("file", "Failed to read value.", databaseError.toException());
            }
        });

        statusLamp4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Log.d("file", "Value is: " + value);
                tvCloset.setText(value);
                if(value.equals("ON")){
                    dateOnLamp4 = Calendar.getInstance().getTimeInMillis();
                    dateOn4 = Calendar.getInstance().getTime();
                    Log.d("file", "dateon :" + dateOnLamp4);
                    Log.d("file", "dateon getTime :" + dateOn4);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); // Set your date format
                    sd = sdf.format(dateOnLamp4);
                    Log.d("file", "startDate: " + sd);
                }else if (value.equals("OFF")) {
                    dateOffLamp4 = Calendar.getInstance().getTimeInMillis();
                    Log.d("file", "dateoff :" + dateOffLamp4);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); // Set your date format
                    ed = sdf.format(dateOffLamp4);
                    Log.d("file", "endDate: " + ed);
                    if(sd == null){
                        Log.d("file", "its null");
                    }
                    else if (sd !=null){
                        dateDiff4 = dateOffLamp4 - dateOnLamp4;
                        datediff4();
                    }
                }
            }
            public void datediff4(){
                long days = dateDiff4 / (24*60*60*1000);
                dateDiff4 = dateDiff4 % (24*60*60*1000);
                long hours = dateDiff4 / (60*60*1000);
                dateDiff4 = dateDiff4 % (60*60*1000);
                long minutess = dateDiff4 / (60*1000);
                dateDiff4 = dateDiff4 % (60*1000);
                long seconds = dateDiff4 / 1000;
                Log.d("file", "days diff: " + days + " hours dif: " + hours + " mins diff: " + minutess + " seconds diff: " + seconds);
//
                if (days == 0) {
                    Log.d("file", "days = 0");
                    timeInterval = dateOffLamp4 - dateOnLamp4;
                    Log.d("file", "time interval:" + timeInterval + " endtime: " + dateOffLamp4 + " starttime: " + dateOnLamp4);
                    durationOn = duration4 + timeInterval;
                    history.child(formattedDate).child("duration4").setValue(durationOn);
                }else if(days >= 1) {
                    Log.d("file", "days > 0");
                    for (int i = 0; i <= days; i++) {
                        Calendar calendar = Calendar.getInstance();
                        //Setting the Calendar date and time to the given date and time
                        calendar.setTime(Calendar.getInstance().getTime());
                        Log.d("file", "Given Time in milliseconds : " + calendar.getTimeInMillis());
                        calendar.add(Calendar.DATE, -i);
                        String prevday = df.format(calendar.getTime());
                        Log.d("file", "prev days:" + prevday);
                        if (i == 0) {
                            String dateStart = "00:00:00";
                            SimpleDateFormat sdff = new SimpleDateFormat("HH:mm:ss");
                            try {
                                dates = sdff.parse(dateStart);
                                Log.d("file", "dates: " + dates + " datee: " + datee);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            dateDiff4 = dateOffLamp4 - dates.getTime();

                            Log.d("file", "dateDiff: " + dateDiff4);
                            long diffSeconds = dateDiff4 / 1000 % 60;
                            long diffMinutes = dateDiff4 / (60 * 1000) % 60;
                            long diffHours = dateDiff4 / (60 * 60 * 1000) % 24;
                            Log.d("file", " hours dif: " + diffHours + " mins diff: " + diffMinutes + " seconds diff: " + diffSeconds);

                            long durmil = (diffHours * 60 * 60 * 1000) + (diffMinutes * 60 * 1000) + (diffSeconds * 1000);
                            Log.d("file", "milliseconds: " + durmil);

                            durationOn = duration4 + durmil;
                            history.child(prevday).child("duration4").setValue(durationOn);
                        } else if (i == days) {
                            long millis = dateOnLamp4;
                            Log.d("file", "millis rusak:" + millis);
                            String dateStart = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                                    TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                                    TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
                            Log.d("file", "dateStart: " + dateStart);

                            String dateStop = "00:00:00";
                            SimpleDateFormat sdff = new SimpleDateFormat("HH:mm:ss");
                            try {
                                dates = sdff.parse(dateStart);
                                datee = sdff.parse(dateStop);
                                Log.d("file", "datee: " + datee + " dates: " + dates);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            dateDiff4 = dates.getTime() - datee.getTime();
                            Log.d("file", "dateDiff: " + dateDiff4);
                            long diffSeconds = dateDiff4 / 1000 % 60;
                            long diffMinutes = dateDiff4 / (60 * 1000) % 60;
                            long diffHours = dateDiff4 / (60 * 60 * 1000) % 24;
                            Log.d("file", " hours dif: " + diffHours + " mins diff: " + diffMinutes + " seconds diff: " + diffSeconds);

                            long durmil = (diffHours * 60 * 60 * 1000) + (diffMinutes * 60 * 1000) + (diffSeconds * 1000);
                            Log.d("file", "milliseconds: " + durmil);

                            durationOn = duration4 + durmil;
                            history.child(prevday).child("duration4").setValue(durationOn);
                        } else {
                            history.child(prevday).child("duration4").setValue(24 * 60 * 60 * 100);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("file", "Failed to read value.", databaseError.toException());
            }
        });

        btn_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                statusLamp1.setValue("OFF");
                statusLamp2.setValue("OFF");
                statusLamp3.setValue("OFF");
                pirStatus.setValue("OFF");
                Toast.makeText(HomeActivity.this, "Turn all the lamps OFF", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("HomeActivity", "On Destroy: ");
    }

    int click = 0;
    long startTime;
    long endTime;
    long timeInterval;
    long durationOn;

    private void setSingleEvent(GridLayout mainGrid) {
        //Loop all child item on Main Grid
        for(int i=0;i<mainGrid.getChildCount();i++){
            final CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    click++;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(click == 1){
                                if(finalI == 0) {
                                    statusLamp1.setValue("OFF");
//                                    dateOff1 = Calendar.getInstance().get(Calendar.DATE);
//                                    Log.d("file", "dateOff: " + dateOff1);
//
////                                    endTime = System.currentTimeMillis();
////                                    endTime = Calendar.getInstance().getTimeInMillis();
//                                    Log.d("file", "endTime: " + endTime);

//                                    if (dateOff1==dateOn1){
//                                        timeInterval = endTime - startTime;
//                                        Log.d("file", "interval: " + timeInterval);
//                                        durationOn = duration1 + timeInterval;
//                                        Log.d("file", "dur: " + duration1 + " durOn: " + durationOn);
//
//                                        history.child(formattedDate).child("duration1").setValue(durationOn);
//                                        Log.d("file", "duration on: " + durationOn);
//                                    }else {
//                                        Log.d("file", "Ini kalau beda tanggal");
//                                    }
                                }
                                if(finalI == 1){
                                    statusLamp2.setValue("OFF");
//                                    dateOff2 = Calendar.getInstance().get(Calendar.DATE);
//                                    Log.d("file", "dateOff: " + dateOff2);
//
//                                    endTime = System.currentTimeMillis();
////                                    endTime = Calendar.getInstance().getTimeInMillis();
//                                    Log.d("file", "endTime: " + endTime);
//
//                                    if (dateOff2==dateOn2){
//                                        timeInterval = endTime - startTime;
//                                        Log.d("file", "interval: " + timeInterval);
//                                        durationOn = duration2 + timeInterval;
//                                        Log.d("file", "dur: " + duration2 + " durOn: " + durationOn);
//
//                                        history.child(formattedDate).child("duration2").setValue(durationOn);
//                                        Log.d("file", "duration on: " + durationOn);
//                                    }else {
//                                        Log.d("file", "Ini kalau beda tanggal");
//                                    }
                                }
                                if(finalI == 2){
                                    statusLamp3.setValue("OFF");
//                                    dateOff3 = Calendar.getInstance().get(Calendar.DATE);
//                                    Log.d("file", "dateOff: " + dateOff3);
//
//                                    endTime = System.currentTimeMillis();
////                                    endTime = Calendar.getInstance().getTimeInMillis();
//                                    Log.d("file", "endTime: " + endTime);
//
//                                    if (dateOff3==dateOn3){
//                                        timeInterval = endTime - startTime;
//                                        Log.d("file", "interval: " + timeInterval);
//                                        durationOn = duration3 + timeInterval;
//                                        Log.d("file", "dur: " + duration3 + " durOn: " + durationOn);
//
//                                        history.child(formattedDate).child("duration3").setValue(durationOn);
//                                        Log.d("file", "duration on: " + durationOn);
//                                    }else {
//                                        Log.d("file", "Ini kalau beda tanggal");
//                                    }
                                }
                                if(finalI == 3){
                                    pirStatus.setValue("OFF");
                                    Toast.makeText(HomeActivity.this, "PIR MOTION SENSOR OFF", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else if(click == 2){
                                if(finalI == 0) {
//                                    dateOn1 = Calendar.getInstance().get(Calendar.DATE);
//                                    Log.d("file", "dateOn: " + dateOn1);
                                    statusLamp1.setValue("ON");
//                                    startTime = System.currentTimeMillis();
//                                    startTime = Calendar.getInstance().getTimeInMillis();
//                                    Log.d("file", "starttime: " + startTime);
                                }
                                if(finalI == 1){
                                    statusLamp2.setValue("ON");
                                    dimmerLamp2.setValue(100);
//                                    dateOn2 = Calendar.getInstance().get(Calendar.DATE);
//                                    Log.d("file", "dateOn: " + dateOn2);
//                                    startTime = Calendar.getInstance().getTimeInMillis();
//                                    Log.d ("file", "startTime: " + startTime);
                                }
                                if(finalI == 2){
                                    statusLamp3.setValue("ON");
                                    dimmerLamp3.setValue(100);
//                                    dateOn3 = Calendar.getInstance().get(Calendar.DATE);
//                                    Log.d("file", "dateOn: " + dateOn3);
//                                    startTime = Calendar.getInstance().getTimeInMillis();
//                                    Log.d ("file", "startTime: " + startTime);
                                }
                                if(finalI == 3){
                                  pirStatus.setValue("ON");
                                  Toast.makeText(HomeActivity.this, "PIR MOTION SENSOR ON", Toast.LENGTH_SHORT).show();
                                }
                            }
                            click = 0;
                        }
                    }, 500);
                }
            });

            cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if(finalI == 0){
                        Intent goTimer = new Intent(HomeActivity.this, SetTimerActivity.class);
                        startActivity(goTimer);
                    }
                    if(finalI == 1){
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeActivity.this);
                        alertDialog.setTitle("Dimmer");
                        alertDialog.setMessage("Please input the dimmer value : % ");
                        alertDialog.setMessage("*Dimmer range: 40% - 100%");
                        dimLamp2 = new EditText(HomeActivity.this);
                        alertDialog.setView(dimLamp2);

                        dimmerLamp2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Long value = dataSnapshot.getValue(Long.class);
                                Log.d("file", "Value is: " + value);
                                dimLamp2.setText(value.toString());
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.w("file", "Failed to read value.", databaseError.toException());
                            }
                        });

                        alertDialog.setPositiveButton("SET", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String value1 = dimLamp2.getText().toString();
                                if(dimLamp2.length() == 0) {
                                    AlertDialog alertDialog = new AlertDialog.Builder(HomeActivity.this).create();
                                    alertDialog.setTitle("Error");
                                    alertDialog.setMessage("Dimmer is required");
                                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    });
                                    alertDialog.show();
                                    Log.d("file", "Error");
                                }else if(Long.parseLong(dimLamp2.getText().toString()) < 40 || Long.parseLong(dimLamp2.getText().toString()) > 100){
                                        AlertDialog alertDialog = new AlertDialog.Builder(HomeActivity.this).create();
                                        alertDialog.setTitle("Error");
                                        alertDialog.setMessage("Please input valid number");
                                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                            }
                                        });
                                        alertDialog.show();
                                        Log.d("file","Error");
                                }else {
                                    long val1 = Long.parseLong(value1);
                                    dimmerLamp2.setValue(val1);
                                    dialogInterface.dismiss();
                                    Toast.makeText(HomeActivity.this, "Dimmer Set", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        alertDialog.create().show();
                    }
                    if(finalI == 2){
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeActivity.this);
                        alertDialog.setTitle("Dimmer");
                        alertDialog.setMessage("Please input the dimmer value : % ");
                        alertDialog.setMessage("*Dimmer range 40%-100%");
                        dimLamp3 = new EditText(HomeActivity.this);
                        alertDialog.setView(dimLamp3);

                        dimmerLamp3.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Long value = dataSnapshot.getValue(Long.class);
                                Log.d("file", "Value is: " + value);
                                dimLamp3.setText(value.toString());
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.w("file", "Failed to read value.", databaseError.toException());
                            }
                        });

                        alertDialog.setPositiveButton("SET", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String value1 = dimLamp3.getText().toString();
                                if(dimLamp3.length() == 0) {
                                    AlertDialog alertDialog = new AlertDialog.Builder(HomeActivity.this).create();
                                    alertDialog.setTitle("Error");
                                    alertDialog.setMessage("Dimmer is required");
                                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    });
                                    alertDialog.show();
                                    Log.d("file", "Error");
                                } else if(Long.parseLong(dimLamp3.getText().toString()) < 40 || Long.parseLong(dimLamp3.getText().toString()) > 100){
                                        AlertDialog alertDialog = new AlertDialog.Builder(HomeActivity.this).create();
                                        alertDialog.setTitle("Error");
                                        alertDialog.setMessage("Please input valid number");
                                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                            }
                                        });
                                        alertDialog.show();
                                        Log.d("file","Error");
                                }else {
                                    long val1 = Long.parseLong(value1);
                                    dimmerLamp3.setValue(val1);
                                    dialogInterface.dismiss();
                                    Toast.makeText(HomeActivity.this, "Dimmer Set", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        alertDialog.create().show();
                    }
                    if(finalI == 3){
                        AlertDialog alertDialog = new AlertDialog.Builder(HomeActivity.this).create();
                        alertDialog.setTitle("Reminder");
                        alertDialog.setMessage("This room is using PIR Motion Detection. Double tap to turn on the sensor and one tap to turn off the sensor.");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        alertDialog.show();
                    }
                    return true;
                }
            });
        }
    }
}