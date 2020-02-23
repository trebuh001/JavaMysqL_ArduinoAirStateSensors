package com.example.arduino_odczyty;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Posortowane_dane extends AppCompatActivity {
    TextView srednia_temp,srednia_wilg,srednia_cisnienie,srednia_gazy;
    ListView listView1;
    SharedPreferences shared;
    String typ;
    Odczyty[] odczyty, odczyty2,odczyty_id;
   // Date data1,data2;
    String temp1,temp2,wilg1,wilg2,cisnienie1,cisnienie2,szk_gazy1,szk_gazy2,data1,data2,rb_sortowanie;
    String tmp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posortowane_dane);
        typ = "login";
        srednia_temp=findViewById(R.id.srednie_wartosci_temp);
        srednia_wilg=findViewById(R.id.srednie_wartosci_wilg);
        srednia_cisnienie=findViewById(R.id.srednie_wartosci_cisnienie);
        srednia_gazy=findViewById(R.id.srednie_wartosci_gazy);

        setTitle(R.string.tytul);
        shared = getSharedPreferences("A", Context.MODE_PRIVATE);
        data1=shared.getString("sortuj_pierwsza_data","");
        data2=shared.getString("sortuj_druga_data","");

        temp1=shared.getString("sortuj_pierwsza_temp","");
        temp2=shared.getString("sortuj_druga_temp","");

        wilg1=shared.getString("sortuj_pierwsza_wilg","");
        wilg2=shared.getString("sortuj_druga_wilg","");

        cisnienie1=shared.getString("sortuj_pierwsza_cisnienie","");
        cisnienie2=shared.getString("sortuj_druga_cisnienie","");

        szk_gazy1=shared.getString("sortuj_pierwsza_gazy","");
        szk_gazy2=shared.getString("sortuj_druga_gazy","");
        rb_sortowanie= shared.getString("sortowanie_w_filtrowaniu","");


        odczyty = new Odczyty[1000000];
        odczyty_id=new Odczyty[1000000];

        BackgroundWorkerPosortowaneDane backgroundWorker = new BackgroundWorkerPosortowaneDane(this, new BackgroundWorkerPosortowaneDane.AsyncResponse() {
            @Override
            public void processFinish(String[] temperatura, String[] wilgotnosc, String[] cisnienie,String[] szk_gazy,String[] data,String[] srednie_wartosci) {


                srednia_temp.setText(getString(R.string.temperatura)+": "+srednie_wartosci[0]+"\u00B0C");
                srednia_wilg.setText(getString(R.string.wilgotnosc)+": "+srednie_wartosci[1]+"%");
                srednia_cisnienie.setText(getString(R.string.cisnienie)+": "+srednie_wartosci[2]+"hPa");
                srednia_gazy.setText(getString(R.string.szkodliwe_gazy)+": "+srednie_wartosci[3]+"ppm");
                String lp2=getString(R.string.lp);
                String tmp2=getString(R.string.temp);
                String wilg2=getString(R.string.wilg);
                String cisn2=getString(R.string.cisnienie);
                String sz_ga2=getString(R.string.szk_gazy);
                String da2=getString(R.string.data);
                odczyty[0] = new Odczyty(lp2,tmp2, wilg2, cisn2,sz_ga2,da2);
                odczyty_id[0]=new Odczyty(" ");
                int i = 1,k=0;
                double temperatura2=0.0,wilgotnosc2=0.0;
                while ((i - 1) < temperatura.length) {


                    odczyty[i] = new Odczyty(String.valueOf(i), temperatura[i - 1]+"\u00B0C", wilgotnosc[i - 1]+"%",cisnienie[i-1]+"hPa",szk_gazy[i-1]+"ppm", data[i - 1]);
                    i++;

                }


                odczyty2 = new Odczyty[i];
                System.arraycopy(odczyty, 0, odczyty2, 0, i);
                RowAdapter adapter = new RowAdapter(Posortowane_dane.this, R.layout.layout_item, odczyty2);
                listView1 = findViewById(R.id.Lista);
                listView1.setAdapter(adapter);

                listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override

                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Odczyty p = (Odczyty) parent.getItemAtPosition(position);
                        int i=position;
                        //Toast.makeText(getApplicationContext(),"Student:"+(position+1)+". "+p.imie+ " "+p.nazwisko+"\nKierunek: "+p.kierunek+"\nRok: "+p.rok_studiow, Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = shared.edit();

                        editor.putString("id", p.id);
                        editor.putString("temperatura", p.temperatura);
                        editor.putString("wilgotnosc", p.wilgotnosc);
                        editor.putString("szk_gazy", p.szk_gazy);
                        editor.putString("cisnienie", p.cisnienie);
                        editor.putString("data", p.data);

                        editor.commit();
                        if(!p.id.equals("LP"))
                        {
                            klik_w_rekord();
                        }


                    }

                });
            }
        });

        backgroundWorker.execute(typ,data1,data2,temp1,temp2,wilg1,wilg2,cisnienie1,cisnienie2,szk_gazy1,szk_gazy2,rb_sortowanie);
    }


    public void klik_w_rekord() {
        Intent intent = new Intent(getApplicationContext(),Dodaj_komentarz_arduino1.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    public void odswiez(View v) {
        Intent intent = new Intent(getApplicationContext(), Posortowane_dane.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    public void pokaz_graf(View v)
    {
        Intent intent = new Intent(getApplicationContext(), Wykresy_posortowane_wybierz.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    public void usun_dane(View v)
    {
        BackgroundWorkerUsunDane backgroundWorker = new BackgroundWorkerUsunDane(this);
        backgroundWorker.execute(typ,data1,data2,temp1,temp2,wilg1,wilg2,cisnienie1,cisnienie2,szk_gazy1,szk_gazy2);
        Intent intent = new Intent(Posortowane_dane.this, Sortuj.class);
        //this.startActivity(new Intent(Twoje_treningi_dodaj.this,MainActivity.class));
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(intent);

    }
    @Override
    public void onBackPressed()
    {
        Intent intent=new Intent(Posortowane_dane.this,Sortuj.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(intent);
        return;
    }
}
