import RiskZoneCard from './RiskZoneCard';

export default function Sidebar({ riskZones, selectedId, onSelect, collapsed }) {
  const highRiskCount = riskZones.filter(
    (z) => z.riskLevel === 'High' || z.riskLevel === 'Extreme'
  ).length;

  return (
    <aside className={`sidebar${collapsed ? ' collapsed' : ''}`}>
      <div className="sidebar-header">
        <h2>California Risk Zones</h2>
        <p>
          {riskZones.length} tracked &middot; {highRiskCount} high or extreme risk
        </p>
      </div>
      <div className="sidebar-list">
        {riskZones.length === 0 && (
          <div className="empty-state">No risk zones to show right now.</div>
        )}
        {riskZones
          .slice()
          .sort((a, b) => b.riskPct - a.riskPct)
          .map((zone) => (
            <RiskZoneCard
              key={zone.id}
              zone={zone}
              selected={zone.id === selectedId}
              onSelect={onSelect}
            />
          ))}
      </div>
    </aside>
  );
}
