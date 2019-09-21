package com.example.books;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            URL bookUrl = ApiUtil.buildUrl("cooking");
            new Bookquery().execute(bookUrl);

        }
        catch (Exception e){
            Log.i("Error",e.getMessage());
        }
    }

    public class Bookquery extends AsyncTask<URL , Void , String>{

        @Override
        protected String doInBackground(URL... urls) {
            URL searchURL = urls[0];
            String result = null;
            try{
                result = ApiUtil.getJson(searchURL);
            }
            catch (IOException e){
                Log.e("Error",e.getMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {

            TextView scr = (TextView) findViewById(R.id.srh_result);
            scr.setText(result);

        }
    }
}
