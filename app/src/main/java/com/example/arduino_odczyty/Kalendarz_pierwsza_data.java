package com.example.arduino_odczyty;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Date;

public class Kalendarz_pierwsza_data extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener{
    CalendarView kalendarz;
    SharedPreferences shared;
    TextView txt;
    String data="",rok_string="",miesiac_string="",dzien_miesiaca_string="";

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
    {
        String hour2=String.valueOf(hourOfDay);
        String minute2=String.valueOf(minute);
        if(hourOfDay<10)
        {
            hour2='0'+hour2;
        }
        if(minute<10)
        {
            minute2='0'+minute2;
        }
        data=rok_string+"-"+miesiac_string+"-"+dzien_miesiaca_string+" "+hour2+":"+minute2+":00";
        SharedPreferences.Editor editor = shared.edit();
        editor.putString("sortuj_pierwsza_data", data);
        editor.commit();
        Toast.makeText(getApplicationContext(),data,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalendarz_pierwsza_data);
        kalendarz=findViewById(R.id.kalendarz_data_rozpoczecia_treningu);
        shared=getSharedPreferences("A", Context.MODE_PRIVATE);
        txt=findViewById(R.id.txt_data_rozpoczecia_treningu);
        setTitle(R.string.tytul);
        kalendarz.setDate(new Date().getTime(),false,true);
        kalendarz.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int rok, int miesiac, int dzien_miesiaca)
            {

                 miesiac_string=String.valueOf(miesiac+1);
                 dzien_miesiaca_string=String.valueOf(dzien_miesiaca);
                miesiac++;
                if(miesiac<10)
                {
                    miesiac_string="0"+miesiac_string;
                }
                else
                {
                    miesiac_string=String.valueOf(miesiac);
                }
                if(dzien_miesiaca<10)
                {
                    dzien_miesiaca_string="0"+dzien_miesiaca_string;
                }
                else
                {
                    dzien_miesiaca_string=String.valueOf(dzien_miesiaca);
                }
                rok_string=String.valueOf(rok);
                data=rok_string+"-"+miesiac_string+"-"+dzien_miesiaca_string;


            }
        });
    }
    public void zapisz_pierwsza_date(View v)
    {

        if(data.equals(""))
        {
            Toast.makeText(getApplicationContext(),R.string.nie_wybrano_zadnej_daty,Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            DialogFragment zegar = new Zegar_1();
            zegar.show(getSupportFragmentManager(), "zegar");
            SharedPreferences.Editor editor = shared.edit();
            editor.putString("sortuj_pierwsza_data", data);
            editor.commit();
            Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();
        }
        //Intent intent=new Intent(this,Kalendarz_data_zakonczenia_treningu.class);
        //Toast.makeText(getApplicationContext(),"Data rozpoczęcia treningu:\n"+shared.getString("data_rozpoczecia_treningu_dodaj",""),Toast.LENGTH_SHORT).show();
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //this.startActivity(intent);
        //  txt.setText(shared.getString("data_rozpoczecia_treningu",""));

    }
    public void zatwierdz_i_przejdz_do_drugiej(View v)
    {
        if(data.equals(""))
        {
            Toast.makeText(getApplicationContext(),R.string.nie_wybrano_zadnej_daty,Toast.LENGTH_SHORT).show();
            return;
        }
        if(data.length()<12)
        {
            data=data+" 00:00:00";
            SharedPreferences.Editor editor = shared.edit();
            editor.putString("sortuj_pierwsza_data", data);
            editor.commit();
            Intent intent=new Intent(this,Kalendarz_druga_data.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            this.startActivity(intent);
        }
        else {
            SharedPreferences.Editor editor = shared.edit();
            editor.putString("sortuj_pierwsza_data", data);
            editor.commit();
            Intent intent=new Intent(this,Kalendarz_druga_data.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            this.startActivity(intent);
        }

    }
    public void przejdz_do_drugiej(View v)
    {
        Intent intent=new Intent(this,Kalendarz_druga_data.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(intent);
    }
    public void zatwierdz_i_zakoncz(View v)
    {
        if(data.equals(""))
        {
            Toast.makeText(getApplicationContext(),R.string.nie_wybrano_zadnej_daty,Toast.LENGTH_SHORT).show();
            return;
        }
        if(data.length()<12)
        {
            SharedPreferences.Editor editor = shared.edit();
            editor.putString("sortuj_pierwsza_data", data);
            editor.commit();
            data=data+" 00:00:00";
            Intent intent=new Intent(this,Sortuj.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            this.startActivity(intent);
        }
        else {
            SharedPreferences.Editor editor = shared.edit();
            editor.putString("sortuj_pierwsza_data", data);
            editor.commit();
            Intent intent=new Intent(this,Sortuj.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            this.startActivity(intent);
        }

    }
    @Override
    public void onBackPressed()
    {
            Intent intent=new Intent(Kalendarz_pierwsza_data.this,Sortuj.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(intent);
        return;
    }

}
