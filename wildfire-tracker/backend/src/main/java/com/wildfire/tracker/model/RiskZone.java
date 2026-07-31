package com.wildfire.tracker.model;

/**
 * Represents a wildfire ignition-risk assessment for a location in California.
 * Kept as a plain POJO so it can be hand-serialized to JSON with no
 * external dependencies (see ApiServer#toJson).
 */
public class RiskZone {
    private final String id;
    private final String name;
    private final String county;
    private final double lat;
    private final double lng;
    private final int riskPct;         // 0-100, likelihood of a wildfire starting
    private final String riskLevel;    // "Low", "Moderate", "High", "Extreme"
    private final int tempF;
    private final int humidityPct;
    private final int windMph;
    private final int drySpellDays;    // consecutive days without meaningful rain
    private final String lastUpdate;   // ISO-8601 timestamp
    private final String summary;

    public RiskZone(String id, String name, String county, double lat, double lng,
                     int riskPct, String riskLevel, int tempF, int humidityPct, int windMph,
                     int drySpellDays, String lastUpdate, String summary) {
        this.id = id;
        this.name = name;
        this.county = county;
        this.lat = lat;
        this.lng = lng;
        this.riskPct = riskPct;
        this.riskLevel = riskLevel;
        this.tempF = tempF;
        this.humidityPct = humidityPct;
        this.windMph = windMph;
        this.drySpellDays = drySpellDays;
        this.lastUpdate = lastUpdate;
        this.summary = summary;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getCounty() { return county; }
    public double getLat() { return lat; }
    public double getLng() { return lng; }
    public int getRiskPct() { return riskPct; }
    public String getRiskLevel() { return riskLevel; }
    public int getTempF() { return tempF; }
    public int getHumidityPct() { return humidityPct; }
    public int getWindMph() { return windMph; }
    public int getDrySpellDays() { return drySpellDays; }
    public String getLastUpdate() { return lastUpdate; }
    public String getSummary() { return summary; }
}
