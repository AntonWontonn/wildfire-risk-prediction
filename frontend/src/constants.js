// California-only view: rough bounding box + a center point used to fit the
// Leaflet map so the app never wanders outside the state.
export const CALIFORNIA_CENTER = [37.2, -119.4];
export const CALIFORNIA_BOUNDS = [
  [32.4, -124.6], // southwest
  [42.1, -113.9], // northeast
];
export const DEFAULT_ZOOM = 6;

// Risk-level color coding, keyed by the riskLevel string returned by the API.
export const RISK_LEVEL_COLORS = {
  Extreme: 'var(--color-extreme)',
  High: 'var(--color-high)',
  Moderate: 'var(--color-moderate)',
  Low: 'var(--color-low)',
};

export const RISK_LEVEL_HEX = {
  Extreme: '#c53030',
  High: '#e8720d',
  Moderate: '#d9971c',
  Low: '#4c9a63',
};

// Mirrors the thresholds used server-side (see RiskZoneService#riskLevelFor)
// so the UI can derive a level if only a raw percentage is available.
export function riskLevelFromPct(riskPct) {
  if (riskPct >= 75) return 'Extreme';
  if (riskPct >= 50) return 'High';
  if (riskPct >= 25) return 'Moderate';
  return 'Low';
}
