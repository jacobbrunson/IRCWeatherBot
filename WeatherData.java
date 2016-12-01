/*
 * Simple class to store weather data
 */
public class WeatherData {

    private double temp;
    private double high;
    private double low;
    private String weather;
    private String location;

    public WeatherData(double temp, double high, double low, String weather, String location) {
        setTemp(temp);
        setHigh(high);
        setLow(low);
        setWeather(weather);
        setLocation(location);
    }

    // Return the data as a formatted message
    public String toString() {
        return String.format("The weather in %s is %s. The temperature is %.1f°F, with a high of %.1f°F and a low of %.1f°F.", location, weather, temp, high, low);
    }

    // Convert from Kelvin to degrees Fahrenheit
    private double kToF(double kelvin) {
        return (kelvin - 273.15) * 1.8 + 32;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = kToF(high);
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = kToF(low);
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather.toLowerCase();
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = kToF(temp);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
