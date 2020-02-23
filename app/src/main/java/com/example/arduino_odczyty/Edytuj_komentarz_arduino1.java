package com.example.arduino_odczyty;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Edytuj_komentarz_arduino1 extends AppCompatActivity {
    SharedPreferences shared;
    TextView txtTemp,txtWilg,txtCisnienie,txtSzk_gazy,txtData;
    String typ,login;
    EditText etKoment;
    String id;
    String komentarz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edytuj_komentarz_arduino1);

        shared=getSharedPreferences("A", Context.MODE_PRIVATE);

        txtTemp=findViewById(R.id.edytuj_komentarz_temp);
        txtWilg=findViewById(R.id.edytuj_komentarz_wilg);
        txtData=findViewById(R.id.edytuj_komentarz_data);
        setTitle(R.string.tytul);
        txtCisnienie=findViewById(R.id.edytuj_komentarz_cisnienie);
        txtSzk_gazy=findViewById(R.id.edytuj_komentarz_szk_gazy);
        etKoment=findViewById(R.id.et_edytuj_komentarz);
        etKoment.setText(shared.getString("komentarz",""));
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
    }

    public void edytuj_komentarz(View v)
    {
        komentarz = etKoment.getText().toString();
        if(!komentarz.equals("")&& !komentarz.contains("trololo"))
        {

            BackgroundWorkerEdytujKomentarz backgroundWorker = new BackgroundWorkerEdytujKomentarz(this);
            backgroundWorker.execute(typ, login, komentarz, shared.getString("data", ""));
            Intent intent = new Intent(Edytuj_komentarz_arduino1.this, Klikniety_rekord.class);
            //this.startActivity(new Intent(Twoje_treningi_dodaj.this,MainActivity.class));
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            this.startActivity(intent);
        }
        else
        {
            Toast.makeText(getApplicationContext(),R.string.warunek_trololo,Toast.LENGTH_SHORT).show();
        }
    }
    public void usun_komentarz(View v)
    {
        BackgroundWorkerUsunKomentarz backgroundWorker = new BackgroundWorkerUsunKomentarz(this);
        backgroundWorker.execute(typ,login,shared.getString("data",""));
        Intent intent=new Intent(Edytuj_komentarz_arduino1.this,Klikniety_rekord.class);
        //this.startActivity(new Intent(Twoje_treningi_dodaj.this,MainActivity.class));
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(intent);
    }
}
