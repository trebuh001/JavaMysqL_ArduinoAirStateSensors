package com.example.arduino_odczyty;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RowAdapter extends ArrayAdapter<Odczyty>
{
    Context context;
    int resource;
   Odczyty dane[]=null;

    public RowAdapter( Context context, int resource,Odczyty[] dane)
    {
        super(context,resource,dane);
        this.resource=resource;
        this.context=context;
        this.dane=dane;
    }
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row=convertView;
        RowAdapter.RowBeanHolder holder=null;

        if(row==null)
        {
            LayoutInflater inflater= ((Activity)context).getLayoutInflater();
            row=inflater.inflate(resource,parent,false);
            holder=new RowAdapter.RowBeanHolder();
            holder.txtID=(TextView)row.findViewById(R.id.txtNumer);
            holder.txtTemperatura=(TextView)row.findViewById(R.id.txtTemperatura);
            holder.txtWilgotnosc=(TextView)row.findViewById(R.id.txtWilgotnosc);
            holder.txtCisnienie=(TextView)row.findViewById(R.id.txtCisnienie);
            holder.txtCO2=(TextView)row.findViewById(R.id.txtCo2);
            holder.txtData=(TextView)row.findViewById(R.id.txtData);

            row.setTag(holder);

        }
        else
        {
            holder=(RowAdapter.RowBeanHolder)row.getTag();
        }
        Odczyty obiekt=dane[position];
        holder.txtID.setText(obiekt.id+". ");
        holder.txtTemperatura.setText(obiekt.temperatura+" ");
        holder.txtWilgotnosc.setText(obiekt.wilgotnosc+" ");
        holder.txtCisnienie.setText(obiekt.cisnienie+" ");
        holder.txtCO2.setText(obiekt.szk_gazy+" ");
        holder.txtData.setText(obiekt.data+" ");
        return row;
    }
    static class RowBeanHolder
    {
        TextView txtID,txtTemperatura,txtWilgotnosc,txtData,txtCisnienie,txtCO2;
    }
}