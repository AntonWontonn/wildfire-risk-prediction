# Wildfire Risk Detector (California) - Test Build

A wildfire **ignition-risk** detector scoped to California, built with a React
frontend (Leaflet map + risk zone sidebar) and a small Java backend. Rather
than tracking currently-burning fires, it shows a percentage likelihood of a
wildfire starting at a given location, derived from conditions like heat,
humidity, wind, and dry-spell length. Color scheme matches your WILDFIRE logo
(orange `#e8720d` on white).

Data is currently **placeholder/mock** - seven sample California risk zones.
Swap it out later for a real feed or your risk-prediction model without
touching the frontend, as long as the JSON shape stays the same (see
`backend/.../service/RiskZoneService.java`).

## Project layout

```
wildfire-tracker/
  backend/    Java REST API (no Maven required - see below)
  frontend/   React + Vite + Leaflet app
```

## Running the backend

The backend uses only the JDK's built-in `HttpServer`
(`com.sun.net.httpserver`) - no Spring Boot, no Maven, no internet access
needed to build it. You just need a JDK (11+) installed.

```bash
cd wildfire-tracker/backend
javac -d out $(find src -name "*.java")
java -cp out com.wildfire.tracker.controller.ApiServer
```

It starts on **http://localhost:8080**. Check it with:

```bash
curl http://localhost:8080/api/risk-zones
```

## Running the frontend

```bash
cd wildfire-tracker/frontend
npm install
npm run dev
```

Opens at **http://localhost:5173**. It tries to fetch risk zones from
`http://localhost:8080/api/risk-zones`; if the backend isn't running, it
falls back to the same mock data baked into the frontend, so the UI still
works standalone.

To point the frontend at a different backend URL, set `VITE_API_BASE_URL`
(e.g. in a `.env` file) before running `npm run dev` / `npm run build`.

## What's implemented

- Full-height California map (Leaflet + OpenStreetMap tiles), panning
  restricted to a California bounding box.
- Collapsible sidebar listing risk zones sorted by risk %, with a risk-level
  badge (Low / Moderate / High / Extreme), temperature, humidity, wind, and a
  relative-time stamp.
- Map markers sized and color-coded by ignition-risk percentage (extreme =
  red, high = orange, moderate = amber, low = green) - bigger and redder
  means higher risk of a fire starting there.
- Clicking a sidebar card or a map marker selects it, flies the map to it,
  and opens its popup with the risk % and contributing conditions.
- Orange/white theme sampled from your logo.

## Not yet wired up (left for you)

- Real weather/fuel-moisture data source to compute risk % (you said you'd
  decide later - NIFC, NASA FIRMS, NOAA, your own feed, etc.)
- A trained risk-prediction model isn't connected to anything yet -
  `riskPct` is currently hand-set mock data per zone. Swapping in real model
  output just means changing what `RiskZoneService#getAllRiskZones()`
  returns.
