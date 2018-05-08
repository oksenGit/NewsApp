package com.example.android.newsapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {
    private static String LOG_TAG = "QueryUtils";

    private QueryUtils() {
    }

    public static ArrayList<NewsItem> extractNews(String JSON_RESPONSE) {

        ArrayList<NewsItem> newsItems = new ArrayList<>();

        try {
            JSONObject reader = new JSONObject(JSON_RESPONSE);
            JSONObject response = reader.getJSONObject("response");
            JSONArray results = response.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.getJSONObject(i);
                String date = obj.getString("webPublicationDate");
                String section = obj.getString("sectionName");
                String url = obj.getString("webUrl");
                JSONObject fields = obj.getJSONObject("fields");
                String title = fields.getString("headline");
                String thumbString = null;
                if (fields.has("thumbnail")) {
                    thumbString = fields.getString("thumbnail");
                }
                else{
                    thumbString = R.drawable.ic_launcher_background+"";
                }

                JSONObject blocks = obj.getJSONObject("blocks");
                JSONArray body = blocks.getJSONArray("body");
                String content = body.getJSONObject(0).getString("bodyTextSummary");

                newsItems.add(new NewsItem(title, thumbString, date,section,url, content));
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the News JSON results", e);
        }

        return newsItems;
    }

    public static List<NewsItem> fetchNewsData(String requestURL) {
        URL url = createUrl(requestURL);
        String JSONResponse = "";

        try {
            JSONResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<NewsItem> newsItems = extractNews(JSONResponse);

        return newsItems;
    }


    public static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e(LOG_TAG, "Error with creating URL", exception);
            return null;
        }
        return url;
    }

    public static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();
            if (urlConnection.getResponseCode() != 200)
                Log.e(LOG_TAG, "Error response code" + urlConnection.getResponseCode());
            inputStream = urlConnection.getInputStream();
            jsonResponse = readFromStream(inputStream);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem Retriving the news JSON results", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {

                inputStream.close();
            }
        }
        return jsonResponse;
    }

    public static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
