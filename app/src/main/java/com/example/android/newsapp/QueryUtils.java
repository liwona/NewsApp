package com.example.android.newsapp;

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
 * Helper methods related to requesting and receiving earthquake data from Guardian.
 */
public class QueryUtils {

    private static final String LOG_TAG = NewsActivity.class.getName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Query the Guardian dataset and return a list of {@link News} objects.
     */
    public static List<News> fetchNewsData(String requestUrl) {

        // making 4 seconds delay in the app to be able to see ProgressBar
//        try {
//            Thread.sleep(4000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        // Create URL object
        URL url = createUrl(requestUrl);
        Log.v("In fetchNewsData","before they're returned");
        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = "{\n" +
                "   \"response\":{\n" +
                "      \"status\":\"ok\",\n" +
                "      \"userTier\":\"developer\",\n" +
                "      \"total\":410,\n" +
                "      \"startIndex\":1,\n" +
                "      \"pageSize\":15,\n" +
                "      \"currentPage\":1,\n" +
                "      \"pages\":28,\n" +
                "      \"orderBy\":\"newest\",\n" +
                "      \"results\":[\n" +
                "         {\n" +
                "            \"id\":\"football/2018/may/12/premier-league-farewells-final-day-rooney-toure-crouch-shaqiri\",\n" +
                "            \"type\":\"article\",\n" +
                "            \"sectionId\":\"football\",\n" +
                "            \"sectionName\":\"Football\",\n" +
                "            \"webPublicationDate\":\"2018-05-12T14:25:30Z\",\n" +
                "            \"webTitle\":\"Premier League farewells: six players who may say goodbye on the final day\",\n" +
                "            \"webUrl\":\"https://www.theguardian.com/football/2018/may/12/premier-league-farewells-final-day-rooney-toure-crouch-shaqiri\",\n" +
                "            \"apiUrl\":\"https://content.guardianapis.com/football/2018/may/12/premier-league-farewells-final-day-rooney-toure-crouch-shaqiri\",\n" +
                "            \"isHosted\":false,\n" +
                "            \"pillarId\":\"pillar/sport\",\n" +
                "            \"pillarName\":\"Sport\"\n" +
                "         },\n" +
                "         {\n" +
                "            \"id\":\"football/blog/2018/may/12/premier-league-stasis-compelling-narrative-2017-18\",\n" +
                "            \"type\":\"article\",\n" +
                "            \"sectionId\":\"football\",\n" +
                "            \"sectionName\":\"Football\",\n" +
                "            \"webPublicationDate\":\"2018-05-12T07:30:24Z\",\n" +
                "            \"webTitle\":\"Premier League of stasis still has a compelling magic all of its own | Barney Ronay\",\n" +
                "            \"webUrl\":\"https://www.theguardian.com/football/blog/2018/may/12/premier-league-stasis-compelling-narrative-2017-18\",\n" +
                "            \"apiUrl\":\"https://content.guardianapis.com/football/blog/2018/may/12/premier-league-stasis-compelling-narrative-2017-18\",\n" +
                "            \"isHosted\":false,\n" +
                "            \"pillarId\":\"pillar/sport\",\n" +
                "            \"pillarName\":\"Sport\"\n" +
                "         },\n" +
                "         {\n" +
                "            \"id\":\"football/2018/may/11/gold-keepers-why-premier-league-will-only-get-richer\",\n" +
                "            \"type\":\"article\",\n" +
                "            \"sectionId\":\"football\",\n" +
                "            \"sectionName\":\"Football\",\n" +
                "            \"webPublicationDate\":\"2018-05-11T16:54:45Z\",\n" +
                "            \"webTitle\":\"Gold keepers: why the Premier League clubs will get only richer\",\n" +
                "            \"webUrl\":\"https://www.theguardian.com/football/2018/may/11/gold-keepers-why-premier-league-will-only-get-richer\",\n" +
                "            \"apiUrl\":\"https://content.guardianapis.com/football/2018/may/11/gold-keepers-why-premier-league-will-only-get-richer\",\n" +
                "            \"isHosted\":false,\n" +
                "            \"pillarId\":\"pillar/sport\",\n" +
                "            \"pillarName\":\"Sport\"\n" +
                "         },\n" +
                "         {\n" +
                "            \"id\":\"football/live/2018/may/11/premier-league-final-day-countdown-play-offs-and-more-friday-football-live\",\n" +
                "            \"type\":\"liveblog\",\n" +
                "            \"sectionId\":\"football\",\n" +
                "            \"sectionName\":\"Football\",\n" +
                "            \"webPublicationDate\":\"2018-05-11T16:27:48Z\",\n" +
                "            \"webTitle\":\"Premier League final day countdown and more: Friday football – as it happened\",\n" +
                "            \"webUrl\":\"https://www.theguardian.com/football/live/2018/may/11/premier-league-final-day-countdown-play-offs-and-more-friday-football-live\",\n" +
                "            \"apiUrl\":\"https://content.guardianapis.com/football/live/2018/may/11/premier-league-final-day-countdown-play-offs-and-more-friday-football-live\",\n" +
                "            \"isHosted\":false,\n" +
                "            \"pillarId\":\"pillar/sport\",\n" +
                "            \"pillarName\":\"Sport\"\n" +
                "         },\n" +
                "         {\n" +
                "            \"id\":\"football/blog/2018/may/11/farewell-yaya-toure-manchester-city-midfielder-premier-league\",\n" +
                "            \"type\":\"article\",\n" +
                "            \"sectionId\":\"football\",\n" +
                "            \"sectionName\":\"Football\",\n" +
                "            \"webPublicationDate\":\"2018-05-11T16:06:34Z\",\n" +
                "            \"webTitle\":\"Farewell then Yaya Touré, Manchester City’s clanking midfield giant | Barney Ronay\",\n" +
                "            \"webUrl\":\"https://www.theguardian.com/football/blog/2018/may/11/farewell-yaya-toure-manchester-city-midfielder-premier-league\",\n" +
                "            \"apiUrl\":\"https://content.guardianapis.com/football/blog/2018/may/11/farewell-yaya-toure-manchester-city-midfielder-premier-league\",\n" +
                "            \"isHosted\":false,\n" +
                "            \"pillarId\":\"pillar/sport\",\n" +
                "            \"pillarName\":\"Sport\"\n" +
                "         },\n" +
                "         {\n" +
                "            \"id\":\"football/2018/may/10/arsene-wenger-european-super-league-premier-league-arsenal\",\n" +
                "            \"type\":\"article\",\n" +
                "            \"sectionId\":\"football\",\n" +
                "            \"sectionName\":\"Football\",\n" +
                "            \"webPublicationDate\":\"2018-05-10T21:33:12Z\",\n" +
                "            \"webTitle\":\"Arsène Wenger: European super league will force Premier League midweek\",\n" +
                "            \"webUrl\":\"https://www.theguardian.com/football/2018/may/10/arsene-wenger-european-super-league-premier-league-arsenal\",\n" +
                "            \"apiUrl\":\"https://content.guardianapis.com/football/2018/may/10/arsene-wenger-european-super-league-premier-league-arsenal\",\n" +
                "            \"isHosted\":false,\n" +
                "            \"pillarId\":\"pillar/sport\",\n" +
                "            \"pillarName\":\"Sport\"\n" +
                "         },\n" +
                "         {\n" +
                "            \"id\":\"football/live/2018/may/10/west-ham-united-v-manchester-united-premier-league-live\",\n" +
                "            \"type\":\"liveblog\",\n" +
                "            \"sectionId\":\"football\",\n" +
                "            \"sectionName\":\"Football\",\n" +
                "            \"webPublicationDate\":\"2018-05-10T21:01:04Z\",\n" +
                "            \"webTitle\":\"West Ham United 0-0 Manchester United: Premier League – as it happened\",\n" +
                "            \"webUrl\":\"https://www.theguardian.com/football/live/2018/may/10/west-ham-united-v-manchester-united-premier-league-live\",\n" +
                "            \"apiUrl\":\"https://content.guardianapis.com/football/live/2018/may/10/west-ham-united-v-manchester-united-premier-league-live\",\n" +
                "            \"isHosted\":false,\n" +
                "            \"pillarId\":\"pillar/sport\",\n" +
                "            \"pillarName\":\"Sport\"\n" +
                "         },\n" +
                "         {\n" +
                "            \"id\":\"football/2018/may/10/west-ham-manchester-united-premier-league-match-report\",\n" +
                "            \"type\":\"article\",\n" +
                "            \"sectionId\":\"football\",\n" +
                "            \"sectionName\":\"Football\",\n" +
                "            \"webPublicationDate\":\"2018-05-10T20:59:38Z\",\n" +
                "            \"webTitle\":\"Tempers flare as Manchester United seal second with draw at West Ham\",\n" +
                "            \"webUrl\":\"https://www.theguardian.com/football/2018/may/10/west-ham-manchester-united-premier-league-match-report\",\n" +
                "            \"apiUrl\":\"https://content.guardianapis.com/football/2018/may/10/west-ham-manchester-united-premier-league-match-report\",\n" +
                "            \"isHosted\":false,\n" +
                "            \"pillarId\":\"pillar/sport\",\n" +
                "            \"pillarName\":\"Sport\"\n" +
                "         },\n" +
                "         {\n" +
                "            \"id\":\"football/2018/may/10/chairman-owners-swansea-blame-for-decline\",\n" +
                "            \"type\":\"article\",\n" +
                "            \"sectionId\":\"football\",\n" +
                "            \"sectionName\":\"Football\",\n" +
                "            \"webPublicationDate\":\"2018-05-10T19:09:22Z\",\n" +
                "            \"webTitle\":\"Chairman and absentee owners must take blame for Swansea’s decline | Stuart James\",\n" +
                "            \"webUrl\":\"https://www.theguardian.com/football/2018/may/10/chairman-owners-swansea-blame-for-decline\",\n" +
                "            \"apiUrl\":\"https://content.guardianapis.com/football/2018/may/10/chairman-owners-swansea-blame-for-decline\",\n" +
                "            \"isHosted\":false,\n" +
                "            \"pillarId\":\"pillar/sport\",\n" +
                "            \"pillarName\":\"Sport\"\n" +
                "         },\n" +
                "         {\n" +
                "            \"id\":\"football/2018/may/10/carlos-carvalhal-set-to-lose-swansea-job-after-season-ends\",\n" +
                "            \"type\":\"article\",\n" +
                "            \"sectionId\":\"football\",\n" +
                "            \"sectionName\":\"Football\",\n" +
                "            \"webPublicationDate\":\"2018-05-10T10:16:18Z\",\n" +
                "            \"webTitle\":\"Carlos Carvalhal set to lose Swansea job after season ends\",\n" +
                "            \"webUrl\":\"https://www.theguardian.com/football/2018/may/10/carlos-carvalhal-set-to-lose-swansea-job-after-season-ends\",\n" +
                "            \"apiUrl\":\"https://content.guardianapis.com/football/2018/may/10/carlos-carvalhal-set-to-lose-swansea-job-after-season-ends\",\n" +
                "            \"isHosted\":false,\n" +
                "            \"pillarId\":\"pillar/sport\",\n" +
                "            \"pillarName\":\"Sport\"\n" +
                "         },\n" +
                "         {\n" +
                "            \"id\":\"football/2018/may/10/huddersfield-stay-up-premier-league-david-wagner-money\",\n" +
                "            \"type\":\"article\",\n" +
                "            \"sectionId\":\"football\",\n" +
                "            \"sectionName\":\"Football\",\n" +
                "            \"webPublicationDate\":\"2018-05-10T10:07:24Z\",\n" +
                "            \"webTitle\":\"Huddersfield staying up is Premier League’s greatest survival story\",\n" +
                "            \"webUrl\":\"https://www.theguardian.com/football/2018/may/10/huddersfield-stay-up-premier-league-david-wagner-money\",\n" +
                "            \"apiUrl\":\"https://content.guardianapis.com/football/2018/may/10/huddersfield-stay-up-premier-league-david-wagner-money\",\n" +
                "            \"isHosted\":false,\n" +
                "            \"pillarId\":\"pillar/sport\",\n" +
                "            \"pillarName\":\"Sport\"\n" +
                "         },\n" +
                "         {\n" +
                "            \"id\":\"football/2018/may/09/chelsea-huddersfield-premier-league-match-report\",\n" +
                "            \"type\":\"article\",\n" +
                "            \"sectionId\":\"football\",\n" +
                "            \"sectionName\":\"Football\",\n" +
                "            \"webPublicationDate\":\"2018-05-10T06:11:16Z\",\n" +
                "            \"webTitle\":\"Huddersfield seal Premier League survival and dent Chelsea's ambitions\",\n" +
                "            \"webUrl\":\"https://www.theguardian.com/football/2018/may/09/chelsea-huddersfield-premier-league-match-report\",\n" +
                "            \"apiUrl\":\"https://content.guardianapis.com/football/2018/may/09/chelsea-huddersfield-premier-league-match-report\",\n" +
                "            \"isHosted\":false,\n" +
                "            \"pillarId\":\"pillar/sport\",\n" +
                "            \"pillarName\":\"Sport\"\n" +
                "         },\n" +
                "         {\n" +
                "            \"id\":\"football/blog/2018/may/09/spurs-goodbye-wembley-european-mission-accomplished\",\n" +
                "            \"type\":\"article\",\n" +
                "            \"sectionId\":\"football\",\n" +
                "            \"sectionName\":\"Football\",\n" +
                "            \"webPublicationDate\":\"2018-05-09T22:11:29Z\",\n" +
                "            \"webTitle\":\"Spurs can say goodbye to Wembley with Champions League mission accomplished | Amy Lawrence\",\n" +
                "            \"webUrl\":\"https://www.theguardian.com/football/blog/2018/may/09/spurs-goodbye-wembley-european-mission-accomplished\",\n" +
                "            \"apiUrl\":\"https://content.guardianapis.com/football/blog/2018/may/09/spurs-goodbye-wembley-european-mission-accomplished\",\n" +
                "            \"isHosted\":false,\n" +
                "            \"pillarId\":\"pillar/sport\",\n" +
                "            \"pillarName\":\"Sport\"\n" +
                "         },\n" +
                "         {\n" +
                "            \"id\":\"football/live/2018/may/09/chelsea-v-huddersfield-spurs-v-newcastle-and-more-premier-league-clockwatch-live\",\n" +
                "            \"type\":\"liveblog\",\n" +
                "            \"sectionId\":\"football\",\n" +
                "            \"sectionName\":\"Football\",\n" +
                "            \"webPublicationDate\":\"2018-05-09T21:17:55Z\",\n" +
                "            \"webTitle\":\"Chelsea 1-1 Huddersfield, Spurs 1-0 Newcastle and more: Premier League clockwatch – as it happened\",\n" +
                "            \"webUrl\":\"https://www.theguardian.com/football/live/2018/may/09/chelsea-v-huddersfield-spurs-v-newcastle-and-more-premier-league-clockwatch-live\",\n" +
                "            \"apiUrl\":\"https://content.guardianapis.com/football/live/2018/may/09/chelsea-v-huddersfield-spurs-v-newcastle-and-more-premier-league-clockwatch-live\",\n" +
                "            \"isHosted\":false,\n" +
                "            \"pillarId\":\"pillar/sport\",\n" +
                "            \"pillarName\":\"Sport\"\n" +
                "         },\n" +
                "         {\n" +
                "            \"id\":\"football/2018/may/09/manchester-city-brighton-premier-league-match-report\",\n" +
                "            \"type\":\"article\",\n" +
                "            \"sectionId\":\"football\",\n" +
                "            \"sectionName\":\"Football\",\n" +
                "            \"webPublicationDate\":\"2018-05-09T21:05:47Z\",\n" +
                "            \"webTitle\":\"Manchester City shatter Premier League records in win over Brighton\",\n" +
                "            \"webUrl\":\"https://www.theguardian.com/football/2018/may/09/manchester-city-brighton-premier-league-match-report\",\n" +
                "            \"apiUrl\":\"https://content.guardianapis.com/football/2018/may/09/manchester-city-brighton-premier-league-match-report\",\n" +
                "            \"isHosted\":false,\n" +
                "            \"pillarId\":\"pillar/sport\",\n" +
                "            \"pillarName\":\"Sport\"\n" +
                "         }\n" +
                "      ]\n" +
                "   }\n" +
                "}";
//        try {
//            jsonResponse = makeHttpRequest(url);
//        } catch (IOException e) {
//            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
//        }

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        List<News> news = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link Earthquake}s
        return news;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e(LOG_TAG, "Problem building the URL", exception);
            return null;
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
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
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
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
     * Return a list of {@link News} objects that has been built up from
     * parsing the given JSON response.
     */
    public static List<News> extractFeatureFromJson(String newsJSON){
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        List<News> news = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Parse the response and build up a list of News objects with the corresponding data.
            JSONObject baseJsonResponse = new JSONObject(newsJSON);

            JSONObject newsObject = baseJsonResponse.getJSONObject("response");
            JSONArray newsArray = newsObject.getJSONArray("results");

            for (int i=0; i<=newsArray.length() - 1; i++) {
                JSONObject currentNews = newsArray.getJSONObject(i);

                // Extract the value for the key called "sectionName"
                 String sectionName = currentNews.getString("sectionName");

                // Extract the value for the key called "webPublicationDate"
                String webPublicationDate = currentNews.getString("webPublicationDate");

                // Extract the value for the key called "webTitle"
                String webTitle = currentNews.getString("webTitle");

//                // Extract the value for the key called "author"
//                String author = currentNews.getString("author");

                // Extract the value for the key called "webUrl"
                String webUrl = currentNews.getString("webUrl");

                // Create a new {@link Earthquake} object with the magnitude, location, time,
                // and url from the JSON response
                News nextNews = new News(webTitle, sectionName, webPublicationDate, "", webUrl);

                // Add the new {@link Earthquake} to the list of earthquakes.
                news.add(nextNews);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return news;
    }

}
