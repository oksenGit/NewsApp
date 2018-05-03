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
    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return a list of {@link NewsItem} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<NewsItem> extractNews(String JSON_RESPONSE) {

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<NewsItem> newsItems = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            JSONObject reader = new JSONObject(JSON_RESPONSE);
            JSONObject response = reader.getJSONObject("response");
            JSONArray results = response.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.getJSONObject(i);

                String date = obj.getString("webPublicationDate");

                JSONObject fields = obj.getJSONObject("fields");
                String title = fields.getString("headline");
                String thumbString = fields.getString("thumbnail");
                URL thumbURL = new URL(thumbString);
                Bitmap thumb = BitmapFactory.decodeStream(thumbURL.openConnection().getInputStream());
                JSONObject blocks = obj.getJSONObject("blocks");
                JSONArray body = blocks.getJSONArray("body");

                String content = body.getJSONObject(0).getString("bodyTextSummary");

                newsItems.add(new NewsItem(title, thumb, date, content));
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
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
                // function must handle java.io.IOException here
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
