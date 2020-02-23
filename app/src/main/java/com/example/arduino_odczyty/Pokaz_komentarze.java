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
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Pokaz_komentarze extends AppCompatActivity {
    TextView srednia_temp,srednia_wilg,srednia_cisnienie,srednia_gazy;
    TextView txt;
    ListView listView1;
    SharedPreferences shared;
    String typ,login;
    Komentarze[] komentarze, komentarze2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokaz_komentarze);
        setTitle(R.string.tytul);
        typ = "login";
        srednia_temp=findViewById(R.id.srednie_wartosci_temp);
        srednia_wilg=findViewById(R.id.srednie_wartosci_wilg);
        srednia_cisnienie=findViewById(R.id.srednie_wartosci_cisnienie);
        srednia_gazy=findViewById(R.id.srednie_wartosci_gazy);

        shared = getSharedPreferences("A", Context.MODE_PRIVATE);
        login=shared.getString("login","");
        komentarze = new Komentarze[1000000];
        komentarze2=new Komentarze[1000000];

        BackgroundWorkerPokazKomentarze backgroundWorker = new BackgroundWorkerPokazKomentarze(this, new BackgroundWorkerPokazKomentarze.AsyncResponse() {
            @Override
            public void processFinish(String[] temperatura, String[] wilgotnosc, String[] data,String[] komentarz,String[] cisnienie, String[] szk_gazy,String[] srednie_wartosci) {

                String lp2=getString(R.string.lp);
                String temp2=getString(R.string.temp);
                String wilg2=getString(R.string.wilg);
                String cisn2=getString(R.string.cisnienie);
                String sz_ga2=getString(R.string.szk_gazy);
                String da2=getString(R.string.data);
                String ko2=getString(R.string.komentarz);
                srednia_temp.setText(getString(R.string.temperatura)+": "+srednie_wartosci[0]+"\u00B0C");
                srednia_wilg.setText(getString(R.string.wilgotnosc)+": "+srednie_wartosci[1]+"%");
                srednia_cisnienie.setText(getString(R.string.cisnienie)+": "+srednie_wartosci[2]+"hPa");
                srednia_gazy.setText(getString(R.string.szkodliwe_gazy)+": "+srednie_wartosci[3]+"ppm");
                komentarze[0] = new Komentarze(lp2,temp2, wilg2, cisn2,sz_ga2,da2,ko2);

                int i = 1,k=0;
                double temperatura2=0.0,wilgotnosc2=0.0;
                while ((i - 1) < temperatura.length) {



               //    komentarze[i] = new Komentarze("i", temperatura[i - 1]+"\u00B0C", wilgotnosc[i - 1]+"%", ""+data[i-1],""+komentarz[i-1]);
                    komentarze[i] = new Komentarze(String.valueOf(i), temperatura[i-1]+"\u00B0C", wilgotnosc[i-1]+"%",cisnienie[i-1]+"hPa",szk_gazy[i-1]+"ppm", data[i-1],komentarz[i-1]);
                    i++;

                }

                // temperatura2=temperatura2/k;
                //wilgotnosc2=wilgotnosc2/k;
                // srednia_temp_dzis.setText("Średnia dzisiejsza temperatura: "+temperatura2+"\u00B0" + "C");
                //srednia_wilg_dzis.setText("Średnia dzisiejsza wilgotność: "+wilgotnosc2+"%");

                komentarze2 = new Komentarze[i];
                System.arraycopy(komentarze, 0, komentarze2, 0, i);
                RowAdapterPokazKomentarze adapter = new RowAdapterPokazKomentarze(Pokaz_komentarze.this, R.layout.layout_item_pokaz_komentarze, komentarze2);
                listView1 = findViewById(R.id.Lista_komentarze);
                listView1.setAdapter(adapter);

                listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override

                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Komentarze p = (Komentarze) parent.getItemAtPosition(position);
                        int i=position;
                        //Toast.makeText(getApplicationContext(),"Student:"+(position+1)+". "+p.imie+ " "+p.nazwisko+"\nKierunek: "+p.kierunek+"\nRok: "+p.rok_studiow, Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = shared.edit();

                        editor.putString("id", p.id);
                        editor.putString("temperatura", p.temperatura);
                        editor.putString("wilgotnosc", p.wilgotnosc);
                        editor.putString("szk_gazy", p.szk_gazy);
                        editor.putString("cisnienie", p.cisnienie);
                        editor.putString("data", p.data);
                        editor.putString("komentarz", p.komentarz);

                        editor.commit();
                        if(!p.id.equals("LP"))
                        {
                            klik_w_rekord();
                        }


                    }

                });
            }
        });

        backgroundWorker.execute(typ,login);
    }

    public void klik_w_rekord() {
        Intent intent = new Intent(getApplicationContext(),Edytuj_komentarz_arduino1.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
