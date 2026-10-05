package com.wildfire.tracker.service;

import com.wildfire.tracker.model.WeatherData;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherService {

    private static final String BASE_URL =
            "https://api.open-meteo.com/v1/forecast";

    private final HttpClient httpClient;

    public WeatherService() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public WeatherData getCurrentWeather(double lat, double lng)
            throws IOException, InterruptedException {

        String url = BASE_URL
                + "?latitude=" + lat
                + "&longitude=" + lng
                + "&current=temperature_2m,relative_humidity_2m,"
                + "precipitation,wind_speed_10m,wind_gusts_10m"
                + "&temperature_unit=fahrenheit"
                + "&wind_speed_unit=mph"
                + "&precipitation_unit=inch";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() != 200) {
            throw new IOException(
                    "Weather API returned status " + response.statusCode()
            );
        }

        return parseWeather(response.body());
    }

    private WeatherData parseWeather(String json) {

        String current = extractObject(json, "current");

        double tempF = extractDouble(current, "temperature_2m");
        int humidityPct =
                (int) Math.round(extractDouble(current, "relative_humidity_2m"));

        double windMph = extractDouble(current, "wind_speed_10m");
        double windGustMph = extractDouble(current, "wind_gusts_10m");
        double precipitationIn = extractDouble(current, "precipitation");

        String time = extractString(current, "time");

        return new WeatherData(
                tempF,
                humidityPct,
                windMph,
                windGustMph,
                precipitationIn,
                time
        );
    }

    private String extractObject(String json, String key) {

        String search = "\"" + key + "\":";
        int keyIndex = json.indexOf(search);

        if (keyIndex == -1) {
            throw new IllegalArgumentException(
                    "Missing JSON object: " + key
            );
        }

        int start = json.indexOf('{', keyIndex);

        if (start == -1) {
            throw new IllegalArgumentException(
                    "Invalid JSON object: " + key
            );
        }

        int depth = 0;

        for (int i = start; i < json.length(); i++) {
            char c = json.charAt(i);

            if (c == '{') {
                depth++;
            } else if (c == '}') {
                depth--;

                if (depth == 0) {
                    return json.substring(start, i + 1);
                }
            }
        }

        throw new IllegalArgumentException(
                "Unclosed JSON object: " + key
        );
    }

    private double extractDouble(String json, String key) {

        String search = "\"" + key + "\":";
        int start = json.indexOf(search);

        if (start == -1) {
            throw new IllegalArgumentException(
                    "Missing JSON number: " + key
            );
        }

        start += search.length();

        int end = start;

        while (end < json.length()) {
            char c = json.charAt(end);

            if (!(Character.isDigit(c)
                    || c == '.'
                    || c == '-'
                    || c == '+')) {
                break;
            }

            end++;
        }

        return Double.parseDouble(
                json.substring(start, end)
        );
    }

    private String extractString(String json, String key) {

        String search = "\"" + key + "\":\"";
        int start = json.indexOf(search);

        if (start == -1) {
            throw new IllegalArgumentException(
                    "Missing JSON string: " + key
            );
        }

        start += search.length();

        int end = json.indexOf('"', start);

        return json.substring(start, end);
    }
}
