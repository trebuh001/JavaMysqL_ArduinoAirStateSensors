package com.example.arduino_odczyty;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class Graf_gazy extends AppCompatActivity {

    LineChart graf;
    SharedPreferences shared;
    String typ;
    Odczyty[] odczyty, odczyty2,odczyty_id;
    String[] daty_lista;
    ArrayList<String> data_lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graf_gazy);
        shared = getSharedPreferences("A", Context.MODE_PRIVATE);
        setTitle(R.string.tytul);
        graf=findViewById(R.id.graf_linoiwy_posortowane);

        // ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();


        odczyty = new Odczyty[1000000];
        odczyty_id=new Odczyty[1000000];

        BackgroundWorkerOdczytyWykresy backgroundWorker = new BackgroundWorkerOdczytyWykresy(this, new BackgroundWorkerOdczytyWykresy.AsyncResponse() {
            @Override
            public void processFinish(String[] temperatura, String[] wilgotnosc, String[] data,String[] cisnienie,String[] szk_gazy) {



                odczyty_id[0]=new Odczyty(" ");
                int i = 0,k=0;
                double temperatura2=0.0,wilgotnosc2=0.0;
                while (i< temperatura.length) {


                    odczyty[i] = new Odczyty(String.valueOf(i), temperatura[i], wilgotnosc[i],cisnienie[i],szk_gazy[i], data[i]);
                    i++;

                }


                odczyty2 = new Odczyty[i];
                System.arraycopy(odczyty, 0, odczyty2, 0, i);
                //RowAdapter adapter = new RowAdapter(Graf_posortowane.this, R.layout.layout_item, odczyty2);
                data_lista = new ArrayList<>();
                ArrayList<Entry> temp_lista = new ArrayList<>();
                ArrayList<Entry> wilg_lista = new ArrayList<>();
                ArrayList<Entry>cisnienie_lista = new ArrayList<>();
                ArrayList<Entry>gazy_lista = new ArrayList<>();
                for(int j=0;j<odczyty2.length;j++)
                {
                    temp_lista.add(new Entry(j,Float.valueOf(odczyty2[j].temperatura)));
                    wilg_lista.add(new Entry(j,Float.valueOf(odczyty2[j].wilgotnosc)));
//                   cisnienie_lista.add(new Entry(j,Float.valueOf(odczyty2[j].cisnienie)));
                    gazy_lista.add(new Entry(j,Float.valueOf(odczyty2[j].szk_gazy)));
                    data_lista.add(j,odczyty2[j].data);

                }
                LineDataSet lineDataSet1 = new LineDataSet(temp_lista,"Temperatura");
                lineDataSet1.setDrawCircles(false);
                lineDataSet1.setColor(Color.RED);

                LineDataSet lineDataSet2 = new LineDataSet(wilg_lista,"Wilgotność");
                lineDataSet2.setDrawCircles(false);
                lineDataSet2.setColor(Color.BLUE);
                LineDataSet lineDataSet3 = new LineDataSet(cisnienie_lista,"Ciśnienie");
                lineDataSet3.setDrawCircles(false);
                lineDataSet3.setColor(Color.CYAN);
                LineDataSet lineDataSet4 = new LineDataSet(gazy_lista,"Szkodliwe gazy");
                lineDataSet4.setDrawCircles(false);
                lineDataSet4.setColor(Color.MAGENTA);
                // LineDataSet lineDataSet5 = new LineDataSet(daty,"Szk. gazy");
                lineDataSet4.setDrawCircles(false);
                lineDataSet4.setColor(Color.MAGENTA);
                ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();
                daty_lista = new String[data_lista.size()];
                for(int j=0; j<data_lista.size();j++)
                {
                    daty_lista[j] =data_lista.get(j);
                }
                //lineDataSets.add(lineDataSet1);
                // lineDataSets.add(lineDataSet2);
                //lineDataSets.add(lineDataSet3);
                 lineDataSets.add(lineDataSet4);


                //graf.sc

                graf.setData(new LineData(lineDataSets));
                graf.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(daty_lista));
                graf.setVisibleXRangeMaximum(65f);
                graf.invalidate();
                graf.getLegend().setEnabled(false);
                graf.getDescription().setEnabled(false);



            }
        });

        backgroundWorker.execute();


    }


    @Override
    public void onBackPressed()
    {
        Intent intent=new Intent(Graf_gazy.this,Wykresy_wybierz.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(intent);
        return;
    }
}
