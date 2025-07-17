import React, { useEffect, useState } from 'react';
import './AdminDashboard.css';

function AdminClaimManagement() {
  const [claims, setClaims] = useState([]);
  const [filter, setFilter] = useState('PENDING');
  const [rejectionReason, setRejectionReason] = useState('');

  useEffect(() => {
    fetchClaims();
  }, [filter]);

  const fetchClaims = async () => {
    const mockData = [
      {
        id: 1,
        user: {
          id: 4,
          name: "Saivarshini",
          email: "sai@gmail.com"
        },
        vehicle: {
          make: "Honda",
          model: "Activa 6G"
        },
        accidentDate: "2025-07-17",
        claimAmount: 3500,
        status: "UNDER_REVIEW",
        documentUrls: [
          "http://localhost:8080/uploads/claim-proof-sai1.pdf",
          "http://localhost:8080/uploads/repair-bill-sai.jpg"
        ]
      },
      {
        id: 2,
        user: {
          id: 2,
          name: "Jeeva A",
          email: "jeeva111@gmail.com"
        },
        vehicle: {
          make: "Hyundai",
          model: "i20"
        },
        accidentDate: "2025-07-12",
        claimAmount: 15000,
        status: "PENDING",
        documentUrls: [
          "http://localhost:8080/uploads/jeeva-inspection.pdf",
          "http://localhost:8080/uploads/i20-damage.jpg"
        ]
      }
    ];

    const filteredClaims = mockData.filter(c => c.status === filter);
    setClaims(filteredClaims);
  };

  const handleApprove = async (claimId) => {
    alert(`Mock approve for Claim ID ${claimId}`);
    fetchClaims();
  };

  const handleReject = async (claimId) => {
    if (!rejectionReason.trim()) {
      alert('Enter a reason for rejection.');
      return;
    }
    alert(`Mock reject for Claim ID ${claimId} with reason: ${rejectionReason}`);
    setRejectionReason('');
    fetchClaims();
  };

  const handleSettle = async (claimId) => {
    alert(`Settle for Claim ID ${claimId}`);
    fetchClaims();
  };

  const handleRequestDocuments = async (claimId) => {
    alert(`Document request for Claim ID ${claimId}`);
  };

  const handleViewDocument = (docUrl) => {
    window.open(docUrl, '_blank');
  };

  return (
    <div className="admin-container">
      <h2>Claim Management</h2>

      <label>Filter by Status:</label>
      <select value={filter} onChange={(e) => setFilter(e.target.value)}>
        <option value="PENDING">Pending</option>
        <option value="UNDER_REVIEW">Under Review</option>
        <option value="APPROVED">Approved</option>
        <option value="REJECTED">Rejected</option>
        <option value="SETTLED">Settled</option>
      </select>

      <div className="claim-list">
        {claims.length === 0 ? (
          <p>No claims found.</p>
        ) : (
          claims.map((claim) => (
            <div key={claim.id} className="claim-card">
              <h4>Claim ID: {claim.id}</h4>
              <p><strong>User:</strong> {claim.user?.name} ({claim.user?.email})</p>
              <p><strong>Vehicle:</strong> {claim.vehicle?.make} {claim.vehicle?.model}</p>
              <p><strong>Accident Date:</strong> {claim.accidentDate}</p>
              <p><strong>Claim Amount:</strong> ₹{claim.claimAmount}</p>
              <p><strong>Status:</strong> {claim.status}</p>

              {claim.documentUrls?.length > 0 && (
                <div>
                  <strong>Documents:</strong>
                  <ul>
                    {claim.documentUrls.map((url, idx) => (
                      <li key={idx}>
                        <button onClick={() => handleViewDocument(url)} className="link-btn">
                          View Document {idx + 1}
                        </button>
                      </li>
                    ))}
                  </ul>
                </div>
              )}

              <div className="actions">
                {claim.status === 'PENDING' && (
                  <>
                    <button onClick={() => handleApprove(claim.id)} className="approve-btn">Approve</button>
                    <input
                      type="text"
                      placeholder="Rejection reason"
                      value={rejectionReason}
                      onChange={(e) => setRejectionReason(e.target.value)}
                      className="rejection-input"
                    />
                    <button onClick={() => handleReject(claim.id)} className="reject-btn">Reject</button>
                    <button onClick={() => handleRequestDocuments(claim.id)} className="neutral-btn">Request Docs</button>
                  </>
                )}
                {claim.status === 'APPROVED' && (
                  <button onClick={() => handleSettle(claim.id)} className="approve-btn">Mark as Settled</button>
                )}
              </div>
            </div>
          ))
        )}
      </div>
    </div>
  );
}

export default AdminClaimManagement;
