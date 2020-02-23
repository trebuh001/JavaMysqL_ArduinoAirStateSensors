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

public class Zakresy_powiadomienia extends AppCompatActivity {
    SharedPreferences shared;
    EditText temp1,temp2,wilg1,wilg2,cisnienie1,cisnienie2,szk_gazy1,szk_gazy2;
    String data1_string,data2_string;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zakresy_powiadomienia);
        setTitle(R.string.tytul);
        shared = getSharedPreferences("A", Context.MODE_PRIVATE);
        temp1=findViewById(R.id.zakres_temp_1);
        temp2=findViewById(R.id.zakres_temp_2);
        wilg1=findViewById(R.id.zakres_wilg_1);
        wilg2=findViewById(R.id.zakres_wilg_2);
        cisnienie1=findViewById(R.id.zakres_cisnienie_1);
        cisnienie2=findViewById(R.id.zakres_cisnienie_2);
        szk_gazy1=findViewById(R.id.zakres_gazy_1);
        szk_gazy2=findViewById(R.id.zakres_gazy_2);
        if((shared.contains("zakresy_pierwsza_temp"))&&(shared.contains("zakresy_druga_temp")))
        {
            temp1.setText(shared.getString("zakresy_pierwsza_temp", ""));
            temp2.setText(shared.getString("zakresy_druga_temp", ""));
        }
        else if(shared.contains("zakresy_pierwsza_temp"))
        {
            temp1.setText(shared.getString("zakresy_pierwsza_temp", ""));
        }
        else if(shared.contains("zakresy_druga_temp"))
        {
            temp2.setText(shared.getString("zakresy_druga_temp", ""));
        }

        if((shared.contains("zakresy_pierwsza_wilg"))&&(shared.contains("zakresy_druga_wilg")))
        {
            wilg1.setText(shared.getString("zakresy_pierwsza_wilg", ""));
            wilg2.setText(shared.getString("zakresy_druga_wilg", ""));
        }
        else if(shared.contains("zakresy_pierwsza_wilg"))
        {
            wilg1.setText(shared.getString("zakresy_pierwsza_wilg", ""));
        }
        else if(shared.contains("zakresy_druga_wilg"))
        {
            wilg2.setText(shared.getString("zakresy_druga_wilg", ""));
        }


        if((shared.contains("zakresy_pierwsza_cisnienie"))&&(shared.contains("zakresy_druga_cisnienie")))
        {
            cisnienie1.setText(shared.getString("zakresy_pierwsza_cisnienie", ""));
            cisnienie2.setText(shared.getString("zakresy_druga_cisnienie", ""));
        }
        else if(shared.contains("zakresy_pierwsza_cisnienie"))
        {
            cisnienie1.setText(shared.getString("zakresy_pierwsza_cisnienie", ""));
        }
        else if(shared.contains("zakresy_druga_cisnienie"))
        {
            cisnienie2.setText(shared.getString("zakresy_druga_cisnienie", ""));
        }

        if((shared.contains("zakresy_pierwsza_gazy"))&&(shared.contains("zakresy_druga_gazy")))
        {
            szk_gazy1.setText(shared.getString("zakresy_pierwsza_gazy", ""));
            szk_gazy2.setText(shared.getString("zakresy_druga_gazy", ""));
        }
        else if(shared.contains("zakresy_pierwsza_gazy"))
        {
            szk_gazy1.setText(shared.getString("zakresy_pierwsza_gazy", ""));
        }
        else if(shared.contains("zakresy_druga_gazy"))
        {
            szk_gazy2.setText(shared.getString("zakresy_druga_gazy", ""));
        }

    }

    public void  ustaw_zakresy(View v)
    {
        SharedPreferences.Editor editor=shared.edit();
        if(temp1.getText().toString().length()>0) {
            editor.putString("zakresy_pierwsza_temp", temp1.getText().toString());
        }
        else
        {
            editor.remove("zakresy_pierwsza_temp");
        }
        if(temp2.getText().toString().length()>0)
        {
            editor.putString("zakresy_druga_temp", temp2.getText().toString());
        }
        else
        {
            editor.remove("zakresy_druga_temp");
        }
        if(wilg1.getText().toString().length()>0)
        {
            editor.putString("zakresy_pierwsza_wilg",wilg1.getText().toString());
        }
        else
        {
            editor.remove("zakresy_pierwsza_wilg");
        }
        if(wilg2.getText().toString().length()>0)
        {
            editor.putString("zakresy_druga_wilg", wilg2.getText().toString());
        }
        else
        {
            editor.remove("zakresy_druga_wilg");
        }
        if(cisnienie1.getText().toString().length()>0)
        {
            editor.putString("zakresy_pierwsza_cisnienie",cisnienie1.getText().toString());
        }
        else
        {
            editor.remove("zakresy_pierwsza_cisnienie");
        }
        if(cisnienie2.getText().toString().length()>0)
        {
            editor.putString("zakresy_druga_cisnienie", cisnienie2.getText().toString());
        }
        else
        {
            editor.remove("zakresy_druga_cisnienie");
        }
        if(szk_gazy1.getText().toString().length()>0)
        {
            editor.putString("zakresy_pierwsza_gazy", szk_gazy1.getText().toString());
        }
        else
        {
            editor.remove("zakresy_pierwsza_gazy");
        }
        if(szk_gazy2.getText().toString().length()>0)
        {
            editor.putString("zakresy_druga_gazy", szk_gazy2.getText().toString());
        }
        else
        {
            editor.remove("zakresy_druga_gazy");
        }
        editor.commit();
        Toast.makeText(getApplicationContext(),"Zakresy wartości dla alarmów zostały ustawione",Toast.LENGTH_SHORT);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
