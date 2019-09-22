package com.example.books;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private ProgressBar bkpb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bkpb = findViewById(R.id.bk_loading);

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
            TextView err = (TextView) findViewById(R.id.bk_error);
            bkpb.setVisibility(View.INVISIBLE);

            if (result == null){
                scr.setVisibility(View.INVISIBLE);
                err.setVisibility(View.VISIBLE);
            }else{
                scr.setVisibility(View.VISIBLE);
                err.setVisibility(View.INVISIBLE);
            }

            scr.setText(result);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            bkpb.setVisibility(View.VISIBLE);
        }
    }
}
