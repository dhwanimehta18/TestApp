package com.example.testapp.testapp;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
public class JSONEx extends AppCompatActivity {
    String data = "{\n" +
            "\t\"Name\":\"Dhwani\"\n" +
            "\t\"Basic Salary\":\"15000\"\n" +
            "\t\"Hobbies\":[\"sports\",\"dance\",\"sing\"]\n" +
            "\t\"Performance\":[{\"Year\":\"2016\"},{\"Year\":\"2017\"}]\n" +
            "}";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsonex);
    }
    class MyJSON extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] params) {
            JSONObject obj = null;
            try {
                URL url = new URL("http://max4all.in/BJSC/WEBAPI/BJSCWebAPI." +
                        "php?tOP=getStudentDetails&nStandard=11");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                InputStream is = urlConnection.getInputStream();
                String res = convertStreamToString(is);
                obj = new JSONObject(res);
                Log.i("status", obj.getString("status"));
                Log.i("Basic Salary :", obj.getString("Basic Salary"));
                JSONArray hobbies = obj.getJSONArray("Hobbies");
                Log.i("Hobbies : ", hobbies.toString());
                JSONArray performance = obj.getJSONArray("Performance");
                for (int i = 0; i < performance.length(); i++) {
                    JSONObject year = (JSONObject) performance.get(i);
                    Log.i("performance : ", year.getString("Year"));
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    private String convertStreamToString(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String cnt = null;
        while ((cnt = br.readLine()) != null) {
            cnt = cnt + " ";
        }
        return cnt;
    }
}