import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './Policies.css';

export default function Policies() {
  const [activePolicies, setActivePolicies] = useState([]);
  const [expiredPolicies, setExpiredPolicies] = useState([]);
  const userId = localStorage.getItem('userId');
  const token = localStorage.getItem('token');
  const BASE_URL = 'http://localhost:8080';

  useEffect(() => {
    axios.get(`${BASE_URL}/api/policies/user/${userId}`, {
      headers: { Authorization: `Bearer ${token}` },
    }).then(res => {
      const now = new Date();
      const active = [];
      const expired = [];

      res.data.forEach(policy => {
        const endDate = new Date(policy.endDate);
        if (endDate >= now) {
          active.push(policy);
        } else {
          expired.push(policy);
        }
      });

      setActivePolicies(active);
      setExpiredPolicies(expired);
    });
  }, []);

  const downloadPolicy = (id) => {
    window.open(`${BASE_URL}/api/policies/${id}/download`, '_blank');
  };

  const handleRenew = (policyId) => {
    window.location.href = `/policy/renew/${policyId}`;
  };

  const renderPolicyCard = (policy, isExpired = false) => (
    <div key={policy.id} className="policy-card">
      <h4>{policy.vehicle?.registrationNumber} - {policy.vehicle?.brand} {policy.vehicle?.model}</h4>
      <p><strong>Policy No.:</strong> {policy.policyNumber}</p>
      <p><strong>Start:</strong> {policy.startDate}</p>
      <p><strong>End:</strong> {policy.endDate}</p>
      <p><strong>Coverage:</strong> {policy.coverageDetails}</p>
      <p><strong>Premium:</strong> ₹{policy.premiumAmount}</p>

      <div className="policy-actions">
        <button onClick={() => downloadPolicy(policy.id)}>📥 Download PDF</button>
        {isExpired && <button onClick={() => handleRenew(policy.id)}>🔄 Renew</button>}
      </div>
    </div>
  );

  return (
    <div className="policy-container">
      <h2>Policy Management</h2>

      <div className="policy-section">
        <h3>Active Policies</h3>
        {activePolicies.length === 0 ? (
          <p>No active policies found.</p>
        ) : (
          activePolicies.map(policy => renderPolicyCard(policy))
        )}
      </div>

      <div className="policy-section">
        <h3>Expired Policies</h3>
        {expiredPolicies.length === 0 ? (
          <p>No expired policies.</p>
        ) : (
          expiredPolicies.map(policy => renderPolicyCard(policy, true))
        )}
      </div>
    </div>
  );
}
