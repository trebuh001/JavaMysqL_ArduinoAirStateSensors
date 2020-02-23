package com.example.arduino_odczyty;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class BackgroundWorkerOdczyty extends AsyncTask<String, Void, String> {
   public ProgressDialog dialog;

    String temperatura,wilgotnosc,data,cisnienie,szk_gazy,srednie_wartosci;
    String[] temperaturaa,wilgotnoscc,dataa,cisnieniee,szk_gazyy,srednie_wartoscii;
    Context contextt;
    String lad;
    SharedPreferences shared;



    public interface AsyncResponse {
        void processFinish(String[] output,String[] output2,String[] output3,String[] output4,String[] output5,String[] output6);
    }

    public BackgroundWorkerOdczyty.AsyncResponse delegate = null;

    BackgroundWorkerOdczyty(Context context, BackgroundWorkerOdczyty.AsyncResponse delegate) {
        contextt = context;
        this.delegate = delegate;
        dialog = new ProgressDialog(contextt);


    }

    @Override
    protected String doInBackground(String... params) {


        String login_url = "http://arduinopwsz.ugu.pl/wyswietl_dane.php";

        try {


            URL url = new URL(login_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();

            outputStream.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String result = "";
            String line = "";

            while ((line = bufferedReader.readLine()) != null) {


                result += line;


            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            temperatura= result.substring(result.indexOf("trololoo") + 8, result.indexOf("trololooo"));
            wilgotnosc= result.substring(result.indexOf("trololoooo") + 10, result.indexOf("trololooooo"));
            data= result.substring(result.indexOf("trololoooooo") + 12, result.indexOf("trololooooooo"));
            cisnienie=result.substring(result.indexOf("trololo1") + 8, result.indexOf("trololo2"));
            szk_gazy=result.substring(result.indexOf("trololo3") + 8, result.indexOf("trololo4"));
            srednie_wartosci=result.substring(result.indexOf("trololo5") + 8, result.indexOf("trololo6"));



            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



        return null;
    }



    @Override
    protected void onPreExecute() {

        dialog.create();

        dialog.setMessage(contextt.getResources().getString(R.string.ladowanie));
        dialog.show();




    }

    @Override
    protected void onPostExecute(String result) {


        temperaturaa=temperatura.split("\t");
        wilgotnoscc=wilgotnosc.split("\t");
        dataa=data.split("\t");
        cisnieniee=cisnienie.split("\t");
        szk_gazyy=szk_gazy.split("\t");
        srednie_wartoscii=srednie_wartosci.split("\t");
        delegate.processFinish(temperaturaa,wilgotnoscc,dataa,cisnieniee,szk_gazyy,srednie_wartoscii);
        //  Toast.makeText(contextt,String.valueOf(temperatura),Toast.LENGTH_LONG).show();
        dialog.dismiss();


    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);


    }



}
