package com.wildfire.tracker.service;

import com.wildfire.tracker.model.RiskZone;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Placeholder data source for wildfire ignition-risk assessments.
 *
 * This is intentionally mock/sample data covering California only, standing
 * in until a real feed (e.g. NIFC, NASA FIRMS weather/fuel data) or a trained
 * risk-prediction model is wired in later. Swap {@link #getAllRiskZones()}
 * for a real data source without touching the controller or frontend contract.
 */
public class RiskZoneService {

    private static final List<RiskZone> RISK_ZONES = buildMockRiskZones();

    public List<RiskZone> getAllRiskZones() {
        return Collections.unmodifiableList(RISK_ZONES);
    }

    private static String riskLevelFor(int riskPct) {
        if (riskPct >= 75) return "Extreme";
        if (riskPct >= 50) return "High";
        if (riskPct >= 25) return "Moderate";
        return "Low";
    }

    private static RiskZone zone(String id, String name, String county, double lat, double lng,
                                  int riskPct, int tempF, int humidityPct, int windMph,
                                  int drySpellDays, String lastUpdate, String summary) {
        return new RiskZone(id, name, county, lat, lng, riskPct, riskLevelFor(riskPct),
                tempF, humidityPct, windMph, drySpellDays, lastUpdate, summary);
    }

    private static List<RiskZone> buildMockRiskZones() {
        List<RiskZone> list = new ArrayList<>();

        list.add(zone(
                "ca-001", "Angeles Foothills", "Los Angeles County",
                34.2367, -118.4319, 88, 101, 9, 32, 41,
                "2026-07-24T08:15:00Z",
                "Extreme heat, single-digit humidity, and sustained Santa Ana winds over parched chaparral."
        ));
        list.add(zone(
                "ca-002", "Feather River Canyon", "Butte County",
                39.7285, -121.6169, 46, 88, 24, 12, 18,
                "2026-07-24T06:40:00Z",
                "Warm and breezy with moderate fuel moisture; conditions easing after recent light rain."
        ));
        list.add(zone(
                "ca-003", "Cleveland National Forest", "San Diego County",
                33.0587, -116.7739, 71, 96, 14, 22, 29,
                "2026-07-24T09:05:00Z",
                "Red flag warning in effect: low humidity and gusty offshore winds over dry backcountry brush."
        ));
        list.add(zone(
                "ca-004", "Sequoia Foothills", "Tulare County",
                36.4864, -118.5658, 22, 82, 33, 8, 10,
                "2026-07-23T22:10:00Z",
                "Mild conditions and decent humidity keep ignition risk low for now."
        ));
        list.add(zone(
                "ca-005", "Sonoma Wine Country", "Sonoma County",
                38.5780, -122.9888, 67, 94, 16, 27, 25,
                "2026-07-24T07:55:00Z",
                "Afternoon winds picking up over cured grass and vineyard-adjacent wildland."
        ));
        list.add(zone(
                "ca-006", "Shasta Canyon", "Shasta County",
                40.7909, -122.3928, 39, 90, 21, 10, 15,
                "2026-07-24T05:30:00Z",
                "Warm and dry but calm winds keep spread potential moderate rather than severe."
        ));
        list.add(zone(
                "ca-007", "Santa Ana Foothills", "Riverside County",
                33.7175, -117.0242, 93, 104, 7, 35, 45,
                "2026-07-24T09:20:00Z",
                "Critical fire weather: record heat, near-zero humidity, and strong sustained winds on tinder-dry slopes."
        ));

        return list;
    }
}
