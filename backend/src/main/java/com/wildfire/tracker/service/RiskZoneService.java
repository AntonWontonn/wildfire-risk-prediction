package com.wildfire.tracker.service;

import com.wildfire.tracker.model.RiskZone;
import com.wildfire.tracker.model.WeatherData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RiskZoneService {

    private final WeatherService weatherService;

    public RiskZoneService() {
        this.weatherService = new WeatherService();
    }

    public List<RiskZone> getAllRiskZones() {

        List<RiskZone> zones = new ArrayList<>();

        zones.add(buildLiveZone(
                "ca-001",
                "Angeles Foothills",
                "Los Angeles County",
                34.2367,
                -118.4319,
                88,
                41
        ));

        zones.add(buildLiveZone(
                "ca-002",
                "Feather River Canyon",
                "Butte County",
                39.7285,
                -121.6169,
                46,
                18
        ));

        zones.add(buildLiveZone(
                "ca-003",
                "Cleveland National Forest",
                "San Diego County",
                33.0587,
                -116.7739,
                71,
                29
        ));

        zones.add(buildLiveZone(
                "ca-004",
                "Sequoia Foothills",
                "Tulare County",
                36.4864,
                -118.5658,
                22,
                10
        ));

        zones.add(buildLiveZone(
                "ca-005",
                "Sonoma Wine Country",
                "Sonoma County",
                38.5780,
                -122.9888,
                67,
                25
        ));

        zones.add(buildLiveZone(
                "ca-006",
                "Shasta Canyon",
                "Shasta County",
                40.7909,
                -122.3928,
                39,
                15
        ));

        zones.add(buildLiveZone(
                "ca-007",
                "Santa Ana Foothills",
                "Riverside County",
                33.7175,
                -117.0242,
                93,
                45
        ));

        return Collections.unmodifiableList(zones);
    }

    private RiskZone buildLiveZone(
                    String id,
                    String name,
                    String county,
                    double lat,
                    double lng,
                    int temporaryRiskPct,
                    int temporaryDrySpellDays) {

            try {
                    WeatherData weather = weatherService.getCurrentWeather(lat, lng);

                    return new RiskZone(
                                    id,
                                    name,
                                    county,
                                    lat,
                                    lng,
                                    temporaryRiskPct,
                                    riskLevelFor(temporaryRiskPct),
                                    (int) Math.round(weather.getTemperatureF()),
                                    weather.getHumidityPct(),
                                    (int) Math.round(weather.getWindMph()),
                                    temporaryDrySpellDays,
                                    weather.getObservationTime(),
                                    "Live weather observation from Open-Meteo.");

            } catch (Exception e) {

                    System.err.println(
                                    "Unable to retrieve weather for "
                                                    + name
                                                    + ": "
                                                    + e.getMessage());

                    return new RiskZone(
                                    id,
                                    name,
                                    county,
                                    lat,
                                    lng,
                                    temporaryRiskPct,
                                    riskLevelFor(temporaryRiskPct),
                                    0,
                                    0,
                                    0,
                                    temporaryDrySpellDays,
                                    "",
                                    "Weather data temporarily unavailable.");
            }
    }

    private String riskLevelFor(int riskPct) {
            if (riskPct >= 75)
                    return "Extreme";
            if (riskPct >= 50)
                    return "High";
            if (riskPct >= 25)
                    return "Moderate";
            return "Low";
    }
}
