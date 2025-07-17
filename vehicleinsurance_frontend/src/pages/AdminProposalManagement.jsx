import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './AdminDashboard.css';

function AdminProposalManagement() {
  const [proposals, setProposals] = useState([]);
  const [statusFilter, setStatusFilter] = useState('PENDING');
  const [rejectionReason, setRejectionReason] = useState('');

  const token = localStorage.getItem('token');

  useEffect(() => {
    fetchProposals();
  }, [statusFilter]);

  const fetchProposals = async () => {
    try {
      const res = await axios.get(`http://localhost:8080/api/proposals/status/${statusFilter}`, {
        headers: { Authorization: `Bearer ${token}` }
      });
      setProposals(res.data);
    } catch (error) {
      console.error('Error fetching proposals', error);
    }
  };

  const handleApprove = async (proposalId) => {
  try {
    await axios.put(`http://localhost:8080/api/proposals/approve/${proposalId}`, {}, {
      headers: { Authorization: `Bearer ${token}` }
    });
    await axios.post(`http://localhost:8080/api/quotes/generate/${proposalId}`, {}, {
      headers: { Authorization: `Bearer ${token}` }
    });

    alert('Proposal approved and quote generated.');
    fetchProposals();
  } catch (error) {
    console.error(error);
    alert('Failed to approve proposal or generate quote');
  }
};

  const handleReject = async (proposalId) => {
    if (!rejectionReason.trim()) {
      alert('Please enter a rejection reason.');
      return;
    }
    try {
      await axios.post(`http://localhost:8080/api/proposals/reject/${proposalId}`, { reason: rejectionReason }, {
        headers: { Authorization: `Bearer ${token}` }
      });
      alert('Proposal rejected.');
      setRejectionReason('');
      fetchProposals();
    } catch (error) {
      alert('Failed to reject proposal');
    }
  };

  return (
    <div className="admin-container">
      <h2>Proposal Management</h2>

      <label>Filter by Status: </label>
      <select value={statusFilter} onChange={(e) => setStatusFilter(e.target.value)}>
        <option value="PENDING">Pending</option>
        <option value="APPROVED">Approved</option>
        <option value="REJECTED">Rejected</option>
      </select>

      <div className="proposal-list">
        {proposals.length === 0 ? (
          <p>No proposals found.</p>
        ) : (
          proposals.map((proposal) => (
            <div key={proposal.id} className="proposal-card">
              <h4>Proposal ID: {proposal.id}</h4>
              <p><strong>User:</strong> {proposal.user.name} ({proposal.user.email})</p>
              <p><strong>Vehicle:</strong> {proposal.vehicle.make} {proposal.vehicle.model} ({proposal.vehicle.registrationNumber})</p>
              <p><strong>Description:</strong> {proposal.description}</p>
              <p><strong>Status:</strong> {proposal.status}</p>

              {statusFilter === 'PENDING' && (
                <div className="actions">
                  <button onClick={() => handleApprove(proposal.id)} className="approve-btn">Approve</button>
                  <input
                    type="text"
                    placeholder="Rejection reason"
                    value={rejectionReason}
                    onChange={(e) => setRejectionReason(e.target.value)}
                    className="rejection-input"
                  />
                  <button onClick={() => handleReject(proposal.id)} className="reject-btn">Reject</button>
                </div>
              )}
            </div>
          ))
        )}
      </div>
    </div>
  );
}

export default AdminProposalManagement;
