package com.example.android.moviestage2;

import android.text.TextUtils;
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

/**
 * Created by e244194 on 10/30/2017.
 */

public final class TrailerUtils {

    /** Tag for the log messages */
    public static final String LOG_TAG = TrailerUtils.class.getSimpleName();

    // Create an empty ArrayList that we can start adding a trailer to
    static ArrayList<VideoList> trailer = new ArrayList<>();

    /**
     * Query the iMdb dataset and return an {@link List} object to represent a single trailer.
     */
    public static List fetchTrailerData(String requestUrl) {

        trailer.clear();  // RD 9/27/17

        // Create URL object
        URL url = createTrailerUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeTrailerHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@link List} object
        List trailer = extractTrailerFeatureFromJson(jsonResponse);

        // Return the {@link List}
        return trailer;
    }


    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createTrailerUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeTrailerHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the trailer JSON results.", e);
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

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
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

    /**
     * Return an {@link List} object by parsing out information
     * about the first movie from the input moviesJSON string.
     */
    private static List extractTrailerFeatureFromJson(String trailerJSON) {
        JSONArray featureArray = null;

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(trailerJSON)) {
            return null;
        }
        try {
            JSONObject baseJsonResponse = new JSONObject(trailerJSON);

            // Checking if "items" is present
            if (baseJsonResponse.has("results")) {
                featureArray = baseJsonResponse.getJSONArray("results");

                Log.i("INSIDE TrailerUtils ", "inside if has results");
            } else
            {
                // Built placeholder JSON string in case "items" not found

            }

            for (int i = 0;i < featureArray.length();i++){
                JSONObject currentMovie = featureArray.getJSONObject(i);
                String trailerKeyString = currentMovie.getString("key");
                Log.i("TrailerUtils","The trailerKeyString is: " + trailerKeyString);


                VideoList mTrailerList = new VideoList(trailerKeyString);
                trailer.add(mTrailerList);

            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the mTrailerList JSON results", e);

        }
        Log.i("UTILS","trailer is: " + trailer);
        return trailer;
    }


}
