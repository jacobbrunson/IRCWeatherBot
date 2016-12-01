import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/*
 * Weather service class, using the Gson library, licenced under the Apache 2.0 license.
 * This class is responsible for retrieving and parsing data from the Open Weather Map API
 */
public class WeatherService {

    // Open Weather Map API key
    private static final String key = "d5f2d800f10315451a373dc5c5d1516f";

    // API endpoint for getting weather data
    private static final String endpoint = "http://api.openweathermap.org/data/2.5/weather?q=%s&APPID=%s";


    // This method connects to the API and requests data
    public static WeatherData getWeather(String location) {
        try {
            // Make a GET request to the API
            URL url = new URL(String.format(endpoint, location, key));
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");

            // Read the data returned from the API
            BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream()));
            StringBuilder result = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();

            // Return the parsed data
            return parseJSON(result.toString());

        } catch (Exception e) {
            System.out.println("Failed to fetch weather data");
        }
        // Return null if failure
        return null;
    }

    // This method parses the JSON data returned by the API
    private static WeatherData parseJSON(String json) {
        // Parse entire JSON string and convert to objet
        JsonObject object = new JsonParser().parse(json).getAsJsonObject();

        // Get the object under the "main" key
        JsonObject main = object.getAsJsonObject("main");

        // Get the temperatures from "main" object
        double temp = main.get("temp").getAsDouble();
        double high = main.get("temp_max").getAsDouble();
        double low = main.get("temp_min").getAsDouble();

        // Get weather from the main object of first element of "weather" array in the "main" object
        String weather = object.getAsJsonArray("weather").get(0).getAsJsonObject().get("main").getAsString();

        // Get the location name from the root object
        String location = object.get("name").getAsString();

        // Return fetched data as a WeatherData object
        return new WeatherData(temp, high, low, weather, location);
    }

}
