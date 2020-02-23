package com.example.arduino_odczyty;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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

public class BackgroundWorkerUsunRekord extends AsyncTask<String,Void,String> {

    int zmienna;
    int result2;
    Context contextt;
    android.support.v7.app.AlertDialog alertDialog;
    AlertDialog.Builder builder;
    SharedPreferences shared;
    ProgressDialog dialog;
    String login;
    BackgroundWorkerUsunRekord(Context context)
    {
        contextt=context;
        dialog = new ProgressDialog(contextt);

    }
    @Override
    protected String doInBackground(String... params) {
        String typ= params[0];
        String login_url="http://arduinopwsz.ugu.pl/usun_rekord.php";
        if(typ.equals("login"))
        {
            try
            {


                String data_dane=params[1];

                URL url=new URL(login_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data= URLEncoder.encode("data","UTF-8")+"="+URLEncoder.encode(data_dane,"UTF-8");


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
        dialog.dismiss();
        Toast.makeText(contextt,R.string.rekord_zostal_usuniety, Toast.LENGTH_SHORT).show();



    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);

    }

}

