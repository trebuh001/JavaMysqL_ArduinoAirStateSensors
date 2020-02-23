package com.example.arduino_odczyty;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

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

public class BackgroundWorker extends AsyncTask<String,Void,String> {

    int zmienna;
    int result2;
    Context contextt;
    android.support.v7.app.AlertDialog alertDialog;
    AlertDialog.Builder builder;
    String login,loginn;
    public ProgressDialog dialog;
    public interface AsyncResponse
    {
        void processFinish(String output);
    }
    public AsyncResponse delegate = null;



    BackgroundWorker (Context context,AsyncResponse delegate)
    {
        contextt=context;
        this.delegate=delegate;
        dialog = new ProgressDialog(contextt);
    }
    @Override
    protected String doInBackground(String... params) {
        String typ= params[0];
        String login_url="http://arduinopwsz.ugu.pl/logowanie.php";
        if(typ.equals("login"))
        {
            try
            {


                login= params[1];
                String haslo=params[2];
                URL url=new URL(login_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data= URLEncoder.encode("login","UTF-8")+"="+URLEncoder.encode(login,"UTF-8")+"&"+
                        URLEncoder.encode("haslo","UTF-8")+"="+URLEncoder.encode(haslo,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
                String result="";
                String line="";

                while((line= bufferedReader.readLine())!=null)
                {


                    result += line;

                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                if(result.contains("xdxd"))
                {


                   result=contextt.getResources().getString(R.string.bledny_login);
                    result2=0;
                }
                else
                {
                    loginn= result.substring(result.indexOf("trololo") + 7, result.indexOf("trololo2"));
                    result=contextt.getResources().getString(R.string.uzytkownik_zalogowany);
                    result2=1;
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
        //   alertDialog= new AlertDialog.Builder(contextt).create();
        //  alertDialog.setTitle("Login Status");
        builder = new android.support.v7.app.AlertDialog.Builder(contextt);
        builder.setTitle(R.string.logowanie);



    }

    @Override
    protected void onPostExecute(String result) {

           alertDialog.setMessage(result);
         alertDialog.show();
        builder.setMessage(result).setCancelable(false);
        if(result.equals(contextt.getResources().getString(R.string.uzytkownik_zalogowany)))
        {
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    delegate.processFinish(loginn);



                }
            });
        }
        else
        {
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                }
            });
        }
        android.support.v7.app.AlertDialog alert = builder.create();
        alert.show();



    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);

    }

}
