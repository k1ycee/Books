package com.example.books;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ApiUtil {
    private ApiUtil(){}

    public static final String BASE_API_URL =
            "https://www.googleapis.com/books/v1/volumes";

    public static URL buildUrl (String title){
        String fullUrl = BASE_API_URL + "?q=" + title;
        URL url = null;
        try{
            url = new URL (fullUrl);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return url;
    }

    public static String getJson (URL url) throws IOException{
        HttpURLConnection connect = (HttpURLConnection)url.openConnection();
        try{
            InputStream stream = connect.getInputStream();
            Scanner scan = new Scanner(stream);
            scan.useDelimiter("\\A");
            boolean hasData = scan.hasNext();
            if (hasData){
                return scan.next();
            }else {
                return null;
            }
        }
        catch (Exception e){
            Log.i("Error",e.toString());
            return null;
        }

        finally {
            connect.disconnect();
        }

    }
}
