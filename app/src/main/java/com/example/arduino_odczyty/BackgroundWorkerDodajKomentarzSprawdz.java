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

public class BackgroundWorkerDodajKomentarzSprawdz extends AsyncTask<String, Void, String> {
    public ProgressDialog dialog;

    String wynik,komentarz="";

    Context contextt;
    SharedPreferences shared;



    public interface AsyncResponse {
        void processFinish(String output,String output2);
    }

    public BackgroundWorkerDodajKomentarzSprawdz.AsyncResponse delegate = null;

    BackgroundWorkerDodajKomentarzSprawdz(Context context, BackgroundWorkerDodajKomentarzSprawdz.AsyncResponse delegate) {
        contextt = context;
        this.delegate = delegate;
        dialog = new ProgressDialog(contextt);


    }

    @Override
    protected String doInBackground(String... params) {


        String login_url = "http://arduinopwsz.ugu.pl/sprawdz_czy_tempwilg_ma_komentarz.php";
        String typ=params[0];
        if (typ.equals("login")) {
            try {


                String login = params[1];
                String data_dane=params[2];
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data= URLEncoder.encode("login","UTF-8")+"="+URLEncoder.encode(login,"UTF-8")+"&"+
                        URLEncoder.encode("data_dane","UTF-8")+"="+URLEncoder.encode(data_dane,"UTF-8");

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

               if(result.contains("trololo1"))
               {
                   wynik="1";
                   if(result.contains("trololo3")&&result.contains("trololo4"))
                   {
                       komentarz = result.substring(result.indexOf("trololo3") + 8, result.indexOf("trololo4"));
                   }
               }
                if(result.contains("trololo2"))
                {
                    wynik="0";
                }


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



        delegate.processFinish(wynik,komentarz);
      //    Toast.makeText(contextt,String.valueOf(temperatura),Toast.LENGTH_LONG).show();
          dialog.dismiss();


    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);


    }



}

