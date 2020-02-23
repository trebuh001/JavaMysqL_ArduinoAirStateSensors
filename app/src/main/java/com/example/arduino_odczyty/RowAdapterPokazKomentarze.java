package com.example.arduino_odczyty;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RowAdapterPokazKomentarze extends ArrayAdapter<Komentarze>
{
    Context context;
    int resource;
    Komentarze dane[]=null;

    public RowAdapterPokazKomentarze( Context context, int resource,Komentarze[] dane)
    {
        super(context,resource,dane);
        this.resource=resource;
        this.context=context;
        this.dane=dane;
    }
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row=convertView;
        RowAdapterPokazKomentarze.RowBeanHolder holder=null;

        if(row==null)
        {
            LayoutInflater inflater= ((Activity)context).getLayoutInflater();
            row=inflater.inflate(resource,parent,false);
            holder=new RowAdapterPokazKomentarze.RowBeanHolder();
            holder.txtID=(TextView)row.findViewById(R.id.txtNumer_kom);
            holder.txtTemperatura=(TextView)row.findViewById(R.id.txtTemperatura_kom);
            holder.txtWilgotnosc=(TextView)row.findViewById(R.id.txtWilgotnosc_kom);
            holder.txtCisnienie=(TextView)row.findViewById(R.id.txtCisnienie_kom);
            holder.txtCo2=(TextView)row.findViewById(R.id.txtCo2_kom);
            holder.txtData=(TextView)row.findViewById(R.id.txtData_kom);
        //    holder.txtKomentarz=(TextView)row.findViewById(R.id.txtKomentarz);
            row.setTag(holder);

        }
        else
        {
            holder=(RowAdapterPokazKomentarze.RowBeanHolder)row.getTag();
        }
        Komentarze obiekt=dane[position];
        holder.txtID.setText(obiekt.id+". ");
        holder.txtTemperatura.setText(obiekt.temperatura+" ");
        holder.txtWilgotnosc.setText(obiekt.wilgotnosc+" ");
        holder.txtCisnienie.setText(obiekt.cisnienie+" ");
        holder.txtCo2.setText(obiekt.szk_gazy+" ");
        holder.txtData.setText(obiekt.data+" ");
   //     holder.txtKomentarz.setText(obiekt.komentarz+" ");

        return row;
    }
    static class RowBeanHolder
    {
        TextView txtID,txtTemperatura,txtWilgotnosc,txtData,txtKomentarz,txtCisnienie,txtCo2;
    }
}
