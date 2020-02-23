package com.example.arduino_odczyty;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class Aktualne_Dane extends AppCompatActivity {
    TextView temp,wilg,cisnieni,jakos,dat;

    String typ,login,temp1,wilg1,cisnienie1,szk_gazy,data1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aktualne__dane);
        typ = "login";
        setTitle(R.string.tytul);
        temp=findViewById(R.id.aktualne_dane_temp);
        wilg=findViewById(R.id.aktualne_dane_wilg);
        cisnieni=findViewById(R.id.aktualne_dane_cisnienie);
        jakos=findViewById(R.id.aktualne_dane_jakosc);
        dat=findViewById(R.id.aktualne_dane_data);
        BackgroundWorkerPokazAktualneDane backgroundWorker = new BackgroundWorkerPokazAktualneDane(this, new BackgroundWorkerPokazAktualneDane.AsyncResponse() {
            @Override
            public void processFinish(String temperatura, String wilgotnosc,String cisnienie, String jakosc,String data) {
                String temp2=getString(R.string.temp);
                String wilg2=getString(R.string.wilg);
                String cisn2=getString(R.string.cisnienie);
                String sz_ga2=getString(R.string.szk_gazy);
                String da2=getString(R.string.data);
                    temp.setText(temp2 +": "+temperatura +"\u2103");
                    wilg.setText(wilg2 +": "+wilgotnosc+"%");
                    cisnieni.setText(cisn2+": "+cisnienie+"hPa");
                    jakos.setText(sz_ga2 +": "+jakosc+"ppm");
                    dat.setText(da2+": "+data);
                    temp1=temperatura;
                    wilg1=wilgotnosc;
                    cisnienie1=cisnienie;
                    szk_gazy=jakosc;
                    data1=data;


            }
        });

        backgroundWorker.execute(typ,login);
    }
    public void odswiez(View v) {
        Intent intent = new Intent(getApplicationContext(), Aktualne_Dane.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    public void zapisz(View v)
    {

        BackgroundWorkerZapiszAktualneDaneWBazie backgroundWorker = new BackgroundWorkerZapiszAktualneDaneWBazie(this);
        backgroundWorker.execute(typ,temp1,wilg1,cisnienie1,szk_gazy,data1);
    }
}
