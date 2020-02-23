package com.example.arduino_odczyty;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

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

public class BackgroundWorkerGrafPosortowaneDane extends AsyncTask<String, Void, String> {
    public ProgressDialog dialog;

    String temperatura,wilgotnosc,data,komentarz,cisnienie,szk_gazy;
    String[] temperaturaa,wilgotnoscc,dataa,komentarzz,cisnieniee,szk_gazyy;
    Context contextt;
    SharedPreferences shared;



    public interface AsyncResponse {
        void processFinish(String[] output, String[] output2, String[] output3, String[] output4, String[] output5);
    }

    public BackgroundWorkerGrafPosortowaneDane.AsyncResponse delegate = null;

    BackgroundWorkerGrafPosortowaneDane(Context context, BackgroundWorkerGrafPosortowaneDane.AsyncResponse delegate) {
        contextt = context;
        this.delegate = delegate;
        dialog = new ProgressDialog(contextt);


    }

    @Override
    protected String doInBackground(String... params) {


        String login_url = "http://arduinopwsz.ugu.pl/wyswietl_posortowane_dane.php";
        String typ=params[0];
        if (typ.equals("login")) {
            try {



                String data1=params[1];
                String data2=params[2];
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("data1", "UTF-8") + "=" + URLEncoder.encode(data1, "UTF-8")+"&"+
                        URLEncoder.encode("data2", "UTF-8") + "=" + URLEncoder.encode(data2, "UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
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

                temperatura = result.substring(result.indexOf("trololo1") + 8, result.indexOf("trololo2"));
                wilgotnosc = result.substring(result.indexOf("trololo3") + 8, result.indexOf("trololo4"));
                data = result.substring(result.indexOf("trololo5") + 8, result.indexOf("trololo6"));
                cisnienie = result.substring(result.indexOf("trololo7") + 8, result.indexOf("trololo8"));
                szk_gazy = result.substring(result.indexOf("trololo9") + 8, result.indexOf("trololo0"));



                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


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
        delegate.processFinish(temperaturaa,wilgotnoscc,cisnieniee,szk_gazyy,dataa);
        //    Toast.makeText(contextt,String.valueOf(temperatura),Toast.LENGTH_LONG).show();
          dialog.dismiss();


    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);


    }



}

