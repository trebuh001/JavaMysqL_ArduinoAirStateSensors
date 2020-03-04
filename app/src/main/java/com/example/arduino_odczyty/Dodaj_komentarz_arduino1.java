package com.example.arduino_odczyty;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Dodaj_komentarz_arduino1 extends AppCompatActivity {
    SharedPreferences shared;
    TextView txtTytul,txtTemp,txtWilg,txtCisnienie,txtSzk_gazy,txtData;
    String typ,login,data;
    EditText etKoment;
    String komentarz,wynik0;
    String id;
    Button przycisk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_komentarz_arduino1);
        shared=getSharedPreferences("A", Context.MODE_PRIVATE);
        txtTemp=findViewById(R.id.dodaj_komentarz_temp);
        txtWilg=findViewById(R.id.dodaj_komentarz_wilg);
        txtData=findViewById(R.id.dodaj_komentarz_data);
        txtCisnienie=findViewById(R.id.dodaj_komentarz_cisnienie);
        txtSzk_gazy=findViewById(R.id.dodaj_komentarz_szk_gazy);
        setTitle(R.string.tytul);
        etKoment=findViewById(R.id.et_dodaj_komentarz);
        txtTytul=findViewById(R.id.tytul);
        przycisk=findViewById(R.id.przycisk_dodaj);
        String temp2=getString(R.string.temperatura);
        String wilg2=getString(R.string.wilgotnosc);
        String cisn2=getString(R.string.cisnienie);
        String sz_ga2=getString(R.string.szk_gazy);
        txtTemp.setText(temp2+": "+shared.getString("temperatura",""));
        txtWilg.setText(wilg2+": "+shared.getString("wilgotnosc",""));
        txtCisnienie.setText(cisn2+": "+shared.getString("cisnienie",""));
        txtSzk_gazy.setText(sz_ga2+": "+shared.getString("szk_gazy",""));
        txtData.setText(shared.getString("data",""));
        id=shared.getString("id","");
        typ="login";
        login=shared.getString("login","");
        data=shared.getString("data","");

        BackgroundWorkerDodajKomentarzSprawdz backgroundWorker = new BackgroundWorkerDodajKomentarzSprawdz(this, new BackgroundWorkerDodajKomentarzSprawdz.AsyncResponse() {
            @Override
            public void processFinish(String wynik,String komentarz2) {
                wynik0=wynik;
                if(wynik=="1")
                {
                    przycisk.setText(R.string.edytuj_komentarz);
                    etKoment.setText(komentarz2);
                    txtTytul.setText(R.string.edytuj_komentarz);
                }
                else if(wynik=="0")
                {
                    przycisk.setText(R.string.dodaj_komentarz);
                }


            }
        });

        backgroundWorker.execute(typ,login,data);
    }
    public void dodaj_komentarz(View v)
    {
        komentarz = etKoment.getText().toString();

        if(!komentarz.isEmpty() && !komentarz.contains("trololo"))
        {
            if(wynik0=="0")
            {
                BackgroundWorkerDodajKomentarz backgroundWorker = new BackgroundWorkerDodajKomentarz(this);
                backgroundWorker.execute(typ, login, komentarz, shared.getString("data", ""));
                Intent intent = new Intent(Dodaj_komentarz_arduino1.this, Klikniety_rekord.class);
                //this.startActivity(new Intent(Twoje_treningi_dodaj.this,MainActivity.class));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                this.startActivity(intent);
            }  else if(wynik0=="1")
            {
                BackgroundWorkerEdytujKomentarz backgroundWorker = new BackgroundWorkerEdytujKomentarz(this);
                backgroundWorker.execute(typ, login, komentarz, shared.getString("data", ""));
                Intent intent = new Intent(Dodaj_komentarz_arduino1.this, Klikniety_rekord.class);
                //this.startActivity(new Intent(Twoje_treningi_dodaj.this,MainActivity.class));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                this.startActivity(intent);
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(),R.string.warunek_trololo,Toast.LENGTH_SHORT).show();

        }
    }


    @Override
    public void onBackPressed()
    {
        //do whatever you want the 'Back' button to do
        //as an example the 'Back' button is set to start a new Activity named 'NewActivity'
        Intent intent=new Intent(Dodaj_komentarz_arduino1.this,Klikniety_rekord.class);
        //this.startActivity(new Intent(Twoje_treningi_dodaj.this,MainActivity.class));
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(intent);
        return;
    }
}
