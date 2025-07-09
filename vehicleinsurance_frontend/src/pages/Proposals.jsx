import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Proposals.css';

export default function Proposals() {
  const [vehicles, setVehicles] = useState([]);
  const [proposals, setProposals] = useState([]);
  const [selectedVehicleId, setSelectedVehicleId] = useState('');
  const [description, setDescription] = useState('');

  const userId = localStorage.getItem('userId');
  const token = localStorage.getItem('token');
  const BASE_URL = 'http://localhost:8080';

  useEffect(() => {
    fetchVehicles();
    fetchProposals();
  }, []);

  const fetchVehicles = () => {
    axios.get(`${BASE_URL}/api/vehicles/owner/${userId}`, {
      headers: { Authorization: `Bearer ${token}` },
    })
    .then(res => setVehicles(res.data));
  };

  const fetchProposals = () => {
    axios.get(`${BASE_URL}/api/proposals/user/${userId}`, {
      headers: { Authorization: `Bearer ${token}` },
    })
    .then(res => setProposals(res.data));
  };

  const handleSubmit = () => {
    if (!selectedVehicleId || !description) {
      alert('Please select a vehicle and fill in the description');
      return;
    }

    const payload = {
      userId,
      vehicleId: selectedVehicleId,
      description
    };

    axios.post(`${BASE_URL}/api/proposals`, payload, {
      headers: { Authorization: `Bearer ${token}` },
    }).then(() => {
      setSelectedVehicleId('');
      setDescription('');
      fetchProposals();
      alert('Proposal submitted');
    });
  };

  return (
    <div className="proposal-container">
      <h2>Proposal Management</h2>

      <div className="proposal-form">
        <h3>Submit New Proposal</h3>
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
              <p><strong>Description:</strong> {p.description}</p>
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
