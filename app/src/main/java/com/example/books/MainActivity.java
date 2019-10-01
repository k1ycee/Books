package com.example.books;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private ProgressBar bkpb;
    TextView scr;
    TextView err;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bkpb = findViewById(R.id.bk_loading);
       scr = (TextView) findViewById(R.id.srh_result);
        err = (TextView) findViewById(R.id.bk_error);

        try{
            URL bookUrl = ApiUtil.buildUrl("cooking");
            new Bookquery(this).execute(bookUrl);

        }
        catch (Exception e){
            Log.i("Error",e.getMessage());
        }
    }

    static class Bookquery extends AsyncTask<URL , Integer , String>{
        private WeakReference<MainActivity> mainActivityWeakReference;
        public Bookquery(MainActivity mainActivity) {
            this.mainActivityWeakReference = new WeakReference<>(mainActivity);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.d("TAG", Arrays.toString(values) + "");
        }

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
            if(mainActivityWeakReference == null) return;
            MainActivity mainActivity = mainActivityWeakReference.get();

            mainActivity.bkpb.setVisibility(View.INVISIBLE);

            if (result == null){
                mainActivity.scr.setVisibility(View.INVISIBLE);
                mainActivity.err.setVisibility(View.VISIBLE);
            }else{
                mainActivity.scr.setVisibility(View.VISIBLE);
                mainActivity.err.setVisibility(View.INVISIBLE);
            }

            mainActivity.scr.setText(result);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(mainActivityWeakReference == null) return;
            MainActivity mainActivity = mainActivityWeakReference.get();

            mainActivity.bkpb.setVisibility(View.VISIBLE);
        }
    }
}
