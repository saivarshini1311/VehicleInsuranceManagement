import React, { useEffect, useState } from 'react';

export default function AdminDashboardReport() {
  const [totals, setTotals] = useState({
    users: 0,
    policies: 0,
    proposals: 0,
    approvedClaims: 0,
    monthlyRevenue: 0,
    settlementRatio: '0%'
  });

  useEffect(() => {
    fetchDashboardData();
  }, []);

  const fetchDashboardData = async () => {
    try {
      await new Promise(resolve => setTimeout(resolve, 300));

      const mockData = {
        users: 3,
        policies: 3,
        proposals: 2,
        approvedClaims: 1,
        monthlyRevenue: 4250, 
        settlementRatio: '50%' 
      };

      setTotals(mockData);
    } catch (err) {
      console.error('Failed to load dashboard overview', err);
    }
  };

  return (
    <div style={{ padding: '20px', fontFamily: 'Arial, sans-serif' }}>
      <h2 style={{ marginBottom: '20px' }}>Report Overview</h2>
      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(200px, 1fr))', gap: '20px' }}>
        <div style={cardStyle}>
          <h3>Total Users</h3>
          <p>{totals.users}</p>
        </div>
        <div style={cardStyle}>
          <h3>Active Policies</h3>
          <p>{totals.policies}</p>
        </div>
        <div style={cardStyle}>
          <h3>Pending Proposals</h3>
          <p>{totals.proposals}</p>
        </div>
        <div style={cardStyle}>
          <h3>Approved Claims</h3>
          <p>{totals.approvedClaims}</p>
        </div>
        <div style={cardStyle}>
          <h3>Monthly Revenue</h3>
          <p>₹{totals.monthlyRevenue}</p>
        </div>
        <div style={cardStyle}>
          <h3>Claim Settlement Ratio</h3>
          <p>{totals.settlementRatio}</p>
        </div>
      </div>
    </div>
  );
}

const cardStyle = {
  backgroundColor: '#f8f9fa',
  padding: '20px',
  borderRadius: '10px',
  boxShadow: '0 2px 6px rgba(0,0,0,0.1)',
  textAlign: 'center'
};
