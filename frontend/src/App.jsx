import { useEffect, useState } from 'react';
import './App.css';
import Sidebar from './components/Sidebar';
import MapView from './components/MapView';
import BrandMark from './components/BrandMark';
import { fetchRiskZones } from './api';

export default function App() {
  const [riskZones, setRiskZones] = useState([]);
  const [selectedId, setSelectedId] = useState(null);
  const [collapsed, setCollapsed] = useState(false);
  const [usingFallback, setUsingFallback] = useState(false);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    let cancelled = false;
    fetchRiskZones().then(({ riskZones, usingFallback }) => {
      if (cancelled) return;
      setRiskZones(riskZones);
      setUsingFallback(usingFallback);
      setLoading(false);
    });
    return () => {
      cancelled = true;
    };
  }, []);

  return (
    <div className="app">
      <header className="app-header">
        <span className="brand-mark">
          <BrandMark />
        </span>
        <h1>Wildfire Risk Detector</h1>
        <span className="subtitle">California &middot; Ignition Risk Forecast</span>
        <span className="live-dot">
          <span className="dot" />
          {loading ? 'Loading' : usingFallback ? 'Sample Data' : 'Live'}
        </span>
      </header>
      <div className="app-body">
        <Sidebar
          riskZones={riskZones}
          selectedId={selectedId}
          onSelect={setSelectedId}
          collapsed={collapsed}
        />
        <div className="map-wrap">
          <button
            className="collapse-toggle"
            onClick={() => setCollapsed((c) => !c)}
            title={collapsed ? 'Show risk zone list' : 'Hide risk zone list'}
          >
            {collapsed ? '☰' : '✕'}
          </button>
          <MapView riskZones={riskZones} selectedId={selectedId} onSelect={setSelectedId} />
        </div>
      </div>
    </div>
  );
}
