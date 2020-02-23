package com.example.arduino_odczyty;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
        SharedPreferences shared;
    Button przycisk_dane_z_arduino,przycisk_rejestracja,przycisk_logowanie,przycisk_alerty,przycisk_sortuj,przycisk_zakresy,przycisk_zmien_haslo,przycisk_email_haslo;
    TextView zalogowany_jako;
    LinearLayout pasek_do_logowania;
    NotificationCompat.Builder notification;
    String typ="login",login;
    private static final int uniqueID=454543;
    double jakosc0,temp0,wilg0,cisnienie0;
    double pierwsza_temp,druga_temp,pierwsza_wilg,druga_wilg,pierwsza_cisnienie,druga_cisnienie,pierwsza_gazy,druga_gazy;
    String data0;
    DatabaseReference ref_haslo2,ref_token;
    private final long[] wibracja= { 1000, 1000, 1000, 1000, 1000 };
    int i=0;
    Toast toast=null;
    private static final int NOTIFICATION_ID = 1;
    private static final String NOTIFICATION_CHANNEL_ID = "Kanał powiadomienia";
    NotificationChannel notificationChannel;
    Uri ref2;
    AudioAttributes atrybuty;
    MediaPlayer dzwiek;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notification=new NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID);
        notification.setAutoCancel(false);
        shared=getSharedPreferences("A",Context.MODE_PRIVATE);
        setTitle(R.string.tytul);
        setContentView(R.layout.activity_main);
