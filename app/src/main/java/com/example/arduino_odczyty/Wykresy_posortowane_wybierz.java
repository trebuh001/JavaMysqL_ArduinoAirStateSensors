package com.example.arduino_odczyty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Wykresy_posortowane_wybierz extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wykresy_posortowane_wybierz);
        setTitle(R.string.tytul);
    }
    public void wykres_temperatura(View v)
    {
        Intent intent= new Intent(getApplicationContext(),Graf_posortowane.class);
        startActivity(intent);
    }
    public void wykres_wilgotnosc(View v)
    {
        Intent intent= new Intent(getApplicationContext(),Graf_posortowane_wilg.class);
        startActivity(intent);
    }
    public void wykres_cisnienie(View v)
    {
        Intent intent= new Intent(getApplicationContext(),Graf_posortowane_cisnienie.class);
        startActivity(intent);
    }
    public void wykres_szkodliwe_gazy(View v)
    {
        Intent intent= new Intent(getApplicationContext(),Graf_posortowane_gazy.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed()
    {
        Intent intent=new Intent(Wykresy_posortowane_wybierz.this,Posortowane_dane.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(intent);
        return;
    }
}
