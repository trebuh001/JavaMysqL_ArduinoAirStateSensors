package com.example.arduino_odczyty;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Message;
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

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Logowanie extends AppCompatActivity {

    EditText Etlogin,Ethaslo;
    Button Btzaloguj,Btdokonta;
    String login_znaleziony="";
    String fir_login,fir_haslo1,fir_haslo2;
    SharedPreferences shared;
    FirebaseDatabase database;
    DatabaseReference ref_login,ref_haslo1,ref_haslo2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logowanie);
        setTitle(R.string.tytul);
        Etlogin=(EditText) findViewById(R.id.log_login);
        Ethaslo=(EditText) findViewById(R.id.log_haslo);
        Btzaloguj=(Button) findViewById(R.id.log_zaloguj);
        Btdokonta=(Button) findViewById(R.id.log_przejdz_do_konta);

        Btdokonta.setVisibility(View.INVISIBLE);
        shared=getSharedPreferences("A",Context.MODE_PRIVATE);
        fir_login=shared.getString("fir_login","");
        fir_haslo2=shared.getString("fir_haslo2","");
       // fir_haslo2=shared.getString()





        //Toast.makeText(getApplicationContext(),fir_haslo2,Toast.LENGTH_SHORT).show();

    }
    public void przejdz_do_konta(View v)
    {
        Intent intent= new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
    public void zaloguj(View v)
    {
        login_znaleziony="";
        String login=Etlogin.getText().toString();
        String haslo=Ethaslo.getText().toString();
        try {
            MessageDigest m=MessageDigest.getInstance("MD5");
            m.update(login.getBytes(),0,login.length());
            login=new BigInteger(1,m.digest()).toString(16);

            m.update(haslo.getBytes(),0,haslo.length());
            haslo=new BigInteger(1,m.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if(fir_login.equals(login)&&fir_haslo2.equals(haslo))
        {
            SharedPreferences.Editor editor=shared.edit();
            editor.putString("login",login_znaleziony);
            editor.commit();
            Etlogin.setVisibility(View.INVISIBLE);
            Ethaslo.setVisibility(View.INVISIBLE);
            Btzaloguj.setVisibility(View.INVISIBLE);
            Btdokonta.setVisibility(View.VISIBLE);


        }
        else
        {

            AlertDialog alertDialog = new AlertDialog.Builder(Logowanie.this).create();
            alertDialog.setTitle(R.string.niepoprawne_logowanie_tytul);
            alertDialog.setMessage(getString(R.string.niepoprawne_logowanie_opis));



            alertDialog.show();
        }



        //String typ="login";
        //BackgroundWorker backgroundWorker=new BackgroundWorker(this);
        //backgroundWorker.execute(typ,login,haslo);
      /*
       BackgroundWorker backgroundWorker=new BackgroundWorker(this, new BackgroundWorker.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                login_znaleziony=output;
                Etlogin.setVisibility(View.INVISIBLE);
                Ethaslo.setVisibility(View.INVISIBLE);
                Btzaloguj.setVisibility(View.INVISIBLE);
                Btdokonta.setVisibility(View.VISIBLE);
                shared=getSharedPreferences("A", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=shared.edit();
                editor.putString("login",login_znaleziony);
                editor.commit();

            }
        });
        backgroundWorker.execute(typ,login,haslo);*/
    }


}
