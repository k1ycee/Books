package com.example.books;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;


public class ApiUtil {
    private ApiUtil(){}

    public static final String BASE_API_URL =
            "https://www.googleapis.com/books/v1/volumes";

    public static final String QUERY_KEY = "q";
    public static final String KEY = "key";
    public static final String API_KEY = "AIzaSyBf0mLfYwnmJpTXEyUtuXZso91pwbcX5bE";


    public static URL buildUrl (String title){

        URL url = null;

        Uri uri = Uri.parse(BASE_API_URL).buildUpon()
                .appendQueryParameter(QUERY_KEY,title)
                .appendQueryParameter(KEY,API_KEY)
                .build();
        try{
            url = new URL (uri.toString());
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

    public static ArrayList<Book> getBookJson(String json){

        final String ID = "id";
        final String TITLE = "title";
        final String SUBTITLE = "subtitle";
        final String AUTHORS = "authors";
        final String PUBLISHER = "publisher";
        final String PUBLISHED_DATE = "published date";
        final String ITEMS = "items";
        final String VOLUMEINFO = "volumeinfo";

        ArrayList<Book> books = new ArrayList<>();

        try{
            JSONObject jbooks = new JSONObject(json);
            JSONArray abooks = jbooks.getJSONArray(ITEMS);
            int numberofbooks = abooks.length();

            for (int i=0; i<numberofbooks;i++){
                JSONObject booksj = abooks.getJSONObject(i);
                JSONObject volumeInfoJ =
                        booksj.getJSONObject(VOLUMEINFO);
                int authorNum = volumeInfoJ.getJSONArray(AUTHORS).length();
                String[] authors = new String[authorNum];
                for (int j=0; j<authorNum; j++){
                    authors[j] = volumeInfoJ.getJSONArray(AUTHORS).get(j).toString();
                }
                Book book = new Book(
                        booksj.getString(ID),
                        volumeInfoJ.getString(TITLE),
                        (volumeInfoJ.isNull(SUBTITLE)?"":volumeInfoJ.getString(SUBTITLE)),
                        authors,
                        volumeInfoJ.getString(PUBLISHER),
                        volumeInfoJ.getString(PUBLISHED_DATE));
                books.add(book);
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        return books;

    }
}
