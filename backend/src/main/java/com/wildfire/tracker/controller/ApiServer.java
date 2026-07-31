package com.wildfire.tracker.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.wildfire.tracker.model.RiskZone;
import com.wildfire.tracker.service.RiskZoneService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Minimal REST API for the Wildfire Risk Detector test site.
 *
 * Deliberately dependency-free: uses the JDK's built-in
 * com.sun.net.httpserver.HttpServer instead of Spring Boot, so it compiles
 * and runs with nothing but a JDK (javac/java) - no Maven/Gradle required.
 *
 * Endpoints:
 *   GET /api/risk-zones -> JSON array of California wildfire ignition-risk assessments
 *   GET /api/health     -> simple liveness check
 */
public class ApiServer {

    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        RiskZoneService riskZoneService = new RiskZoneService();

        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/api/risk-zones", withCors(exchange -> handleRiskZones(exchange, riskZoneService)));
        server.createContext("/api/health", withCors(ApiServer::handleHealth));
        server.setExecutor(null);
        server.start();

        System.out.println("Wildfire Risk Detector API listening on http://localhost:" + PORT);
        System.out.println("Try: curl http://localhost:" + PORT + "/api/risk-zones");
    }

    private static void handleRiskZones(HttpExchange exchange, RiskZoneService service) throws IOException {
        if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            sendResponse(exchange, 405, "{\"error\":\"method not allowed\"}");
            return;
        }
        List<RiskZone> riskZones = service.getAllRiskZones();
        sendResponse(exchange, 200, toJsonArray(riskZones));
    }

    private static void handleHealth(HttpExchange exchange) throws IOException {
        sendResponse(exchange, 200, "{\"status\":\"ok\"}");
    }

    /** Wraps a handler so every response (including OPTIONS preflight) carries CORS headers. */
    private static HttpHandler withCors(HttpHandler delegate) {
        return exchange -> {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }
            delegate.handle(exchange);
        };
    }

    private static void sendResponse(HttpExchange exchange, int statusCode, String body) throws IOException {
        exchange.getResponseHeaders().add("Content-Type", "application/json; charset=utf-8");
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(statusCode, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    // ---- tiny hand-rolled JSON serialization (no external libraries) ----

    private static String toJsonArray(List<RiskZone> riskZones) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < riskZones.size(); i++) {
            if (i > 0) sb.append(",");
            sb.append(toJson(riskZones.get(i)));
        }
        return sb.append("]").toString();
    }

    private static String toJson(RiskZone zone) {
        return "{"
                + field("id", zone.getId()) + ","
                + field("name", zone.getName()) + ","
                + field("county", zone.getCounty()) + ","
                + "\"lat\":" + zone.getLat() + ","
                + "\"lng\":" + zone.getLng() + ","
                + "\"riskPct\":" + zone.getRiskPct() + ","
                + field("riskLevel", zone.getRiskLevel()) + ","
                + "\"tempF\":" + zone.getTempF() + ","
                + "\"humidityPct\":" + zone.getHumidityPct() + ","
                + "\"windMph\":" + zone.getWindMph() + ","
                + "\"drySpellDays\":" + zone.getDrySpellDays() + ","
                + field("lastUpdate", zone.getLastUpdate()) + ","
                + field("summary", zone.getSummary())
                + "}";
    }

    private static String field(String key, String value) {
        return "\"" + key + "\":\"" + escape(value) + "\"";
    }

    private static String escape(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
