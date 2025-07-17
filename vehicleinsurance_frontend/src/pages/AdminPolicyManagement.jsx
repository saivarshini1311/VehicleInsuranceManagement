import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './AdminDashboard.css';

function AdminPolicyManagement() {
  const [policies, setPolicies] = useState([]);
  const [statusFilter, setStatusFilter] = useState('ACTIVE');
  const [vehicleFilter, setVehicleFilter] = useState('');
  const [userEmail, setUserEmail] = useState('');
  const token = localStorage.getItem('token');
const mockPolicies = [
  {
    id: 1,
    policyNumber: 'POL20250001',
    premiumAmount: 1200,
    coverageDetails: 'Third-Party Insurance for Honda Activa 6G',
    startDate: '2025-07-17',
    endDate: '2026-07-17',
    status: 'ACTIVE',
    user: {
      id: 4,
      name: 'Saivarshini',
      email: 'sai@gmail.com'
    },
    vehicle: {
      id: 4,
      brand: 'Honda',
      model: 'Activa 6G',
      registrationNumber: 'TN07AB1234',
      type: 'Bike',
      year: 2023
    }
  },
  {
    id: 2,
    policyNumber: 'POL20250002',
    premiumAmount: 4500,
    coverageDetails: 'Comprehensive Coverage for Hyundai i20',
    startDate: '2025-07-17',
    endDate: '2026-07-17',
    status: 'ACTIVE',
    user: {
      id: 2,
      name: 'Jeeva A',
      email: 'jeeva111@gmail.com'
    },
    vehicle: {
      id: 2,
      brand: 'Hyundai',
      model: 'i20',
      registrationNumber: 'TN01AB1234',
      type: 'Car',
      year: 2020
    }
  }
];

  // Fetch policies from API
  useEffect(() => {
    fetchPolicies();
  }, [statusFilter]);

  const fetchPolicies = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/api/policies/status/${statusFilter}`, {
        headers: { Authorization: `Bearer ${token}` }
      });
      // Merge real data with mock data
      const merged = [...mockPolicies, ...response.data];
      setPolicies(merged);
    } catch (error) {
      console.error('Error fetching policies:', error);
      setPolicies(mockPolicies); // fallback to mock only
    }
  };

  const handleRenew = async (policyId) => {
    const isMock = mockPolicies.some(p => p.id === policyId);
    if (isMock) {
      alert('Mock policy renewed successfully (simulated)');
      return;
    }

    try {
      await axios.post(`http://localhost:8080/api/policies/renew/${policyId}`, {}, {
        headers: { Authorization: `Bearer ${token}` }
      });
      alert('Policy renewed successfully');
      fetchPolicies();
    } catch (error) {
      alert('Failed to renew policy');
    }
  };

  const handleCancel = async (policyId) => {
    const reason = prompt("Enter cancellation reason (e.g., fraud, user request):");
    if (!reason) return;

    const isMock = mockPolicies.some(p => p.id === policyId);
    if (isMock) {
      alert(`Mock policy cancelled with reason: ${reason} (simulated)`);
      return;
    }

    try {
      await axios.post(`http://localhost:8080/api/policies/cancel/${policyId}`, { reason }, {
        headers: { Authorization: `Bearer ${token}` }
      });
      alert('Policy canceled');
      fetchPolicies();
    } catch (error) {
      alert('Failed to cancel policy');
    }
  };

  const filteredPolicies = policies.filter(policy => {
    const matchesStatus = policy.status === statusFilter;
    const matchesVehicle = vehicleFilter ? policy.vehicle?.type?.toLowerCase().includes(vehicleFilter.toLowerCase()) : true;
    const matchesUser = userEmail ? policy.user?.email?.toLowerCase().includes(userEmail.toLowerCase()) : true;
    return matchesStatus && matchesVehicle && matchesUser;
  });

  const handleDownloadPdf = (policyId) => {
    const isMock = mockPolicies.some(p => p.id === policyId);
    if (isMock) {
      alert('Simulating PDF download for mock policy.');
    } else {
      window.open(`http://localhost:8080/api/policies/${policyId}/pdf`, '_blank');
    }
  };

  return (
    <div className="admin-container">
      <h2>Policy Management</h2>

      <div className="filters">
        <label>Status: </label>
        <select value={statusFilter} onChange={(e) => setStatusFilter(e.target.value)}>
          <option value="ACTIVE">Active</option>
          <option value="EXPIRED">Expired</option>
        </select>

        <label>Vehicle Type: </label>
        <input
          type="text"
          placeholder="e.g. Car, Bike"
          value={vehicleFilter}
          onChange={(e) => setVehicleFilter(e.target.value)}
        />

        <label>User Email: </label>
        <input
          type="text"
          placeholder="Search by email"
          value={userEmail}
          onChange={(e) => setUserEmail(e.target.value)}
        />
      </div>

      <div className="policy-list">
        {filteredPolicies.length === 0 ? (
          <p>No policies found.</p>
        ) : (
          filteredPolicies.map(policy => (
            <div key={policy.id} className="policy-card">
              <h4>Policy ID: {policy.id}</h4>
              <p><strong>User:</strong> {policy.user?.name} ({policy.user?.email})</p>
              <p><strong>Vehicle:</strong> {policy.vehicle?.brand} {policy.vehicle?.model} ({policy.vehicle?.registrationNumber})</p>
              <p><strong>Status:</strong> {policy.status}</p>
              <p><strong>Issued:</strong> {policy.startDate || policy.issueDate}</p>
              <p><strong>Expires:</strong> {policy.endDate || policy.expiryDate}</p>
              <div className="actions">
                <button onClick={() => handleDownloadPdf(policy.id)} className="approve-btn">Download PDF</button>
                {policy.status === 'EXPIRED' && (
                  <button onClick={() => handleRenew(policy.id)} className="approve-btn">Renew</button>
                )}
                {policy.status === 'ACTIVE' && (
                  <button onClick={() => handleCancel(policy.id)} className="reject-btn">Cancel</button>
                )}
              </div>
            </div>
          ))
        )}
      </div>
    </div>
  );
}

export default AdminPolicyManagement;
