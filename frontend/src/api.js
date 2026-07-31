import { MOCK_RISK_ZONES } from './mockRiskZones';

const API_BASE = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080';

/**
 * Fetches wildfire ignition-risk zones from the Java backend. If the backend
 * isn't running yet, falls back to local mock data so the UI still renders -
 * useful while you're wiring up a real data source later.
 */
export async function fetchRiskZones() {
  try {
    const res = await fetch(`${API_BASE}/api/risk-zones`, {
      signal: AbortSignal.timeout(3000),
    });
    if (!res.ok) throw new Error(`API responded with ${res.status}`);
    const data = await res.json();
    return { riskZones: data, usingFallback: false };
  } catch (err) {
    console.warn('Falling back to mock risk zone data:', err.message);
    return { riskZones: MOCK_RISK_ZONES, usingFallback: true };
  }
}
