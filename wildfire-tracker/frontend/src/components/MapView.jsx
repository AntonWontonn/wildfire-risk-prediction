import { useEffect } from 'react';
import { MapContainer, TileLayer, CircleMarker, Popup, useMap } from 'react-leaflet';
import 'leaflet/dist/leaflet.css';
import RiskBadge from './RiskBadge';
import {
  CALIFORNIA_CENTER,
  CALIFORNIA_BOUNDS,
  DEFAULT_ZOOM,
  RISK_LEVEL_HEX,
} from '../constants';

// Pans/zooms the map to a selected risk zone without remounting it.
function FlyToSelected({ zone }) {
  const map = useMap();
  useEffect(() => {
    if (zone) {
      map.flyTo([zone.lat, zone.lng], Math.max(map.getZoom(), 8), {
        duration: 0.6,
      });
    }
  }, [zone, map]);
  return null;
}

// Scales marker radius with risk percentage so the highest-risk zones stand
// out on the map at a glance.
function radiusForRisk(riskPct, isSelected) {
  const base = 7 + (riskPct / 100) * 8;
  return isSelected ? base + 3 : base;
}

export default function MapView({ riskZones, selectedId, onSelect }) {
  const selected = riskZones.find((z) => z.id === selectedId) || null;

  return (
    <MapContainer
      center={CALIFORNIA_CENTER}
      zoom={DEFAULT_ZOOM}
      minZoom={5}
      maxBounds={CALIFORNIA_BOUNDS}
      maxBoundsViscosity={0.8}
      style={{ height: '100%', width: '100%' }}
    >
      <TileLayer
        attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
      />
      <FlyToSelected zone={selected} />
      {riskZones.map((zone) => (
        <CircleMarker
          key={zone.id}
          center={[zone.lat, zone.lng]}
          radius={radiusForRisk(zone.riskPct, zone.id === selectedId)}
          pathOptions={{
            color: '#ffffff',
            weight: 2,
            fillColor: RISK_LEVEL_HEX[zone.riskLevel] || '#e8720d',
            fillOpacity: 0.85,
          }}
          eventHandlers={{ click: () => onSelect(zone.id) }}
        >
          <Popup>
            <h3>{zone.name}</h3>
            <div className="popup-risk-pct">{zone.riskPct}% ignition risk</div>
            <RiskBadge level={zone.riskLevel} />
            <p className="summary">
              {zone.county} &middot; {zone.tempF}&deg;F &middot; {zone.humidityPct}% humidity
              &middot; {zone.windMph} mph wind
            </p>
          </Popup>
        </CircleMarker>
      ))}
    </MapContainer>
  );
}
