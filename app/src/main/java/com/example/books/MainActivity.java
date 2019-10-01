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
import java.util.ArrayList;

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

            TextView scr =  findViewById(R.id.srh_result);
            TextView err =  findViewById(R.id.bk_error);
            bkpb.setVisibility(View.INVISIBLE);

            if (result == null){
                scr.setVisibility(View.INVISIBLE);
                err.setVisibility(View.VISIBLE);
            }else{
                scr.setVisibility(View.VISIBLE);
                err.setVisibility(View.INVISIBLE);
            }
            ArrayList<Book> bks = ApiUtil.getBookJson(result);
            StringBuilder resultstring = new StringBuilder();
            for (Book bk : bks){
                resultstring.append(bk.title).append("\n").append(bk.publishdate).append("\n\n");
            }
            scr.setText(resultstring.toString());

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            bkpb.setVisibility(View.VISIBLE);
        }
    }
}
