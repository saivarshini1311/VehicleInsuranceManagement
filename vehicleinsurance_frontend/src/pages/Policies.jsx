import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './Policies.css';

export default function Policies() {
  const [activePolicies, setActivePolicies] = useState([]);
  const [expiredPolicies, setExpiredPolicies] = useState([]);
  const [user, setUser] = useState({});
  const userId = localStorage.getItem('userId');
  const token = localStorage.getItem('token');
  const BASE_URL = 'http://localhost:8080';

  useEffect(() => {
    // Fetch user's policies
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
    }).catch(err => {
      console.error("Failed to fetch policies:", err);
    });

    // Fetch user's info
    axios.get(`${BASE_URL}/api/users/${userId}`, {
      headers: { Authorization: `Bearer ${token}` },
    }).then(res => {
      setUser(res.data);
    }).catch(err => {
      console.error("Failed to fetch user:", err);
    });
  }, []);

  const downloadPolicy = async (id) => {
    try {
      const response = await axios.get(`${BASE_URL}/api/policies/download/${id}`, {
        headers: { Authorization: `Bearer ${token}` },
        responseType: 'blob',
      });

      const blob = new Blob([response.data], { type: 'application/pdf' });
      const link = document.createElement('a');
      link.href = window.URL.createObjectURL(blob);
      link.download = `policy_${id}.pdf`;
      link.click();
    } catch (error) {
      console.error("Failed to download policy:", error);
      alert("Failed to download policy.");
    }
  };

  const handleRenew = async (policyId) => {
    try {
      await axios.put(`${BASE_URL}/api/policies/${policyId}`, {
        status: "RENEWED", // You can change this based on your backend logic
      }, {
        headers: { Authorization: `Bearer ${token}` },
      });

      alert("Policy renewed successfully.");
      window.location.reload(); // Re-fetch updated data
    } catch (error) {
      console.error("Error renewing policy:", error);
      alert("Failed to renew policy.");
    }
  };

  const renderPolicyCard = (policy, isExpired = false) => (
    <div key={policy.id} className="policy-card">
      <h4>{policy.registrationNumber} - {policy.brand} {policy.model}</h4>
      <p><strong>Policy No.:</strong> {policy.policyNumber}</p>
      <p><strong>Start:</strong> {policy.startDate}</p>
      <p><strong>End:</strong> {policy.endDate}</p>
      <p><strong>Coverage:</strong> {policy.coverageDetails}</p>
      <p><strong>Premium:</strong> ₹{policy.premiumAmount}</p>

      <div className="user-info">
        <p><strong>Policy Holder:</strong> {user.name}</p>
        <p><strong>Email:</strong> {user.email}</p>
      </div>

      <div className="policy-actions">
        <button onClick={() => downloadPolicy(policy.id)}>Download PDF</button>
        {isExpired && <button onClick={() => handleRenew(policy.id)}>Renew</button>}
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
