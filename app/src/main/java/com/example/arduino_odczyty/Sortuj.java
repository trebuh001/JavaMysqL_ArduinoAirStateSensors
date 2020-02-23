package com.example.arduino_odczyty;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Sortuj extends AppCompatActivity {
SharedPreferences shared;
TextView data1,data2,txt_sort;
EditText temp1,temp2,wilg1,wilg2,cisnienie1,cisnienie2,szk_gazy1,szk_gazy2;
RadioGroup radiogroup1,radiogroup2;
RadioButton rb_temp1,rb_temp2,rb_wilg1,rb_wilg2,rb_cisn1,rb_cisn2,rb_gazy1,rb_gazy2,rb_data1,rb_data2;
RadioButton rb_wybrany;
String data1_string,data2_string;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sortuj);
        setTitle(R.string.tytul);
        shared = getSharedPreferences("A", Context.MODE_PRIVATE);
        data1=findViewById(R.id.sortuj_data_1);
        data2=findViewById(R.id.sortuj_data_2);
        temp1=findViewById(R.id.sortuj_temp_1);
        temp2=findViewById(R.id.sortuj_temp_2);
        wilg1=findViewById(R.id.sortuj_wilg_1);
        wilg2=findViewById(R.id.sortuj_wilg_2);
        cisnienie1=findViewById(R.id.sortuj_cisnienie_1);
        cisnienie2=findViewById(R.id.sortuj_cisnienie_2);
        szk_gazy1=findViewById(R.id.sortuj_gazy_1);
        szk_gazy2=findViewById(R.id.sortuj_gazy_2);
        txt_sort=findViewById(R.id.tekst_sortowanie);
        radiogroup1=findViewById(R.id.sortowanie_filtr);
        radiogroup2=findViewById(R.id.sortowanie_filtr2);
        rb_temp1=findViewById(R.id.radio_temp1);
        rb_temp2=findViewById(R.id.radio_temp2);
        rb_wilg1=findViewById(R.id.radio_wilg1);
        rb_wilg2=findViewById(R.id.radio_wilg2);
        rb_cisn1=findViewById(R.id.radio_cisn1);
        rb_cisn2=findViewById(R.id.radio_cisn2);
        rb_gazy1=findViewById(R.id.radiogazy1);
        rb_gazy2=findViewById(R.id.radiogazy2);
        rb_data1=findViewById(R.id.radiodata1);
        rb_data2=findViewById(R.id.radiodata2);
        radiogroup1.setVisibility(View.INVISIBLE);
        if(shared.getInt("radiogroup",-999)==1)
        {
            radiogroup1.setVisibility(View.INVISIBLE);
            radiogroup2.setVisibility(View.VISIBLE);
        }
        if(shared.getInt("radiogroup",-999)==0)
        {
            radiogroup1.setVisibility(View.VISIBLE);
            radiogroup2.setVisibility(View.INVISIBLE);
        }
        if((shared.contains("sortuj_pierwsza_data"))&&(shared.contains("sortuj_druga_data")))
        {
            data1.setText(shared.getString("sortuj_pierwsza_data", ""));
            data2.setText(shared.getString("sortuj_druga_data", ""));
        }
        else if(shared.contains("sortuj_pierwsza_data"))
        {
            data1.setText(shared.getString("sortuj_pierwsza_data", ""));
        }
        else if(shared.contains("sortuj_druga_data"))
        {
            data2.setText(shared.getString("sortuj_druga_data", ""));
        }




    }
    public void rodzaj_sortowania(View v)
    {
        String string=getString(R.string.Sortuj_najnizsze);
        if(txt_sort.getText().equals(string))
        {
            txt_sort.setText(R.string.Sortuj_najwyzsze);
            radiogroup2.setVisibility(View.VISIBLE);
            radiogroup1.setVisibility(View.INVISIBLE);

        }
        else
        {
            txt_sort.setText(R.string.Sortuj_najnizsze);
            radiogroup2.setVisibility(View.INVISIBLE);
            radiogroup1.setVisibility(View.VISIBLE);

        }
    //    Intent intent = new Intent(getApplicationContext(), Sortuj.class);
     //   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
      //  startActivity(intent);

    }

    public void zakres_czasu(View v)
    {
        Intent intent=new Intent(this,Kalendarz_pierwsza_data.class);
        startActivity(intent);
    }
    public void wyczysc_daty(View v)
    {
        SharedPreferences.Editor editor=shared.edit();
        editor.remove("sortuj_pierwsza_data");
        editor.remove("sortuj_druga_data");
        editor.commit();
        Intent intent=new Intent(this,Sortuj.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    public void sortowanie(View v)
    {

        SharedPreferences.Editor editor=shared.edit();
        editor.putString("sortuj_pierwsza_data",data1.getText().toString());
        editor.putString("sortuj_druga_data",data2.getText().toString());

        if(radiogroup1.getVisibility()==View.VISIBLE)
        {
            int selectedId = radiogroup1.getCheckedRadioButtonId();
            rb_wybrany =findViewById(selectedId);
            editor.putString("sortowanie_w_filtrowaniu",rb_wybrany.getText().toString());
        }
        if(radiogroup2.getVisibility()==View.VISIBLE)
        {
            int selectedId = radiogroup2.getCheckedRadioButtonId();
            rb_wybrany =findViewById(selectedId);
            editor.putString("sortowanie_w_filtrowaniu",rb_wybrany.getText().toString());

        }

        editor.putString("sortuj_pierwsza_temp",temp1.getText().toString());
        editor.putString("sortuj_druga_temp",temp2.getText().toString());

        editor.putString("sortuj_pierwsza_wilg",wilg1.getText().toString());
        editor.putString("sortuj_druga_wilg",wilg2.getText().toString());

        editor.putString("sortuj_pierwsza_cisnienie",cisnienie1.getText().toString());
        editor.putString("sortuj_druga_cisnienie",cisnienie2.getText().toString());

        editor.putString("sortuj_pierwsza_gazy",szk_gazy1.getText().toString());
        editor.putString("sortuj_druga_gazy",szk_gazy2.getText().toString());

        editor.commit();

        Intent intent=new Intent(this,Posortowane_dane.class);
        startActivity(intent);

    }

}
