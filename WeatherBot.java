import org.jibble.pircbot.PircBot;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * IRC bot class, using the PircBot library, licenced under the GNU General Public License.
 * This is the main class.
 */
public class WeatherBot extends PircBot {

    // IRC server to connect to
    static final String server = "irc.freenode.net";

    // IRC channel to join
    static final String channel = "#cs2336jacob";

    // Name of bot
    static final String name = "JacobWeatherBot";

    // Default location
    static final String defaultLocation = "75080";

    // Regular expression to find a 5-digit number (i.e. find zip code)
    static final Pattern regex = Pattern.compile("(\\d{5})");

    public WeatherBot() {
        setName(name);
    }

    public void onMessage(String channel, String sender, String login, String hostname, String message) {
        message = message.toLowerCase();

        // If someone mentions the word "weather"
        if (message.contains("weather")) {
            // If unable to determine location, will use 75080 (Richardson) as default
            String location = defaultLocation;

            // Split message into separate words
            String[] words = message.split(" ");

            // If message is 2 words long (looking for "weather {location}"
            if (words.length == 2) {
                // If they say "weather {location}" then location = second word.
                // If they say "{location} weather" then location = first word
                if (words[0].equals("weather")) {
                    location = words[1];
                } else {
                    location = words[0];
                }
            // The message is longer than 2 words (e.g. a sentence)
            } else {
                Matcher matcher = regex.matcher(message);
                // Try to find a 5 digit number in the message
                if (matcher.find()) {
                    // Let the location be number (the zip code)
                    location = matcher.group(1);
                } else {
                    // If no zip code is detected, tell the user that we are assuming richardson
                    sendMessage(channel, "Unable to determine location. Assuming Richardson.");
                }
            }

            WeatherData data = WeatherService.getWeather(location);
            // If the request failed
            if (data == null) {
                // Try getting weather for the default location instead
                sendMessage(channel, "Unable to fetch weather data for " + location + ". Trying " + defaultLocation + " instead.");
                data = WeatherService.getWeather(defaultLocation);
                // If the request fails a second time
                if (data == null) {
                    sendMessage(channel, "Sorry, there is an error with the weather API.");
                }
            }
            // Convert weather data to a string
            String weather = data.toString();
            // Output weather message
            sendMessage(channel, weather);
        }
    }

    public static void main(String[] args) {

        // Instantiate weather bot
        WeatherBot bot = new WeatherBot();
        //bot.setVerbose(true);

        try {
            // Attempt to connect to the server
            bot.connect(server);
        } catch (Exception e) {
            // Could not connect
            System.out.printf("Failed to connect to %s\n", server);
        } finally {
            // If we connected, the join the server
            bot.joinChannel(channel);
        }
    }

}
