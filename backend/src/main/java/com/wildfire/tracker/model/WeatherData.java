package com.wildfire.tracker.model;

public class WeatherData {

    private final double temperatureF;
    private final int humidityPct;
    private final double windMph;
    private final double windGustMph;
    private final double precipitationIn;
    private final String observationTime;

    public WeatherData(
            double temperatureF,
            int humidityPct,
            double windMph,
            double windGustMph,
            double precipitationIn,
            String observationTime
    ) {
        this.temperatureF = temperatureF;
        this.humidityPct = humidityPct;
        this.windMph = windMph;
        this.windGustMph = windGustMph;
        this.precipitationIn = precipitationIn;
        this.observationTime = observationTime;
    }

    public double getTemperatureF() {
        return temperatureF;
    }

    public int getHumidityPct() {
        return humidityPct;
    }

    public double getWindMph() {
        return windMph;
    }

    public double getWindGustMph() {
        return windGustMph;
    }

    public double getPrecipitationIn() {
        return precipitationIn;
    }

    public String getObservationTime() {
        return observationTime;
    }
}
