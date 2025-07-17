import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Proposals.css';

export default function Proposals() {
  const [vehicles, setVehicles] = useState([]);
  const [policies, setPolicies] = useState([]);
  const [proposals, setProposals] = useState([]);
  const [selectedVehicleId, setSelectedVehicleId] = useState('');
  const [selectedPolicyId, setSelectedPolicyId] = useState('');
  const [description, setDescription] = useState('');
  const [coverageType, setCoverageType] = useState('');
  const userId = localStorage.getItem('userId');
  const token = localStorage.getItem('token');
  const BASE_URL = 'http://localhost:8080';

  useEffect(() => {
    fetchVehicles();
    fetchPolicies();
    fetchProposals();
  }, []);

  const fetchVehicles = () => {
    axios.get(`${BASE_URL}/api/vehicles/owner/${userId}`, {
      headers: { Authorization: `Bearer ${token}` },
    }).then(res => setVehicles(res.data));
  };

const fetchPolicies = async () => {
  try {
    const response = await axios.get("http://localhost:8080/api/policies");
    setPolicies(response.data);
  } catch (error) {
    console.error("Error fetching policies:", error);
  }
};

  const fetchProposals = () => {
    axios.get(`${BASE_URL}/api/proposals/user/${userId}`, {
      headers: { Authorization: `Bearer ${token}` },
    }).then(res => setProposals(res.data));
  };

  const handleSubmit = () => {
    if (!selectedVehicleId || !selectedPolicyId || !description) {
      alert('Please select a vehicle, policy, and provide a description.');
      return;
    }

    const payload = {
      userId,
      vehicleId: selectedVehicleId,
      policyId: selectedPolicyId,
      remarks: description,
      coverageType: coverageType
    };

    axios.post(`${BASE_URL}/api/proposals/submit`, payload, {
      headers: { Authorization: `Bearer ${token}` },
    }).then(() => {
      setSelectedVehicleId('');
      setSelectedPolicyId('');
      setDescription('');
      setCoverageType('');
      fetchProposals();
      alert('Proposal submitted');
    }).catch((err) => {
      console.error("Proposal submission failed:", err);
      alert("Error submitting proposal");
    });
  };

  return (
    <div className="proposal-container">
      <h2>Submit New Insurance Proposal</h2>

      <div className="proposal-form">
        <select value={selectedVehicleId} onChange={(e) => setSelectedVehicleId(e.target.value)}>
          <option value="">Select Vehicle</option>
          {vehicles.map(v => (
            <option key={v.id} value={v.id}>
              {v.registrationNumber} - {v.brand} {v.model}
            </option>
          ))}
        </select>

        <textarea
          placeholder="Why do you want insurance for this vehicle?"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
        ></textarea>

        <label>Coverage Type</label>
        <select value={coverageType} onChange={(e) => setCoverageType(e.target.value)} required>
          <option value="">Select Coverage Type</option>
          <option value="Comprehensive">Comprehensive</option>
          <option value="Third-party">Third-party</option>
        </select>
        <button onClick={handleSubmit}>Submit Proposal</button>
      </div>

      <div className="proposal-list">
        <h3>My Proposals</h3>
        {proposals.length === 0 ? (
          <p>No proposals submitted yet.</p>
        ) : (
          proposals.map((p) => (
            <div key={p.id} className="proposal-card">
              <p><strong>Vehicle:</strong> {p.vehicle?.registrationNumber}</p>
              <p><strong>Description:</strong> {p.remarks}</p>
              <p><strong>Coverage:</strong> {p.coverageType}</p>
              <p><strong>Status:</strong> <span className={`status ${p.status.toLowerCase()}`}>{p.status}</span></p>
              {p.status === 'REJECTED' && <p className="rejected-reason"><strong>Reason:</strong> {p.rejectionReason}</p>}
              {p.status === 'APPROVED' && (
                <button onClick={() => window.location.href = `/quote/view/${p.id}`}>View Quote</button>
              )}
            </div>
          ))
        )}
      </div>
    </div>
  );
}
