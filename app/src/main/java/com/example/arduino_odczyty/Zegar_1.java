package com.example.arduino_odczyty;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import java.text.DateFormat;
import java.util.Calendar;

public class Zegar_1 extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c=Calendar.getInstance();
        int godzina=c.get(Calendar.HOUR_OF_DAY);
        int minuta=c.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(),(TimePickerDialog.OnTimeSetListener)getActivity(),godzina,minuta,android.text.format.DateFormat.is24HourFormat(getActivity()));
    }
}
