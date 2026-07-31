import { RISK_LEVEL_COLORS } from '../constants';

export default function RiskBadge({ level }) {
  const color = RISK_LEVEL_COLORS[level] || 'var(--color-moderate)';
  return (
    <span className="risk-badge" style={{ background: color }}>
      {level} Risk
    </span>
  );
}