//       dzwiek=MediaPlayer.create(MainActivity);
        przycisk_dane_z_arduino=findViewById(R.id.przycisk_Dane_z_arduino1);
        przycisk_sortuj=findViewById(R.id.przycisk_Sortuj);
        przycisk_logowanie=findViewById(R.id.przycisk_Logowanie);
        zalogowany_jako=findViewById(R.id.zalogowany_jako);
        przycisk_alerty=findViewById(R.id.przycisk_powiadomienia);
        pasek_do_logowania=findViewById(R.id.pasek_dla_logowania);
        przycisk_zakresy=findViewById(R.id.przycisk_zakresy);


       // przycisk_alerty=findViewById(R.id.przycisk_Alerty);
        przycisk_alerty.setVisibility(View.VISIBLE);
        pasek_do_logowania.setVisibility(View.INVISIBLE);
        przycisk_dane_z_arduino.setVisibility(View.VISIBLE);
        przycisk_sortuj.setVisibility(View.VISIBLE);
        przycisk_logowanie.setVisibility(View.VISIBLE);
        przycisk_zakresy.setVisibility(View.VISIBLE);




        if(shared.contains("login"))
        {
            zalogowany_jako.setText(shared.getString("login",""));
            login=shared.getString("login","");
            przycisk_logowanie.setVisibility(View.INVISIBLE);

            pasek_do_logowania.setVisibility(View.VISIBLE);
            przycisk_alerty.setVisibility(View.VISIBLE);
            przycisk_sortuj.setVisibility(View.VISIBLE);
            przycisk_zakresy.setVisibility(View.VISIBLE);



        }
        else
        {
            przycisk_dane_z_arduino.setVisibility(View.INVISIBLE);
            przycisk_alerty.setVisibility(View.INVISIBLE);
            przycisk_sortuj.setVisibility(View.INVISIBLE);
            przycisk_zakresy.setVisibility(View.INVISIBLE);


        }
        zalogowany_jako.setVisibility(View.INVISIBLE);
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference Ref_temp = database.getReference("User/temperatura");
        DatabaseReference Ref_wilg = database.getReference("User/wilgotnosc");
        DatabaseReference Ref_cisnienie = database.getReference("User/cisnienie");
        DatabaseReference Ref_jakosc = database.getReference("User/szk_gazy");
        DatabaseReference Ref_data = database.getReference("User/data");
        DatabaseReference ref_login = database.getReference("Logs/login");
        DatabaseReference ref_haslo1= database.getReference("Logs/haslo1");
        ref_haslo2= database.getReference("Logs/haslo2");
        ref_token= database.getReference("Logs/token");
        DatabaseReference ref_email= database.getReference("Logs/email");

 //   FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();

        if(shared.contains("nowe_haslo"))
        {
             ref_haslo2.setValue(shared.getString("nowe_haslo",""));
             SharedPreferences.Editor editor=shared.edit();
             editor.remove("nowe_haslo");
             editor.commit();
        }

        ref_login.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                SharedPreferences.Editor editor=shared.edit();
                editor.putString("fir_login",value);
                editor.commit();
                //fir_login=value;

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("35", "Failed to read value.", error.toException());
            }
        });
        ref_haslo1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                SharedPreferences.Editor editor=shared.edit();
                editor.putString("fir_haslo1",value);
                editor.commit();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("35", "Failed to read value.", error.toException());
            }
        });
        ref_haslo2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                SharedPreferences.Editor editor=shared.edit();
                editor.putString("fir_haslo2",value);
                editor.commit();


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("35", "Failed to read value.", error.toException());
            }
        });
        ref_email.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                SharedPreferences.Editor editor=shared.edit();
                editor.putString("fir_email",value);
                editor.commit();


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("35", "Failed to read value.", error.toException());
            }
        });

        if(shared.contains("ustaw_domyslne_haslo"))
        {
            ref_haslo2.setValue(shared.getString("fir_haslo1",""));
            SharedPreferences.Editor editor=shared.edit();
            editor.remove("ustaw_domyslne_haslo");
            editor.commit();
        }
        if(shared.contains("ustaw_domyslny_email"))
        {
            ref_email.setValue(shared.getString("fir_email",""));
            SharedPreferences.Editor editor=shared.edit();
            editor.remove("ustaw_domyslny_email");
            editor.commit();
        }

        Ref_temp.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                temp0=Double.valueOf(value);
                Log.d("46", "Value is: " + value);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("35", "Failed to read value.", error.toException());
            }
        });

        Ref_wilg.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                wilg0=Double.valueOf(value);
                Log.d("47", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("36", "Failed to read value.", error.toException());
            }
        });

        Ref_cisnienie.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                cisnienie0=Double.valueOf(value);
                Log.d("48", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("37", "Failed to read value.", error.toException());
            }
        });
        Ref_jakosc.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                jakosc0=Double.valueOf(value);
                Log.d("49", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("38", "Failed to read value.", error.toException());
            }
    });

        Ref_data.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                data0=value;
                i++;
                Log.d("55", "i: :"+i);
              //  toast=Toast.makeText(getApplicationContext(),"Temperatura: "+temp0+"\u2103, Wilgotność: "+wilg0+"%\n"+
               //         "Ciśnienie: "+cisnienie0+"hPa"+", Szkodliwe gazy:"+jakosc0+"ppm",Toast.LENGTH_SHORT).show();
                Log.d("50", "Value is: " + value);
                Uri ref= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                notification.setSmallIcon((R.drawable.idea));
                notification.setTicker("ticker");
                notification.setWhen(System.currentTimeMillis());

                notification.setContentTitle(data0+" | Temp: "+temp0+"\u2103 | Wilg: "+wilg0+"%");
                notification.setContentText("Ciśnienie: "+cisnienie0+"hPa"+" | Szk. gazy:"+jakosc0+"ppm");
               notification.setSound(null);
               notification.setVibrate(null);
                 ref2 = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                        + "://" + getPackageName() + "/raw/alert");

                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                {
                    notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, getApplicationContext().getResources().getString(R.string.powiadomienie), NotificationManager.IMPORTANCE_DEFAULT);

                    // Configure the notification channel.
                    notificationChannel.setDescription("Channel description");
                   // notificationChannel.enableLights(true);
                    notificationChannel.setLightColor(Color.RED);
                    notificationChannel.setVibrationPattern(wibracja);
                    notificationChannel.enableVibration(true);
                    atrybuty = new AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                           .build();

                    notificationManager.createNotificationChannel(notificationChannel);

                }
                if(shared.contains("zakresy_pierwsza_temp"))
                {
                    pierwsza_temp = Double.valueOf(shared.getString("zakresy_pierwsza_temp", ""));
                }
                else
                {
                    pierwsza_temp=-1000;
                }
                if(shared.contains("zakresy_druga_temp"))
                {
                    druga_temp = Double.valueOf(shared.getString("zakresy_druga_temp", ""));
                }
                else
                {
                    druga_temp=1000;
                }
                if(shared.contains("zakresy_pierwsza_wilg"))
                {
                    pierwsza_wilg = Double.valueOf(shared.getString("zakresy_pierwsza_wilg", ""));
                }
                else
                {
                    pierwsza_wilg=-10;
                }

                if(shared.contains("zakresy_druga_wilg"))
                {
                    druga_wilg = Double.valueOf(shared.getString("zakresy_druga_wilg", ""));
                }
                else
                {
                    druga_wilg=101;
                }

                if(shared.contains("zakresy_pierwsza_cisnienie"))
                {
                    pierwsza_cisnienie = Double.valueOf(shared.getString("zakresy_pierwsza_cisnienie", ""));
                }
                else
                {
                    pierwsza_cisnienie=-10;
                }

                if(shared.contains("zakresy_druga_cisnienie"))
                {
                    druga_cisnienie = Double.valueOf(shared.getString("zakresy_druga_cisnienie", ""));
                }
                else
                {
                    druga_cisnienie=1500;
                }



                if(shared.contains("zakresy_pierwsza_gazy"))
                {
                    pierwsza_gazy = Double.valueOf(shared.getString("zakresy_pierwsza_gazy", ""));
                }
                else
                {
                    pierwsza_gazy=-10;
                }

                if(shared.contains("zakresy_druga_gazy"))
                {
                    druga_gazy = Double.valueOf(shared.getString("zakresy_druga_gazy", ""));
                }
                else
                {
                    druga_gazy=999999;
                }

                if((temp0<pierwsza_temp || temp0>druga_temp)&&
                        (wilg0<pierwsza_wilg || wilg0>druga_wilg)&&
                        (cisnienie0<pierwsza_cisnienie ||cisnienie0>druga_cisnienie)&&
                        (jakosc0<pierwsza_gazy|| jakosc0>druga_gazy))
                {
                    if((toast == null || toast.getView().getWindowVisibility() != View.VISIBLE)) {
                        i = 0;
                        toast=null;
                        toast=Toast.makeText(getApplicationContext(), R.string.czujniki_toast_temp_wilg_cisn_gazy, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    notification.setSound(ref2);
                    notification.setVibrate(wibracja);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    {
                        try
                        {
                            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), ref2);
                            r.play();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                else if((temp0<pierwsza_temp || temp0>druga_temp)&&
                        ( wilg0<pierwsza_wilg || wilg0>druga_wilg)&&
                        (cisnienie0<pierwsza_cisnienie ||cisnienie0>druga_cisnienie)&&
                (jakosc0>pierwsza_gazy&& jakosc0<druga_gazy))
                {
                    if((toast == null || toast.getView().getWindowVisibility() != View.VISIBLE)&&i>1)
                    {
                        i = 0;
                        toast=null;
                        toast=Toast.makeText(getApplicationContext(), R.string.czujniki_toast_temp_wilg_cisn, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    notification.setSound(ref2);
                    notification.setVibrate(wibracja);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    {
                        try
                        {
                            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), ref2);
                            r.play();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
               else if((temp0<pierwsza_temp || temp0>druga_temp)&&
                        (wilg0<pierwsza_wilg || wilg0>druga_wilg)&&
                        (cisnienie0>pierwsza_cisnienie &&cisnienie0<druga_cisnienie)
                        &&(jakosc0>pierwsza_gazy&& jakosc0<druga_gazy))
                {
                    if((toast == null || toast.getView().getWindowVisibility() != View.VISIBLE)&&i>1)
                    {
                        i = 0;
                        toast=null;
                        toast=Toast.makeText(getApplicationContext(), R.string.czujniki_toast_temp_wilg, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    notification.setSound(ref2);
                    notification.setVibrate(wibracja);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    {
                        try
                        {
                            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), ref2);
                            r.play();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
               else if((temp0<pierwsza_temp || temp0>druga_temp)&&
                (wilg0>pierwsza_wilg && wilg0<druga_wilg)&&
                (cisnienie0>pierwsza_cisnienie &&cisnienie0<druga_cisnienie)
                        &&(jakosc0>pierwsza_gazy && jakosc0<druga_gazy))
                {
                    if((toast == null || toast.getView().getWindowVisibility() != View.VISIBLE)&&i>1)
                    {
                        i = 0;
                        toast=null;
                        toast=Toast.makeText(getApplicationContext(), R.string.czujniki_toast_temp, Toast.LENGTH_SHORT);                        toast.show();
                    }
                    notification.setSound(ref2);
                    notification.setVibrate(wibracja);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    {
                        try
                        {
                            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), ref2);
                            r.play();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                else if((temp0>pierwsza_temp && temp0<druga_temp)&&(wilg0<pierwsza_wilg || wilg0>druga_wilg)&&
                        (cisnienie0<pierwsza_cisnienie ||cisnienie0>druga_cisnienie)&&
                        (jakosc0<pierwsza_gazy|| jakosc0>druga_gazy))
                {
                    if((toast == null || toast.getView().getWindowVisibility() != View.VISIBLE)&&i>1)
                    {
                        i = 0;
                        toast=null;
                        toast=Toast.makeText(getApplicationContext(), R.string.czujniki_toast_wilg_cisn_gazy, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    notification.setSound(ref2);
                    notification.setVibrate(wibracja);
                }
                else if((temp0>pierwsza_temp && temp0<druga_temp)&&(wilg0>pierwsza_wilg && wilg0<druga_wilg)
                        &&(cisnienie0<pierwsza_cisnienie ||cisnienie0>druga_cisnienie)&&
                        (jakosc0<pierwsza_gazy|| jakosc0>druga_gazy))
                {
                    if((toast == null || toast.getView().getWindowVisibility() != View.VISIBLE)&&i>1)
                    {
                        i = 0;
                        toast=null;
                        toast=Toast.makeText(getApplicationContext(), R.string.czujniki_toast_cisn_gazy, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    notification.setSound(ref2);
                    notification.setVibrate(wibracja);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    {
                        try
                        {
                            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), ref2);
                            r.play();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

               else if((temp0>pierwsza_temp && temp0<druga_temp)&&(wilg0>pierwsza_wilg && wilg0<druga_wilg)
                        &&(cisnienie0>pierwsza_cisnienie &&cisnienie0<druga_cisnienie)&&
                       (jakosc0<pierwsza_gazy|| jakosc0>druga_gazy))//4
                {
                    if((toast == null || toast.getView().getWindowVisibility() != View.VISIBLE)&&i>1)
                    {
                        i = 0;
                        toast=null;
                        toast=Toast.makeText(getApplicationContext(), R.string.czujniki_toast_gazy, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    notification.setSound(ref2);
                    notification.setVibrate(wibracja);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    {
                        try
                        {
                            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), ref2);
                            r.play();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
               else if((temp0>pierwsza_temp && temp0<druga_temp)&&(wilg0<pierwsza_wilg || wilg0>druga_wilg)&&
                (cisnienie0<pierwsza_cisnienie ||cisnienie0>druga_cisnienie)&&(jakosc0>pierwsza_gazy&& jakosc0<druga_gazy))
                {
                    if((toast == null || toast.getView().getWindowVisibility() != View.VISIBLE)&&i>1)
                    {
                        i = 0;
                        toast=null;
                        toast=Toast.makeText(getApplicationContext(), R.string.czujniki_toast_wilg_cisn, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    notification.setSound(ref2);
                    notification.setVibrate(wibracja);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    {
                        try
                        {
                            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), ref2);
                            r.play();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                else if((temp0>pierwsza_temp && temp0<druga_temp)&&(wilg0<pierwsza_wilg || wilg0>druga_wilg)&&
                        (cisnienie0>pierwsza_cisnienie && cisnienie0<druga_cisnienie)&&(jakosc0>pierwsza_gazy&& jakosc0<druga_gazy))
                {
                    if((toast == null || toast.getView().getWindowVisibility() != View.VISIBLE)&&i>1)
                    {
                        i = 0;
                        toast=null;
                        toast=Toast.makeText(getApplicationContext(), R.string.czujniki_toast_wilg, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    notification.setSound(ref2);
                    notification.setVibrate(wibracja);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    {
                        try
                        {
                            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), ref2);
                            r.play();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                else if((temp0>pierwsza_temp && temp0<druga_temp)&&(wilg0<pierwsza_wilg || wilg0>druga_wilg)&&
                        (cisnienie0>pierwsza_cisnienie && cisnienie0<druga_cisnienie)&&(jakosc0<pierwsza_gazy|| jakosc0>druga_gazy))// 2 4
                {
                    if((toast == null || toast.getView().getWindowVisibility() != View.VISIBLE)&&i>1) {
                        i = 0;
                        toast=null;
                        toast=Toast.makeText(getApplicationContext(), R.string.czujniki_toast_wilg_gazy, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    notification.setSound(ref2);
                    notification.setVibrate(wibracja);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    {
                        try
                        {
                            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), ref2);
                            r.play();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                else if((temp0<pierwsza_temp || temp0>druga_temp)&&(wilg0>pierwsza_wilg && wilg0<druga_wilg)&&
                        (cisnienie0<pierwsza_cisnienie ||cisnienie0>druga_cisnienie)&&(jakosc0>pierwsza_gazy&& jakosc0<druga_gazy))
                {
                    if((toast == null || toast.getView().getWindowVisibility() != View.VISIBLE)&&i>1) {
                        i = 0;
                        toast=null;
                        toast=Toast.makeText(getApplicationContext(), R.string.czujniki_toast_temp_cisn, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    notification.setSound(ref2);
                    notification.setVibrate(wibracja);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    {
                        try
                        {
                            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), ref2);
                            r.play();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                else if((temp0<pierwsza_temp || temp0>druga_temp)&&(wilg0>pierwsza_wilg && wilg0<druga_wilg)&&
                        (cisnienie0<pierwsza_cisnienie ||cisnienie0>druga_cisnienie)&&(jakosc0<pierwsza_gazy|| jakosc0>druga_gazy))
                {
                    if((toast == null || toast.getView().getWindowVisibility() != View.VISIBLE)&&i>1)
                    {
                        i = 0;
                        toast=null;
                        toast=Toast.makeText(getApplicationContext(), R.string.czujniki_toast_temp_cisn_gazy, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    notification.setSound(ref2);
                    notification.setVibrate(wibracja);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    {
                        try
                        {
                            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), ref2);
                            r.play();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                else if((temp0>pierwsza_temp && temp0<druga_temp)&& (wilg0>pierwsza_wilg && wilg0<druga_wilg)
                        &&(cisnienie0<pierwsza_cisnienie ||cisnienie0>druga_cisnienie)&&(jakosc0>pierwsza_gazy&& jakosc0<druga_gazy))
                {
                    if((toast == null || toast.getView().getWindowVisibility() != View.VISIBLE)&&i>1)
                    { i=0;
                        toast=null;
                        toast=Toast.makeText(getApplicationContext(), R.string.czujniki_toast_cisn, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    notification.setSound(ref2);
                    notification.setVibrate(wibracja);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    {
                        try
                        {
                            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), ref2);
                            r.play();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                else if((temp0<pierwsza_temp || temp0>druga_temp)&&(wilg0>pierwsza_wilg && wilg0<druga_wilg)
                        &&(cisnienie0>pierwsza_cisnienie &&cisnienie0<druga_cisnienie)&& (jakosc0<pierwsza_gazy|| jakosc0>druga_gazy))
                {
                    if((toast == null || toast.getView().getWindowVisibility() != View.VISIBLE)&&i>1)
                    {
                        i = 0;toast=null;
                        toast=Toast.makeText(getApplicationContext(), R.string.czujniki_toast_temp_gazy, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    notification.setSound(ref2);
                    notification.setVibrate(wibracja);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    {
                        try
                        {
                            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), ref2);
                            r.play();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }








                NotificationManager nm=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                nm.notify(NOTIFICATION_ID,notification.build());

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("39", "Failed to read value.", error.toException());
            }
        });
        Intent intent= new Intent(this,Aktualne_Dane.class);
        PendingIntent pendingIntent= PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);
}
    public void arduino1(View v) {
        Intent intent = new Intent(getApplicationContext(), Klikniety_rekord.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void powiadomienie(View v)
    {

        Intent intent=new Intent(this,Aktualne_Dane.class);
        startActivity(intent);



    }



    public void sortuj(View v)
    {
        Intent intent=new Intent(this,Sortuj.class);
        startActivity(intent);
    }
    public void logowanie(View v)
    {
        Intent intent=new Intent(this,Logowanie.class);
        startActivity(intent);
    }

   public void powiadomienie_notif(View v)
    {

    }
public void zakresy_powiadomienia(View v)
{
    Intent intent=new Intent(this,Zakresy_powiadomienia.class);
    startActivity(intent);
}
    public void wyloguj(View v)
    {
        SharedPreferences.Editor editor=shared.edit();
        editor.clear();
        editor.commit();
        finish();
        startActivity(getIntent());
    }

}
