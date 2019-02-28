package com.example.android.moviestage2.Reviews;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.android.moviestage2.R;

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

public final class ReviewUtils {

    /** Tag for the log messages */
    public static final String LOG_TAG = ReviewUtils.class.getSimpleName();

    // Create an empty ArrayList that we can start adding reviews to
    static ArrayList<ReviewList> reviews = new ArrayList<>();

    /**
     * Query the iMdb dataset and return an {@link List} object to represent a single movie.
     */
    public static List fetchReviewData(String requestReviewUrl) {

        reviews.clear();  // RD 2/28/19

        // Create URL object
        URL url = createReviewUrl(requestReviewUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = reviewMakeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@link List} object
        List movies = reviewExtractFeatureFromJson(jsonResponse);

        // Return the {@link List}
        return movies;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createReviewUrl(String stringUrl) {
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
    private static String reviewMakeHttpRequest(URL url) throws IOException {
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
                jsonResponse = reviewReadFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the movie JSON results.", e);
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
    private static String reviewReadFromStream(InputStream inputStream) throws IOException {
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
     * about the first review from the input reviewsJSON string.
     */
    private static List reviewExtractFeatureFromJson(String reviewsJSON) {
        JSONArray reviewArray = null;

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(reviewsJSON)) {
            return null;
        }
        try {
            JSONObject baseJsonResponse = new JSONObject(reviewsJSON);

            // Checking if "items" is present
            if (baseJsonResponse.has("results")) {
                reviewArray = baseJsonResponse.getJSONArray("results");

                Log.i("INSIDE ReviewUtils.java", "inside if has results");
            } else
            {
                // Built placeholder JSON string in case "items" not found
            }


            for (int i = 0;i < reviewArray.length();i++){
                JSONObject currentReview = reviewArray.getJSONObject(i);
                String authorString = currentReview.getString("author");
                String contentString = currentReview.getString("content");

                ReviewList rReviewList = new ReviewList(authorString, contentString);
                reviews.add(rReviewList);

            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the rReviewList JSON results", e);

        }
        Log.i("ReviewUTILS","reviews is: " + reviews);
        return reviews;
    }



}
