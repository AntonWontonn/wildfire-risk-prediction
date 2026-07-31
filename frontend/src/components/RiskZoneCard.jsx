import RiskBadge from './RiskBadge';
import { RISK_LEVEL_HEX } from '../constants';

function timeAgo(isoString) {
  const diffMs = Date.now() - new Date(isoString).getTime();
  const mins = Math.round(diffMs / 60000);
  if (mins < 60) return `${mins}m ago`;
  const hours = Math.round(mins / 60);
  if (hours < 24) return `${hours}h ago`;
  return `${Math.round(hours / 24)}d ago`;
}

export default function RiskZoneCard({ zone, selected, onSelect }) {
  return (
    <div
      className={`risk-card${selected ? ' selected' : ''}`}
      onClick={() => onSelect(zone.id)}
      role="button"
      tabIndex={0}
    >
      <div className="risk-card-top">
        <div
          className="risk-pct"
          style={{ color: RISK_LEVEL_HEX[zone.riskLevel] }}
          title={`${zone.riskPct}% chance of ignition`}
        >
          {zone.riskPct}%
        </div>
        <div style={{ flex: 1 }}>
          <h3>{zone.name}</h3>
          <p className="county">{zone.county}</p>
        </div>
        <RiskBadge level={zone.riskLevel} />
      </div>
      <div className="risk-meta">
        <span>{zone.tempF}&deg;F</span>
        <span>{zone.humidityPct}% humidity</span>
        <span>{zone.windMph} mph wind</span>
        <span>{timeAgo(zone.lastUpdate)}</span>
      </div>
      <p className="summary">{zone.summary}</p>
    </div>
  );
}
