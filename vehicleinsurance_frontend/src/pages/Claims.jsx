import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Claims.css';

export default function Claims() {
  const userId = localStorage.getItem('userId');

  const [formData, setFormData] = useState({
    policyId: '',
    accidentDetails: '',
    damageProof: null,
  });

  const [claimHistory, setClaimHistory] = useState([]);
  const [userPolicies, setUserPolicies] = useState([]);
  const [submitting, setSubmitting] = useState(false);

  // Fetch active policies for the user
  useEffect(() => {
    const fetchPolicies = async () => {
      try {
        const res = await axios.get(`http://localhost:8080/api/policies/user/${userId}/active`);
        setUserPolicies(res.data);
      } catch (err) {
        console.error('Error fetching policies:', err);
      }
    };
    fetchPolicies();
  }, [userId]);

  // Fetch existing claims
  const fetchClaimHistory = async () => {
    try {
      const res = await axios.get(`http://localhost:8080/api/claims/user/${userId}`);
      setClaimHistory(res.data);
    } catch (err) {
      console.error('Error fetching claim history:', err);
    }
  };

  useEffect(() => {
    fetchClaimHistory();
  }, []);

  // Handle input change
  const handleChange = (e) => {
    const { name, value, files } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: files ? files[0] : value,
    }));
  };

  // Submit a new claim
  const handleSubmit = async (e) => {
    e.preventDefault();
    setSubmitting(true);

    const claim = {
      claimNumber: `CLM${Date.now()}`,
      description: formData.accidentDetails,
      claimDate: new Date().toISOString().split('T')[0],
      status: 'UNDER_REVIEW',
      amountClaimed: 0,
      userId: parseInt(userId),
      policyId: parseInt(formData.policyId),
    };

    try {
      const res = await axios.post('http://localhost:8080/api/claims/create', claim);
      const claimId = res.data.id;

      // Upload damage proof if available
      if (formData.damageProof) {
        const uploadData = new FormData();
        uploadData.append('file', formData.damageProof);
        await axios.post(`http://localhost:8080/api/claims/${claimId}/upload`, uploadData);
      }

      alert('Claim submitted successfully!');
      setFormData({
        policyId: '',
        accidentDetails: '',
        damageProof: null,
      });
      fetchClaimHistory();
    } catch (error) {
      console.error('Error submitting claim:', error);
      alert('Claim submission failed.');
    } finally {
      setSubmitting(false);
    }
  };

  const handleCancel = async (id) => {
    if (window.confirm('Cancel this claim?')) {
      try {
        await axios.delete(`http://localhost:8080/api/claims/${id}`);
        fetchClaimHistory();
      } catch (err) {
        console.error('Error cancelling claim:', err);
        alert('Could not cancel claim.');
      }
    }
  };

  const handleDownloadAck = async (id) => {
    try {
      const res = await axios.get(`http://localhost:8080/api/claims/${id}/acknowledgement`, {
        responseType: 'blob',
      });
      const blob = new Blob([res.data], { type: 'text/plain' });
      const link = document.createElement('a');
      link.href = window.URL.createObjectURL(blob);
      link.download = `Claim_Acknowledgement_${id}.txt`;
      link.click();
    } catch (err) {
      console.error('Error downloading acknowledgement:', err);
    }
  };

  return (
    <div className="claims-container">
      <h2>Submit a Claim</h2>
      <form className="claim-form" onSubmit={handleSubmit}>
        <label>
          Select Policy:
          <select name="policyId" value={formData.policyId} onChange={handleChange} required>
            <option value="">-- Select Policy --</option>
            {userPolicies.map((policy) => (
              <option key={policy.id} value={policy.id}>
                {policy.policyNumber} — {policy.coverageDetails}
              </option>
            ))}
          </select>
        </label>

        <label>
          Accident Details:
          <textarea
            name="accidentDetails"
            value={formData.accidentDetails}
            onChange={handleChange}
            placeholder="Describe the accident..."
            required
          />
        </label>

        <label>
          Upload Damage Proof:
          <input
            type="file"
            name="damageProof"
            accept=".jpg,.jpeg,.png,.pdf"
            onChange={handleChange}
          />
        </label>

        <button type="submit" disabled={submitting}>
          {submitting ? 'Submitting...' : 'Submit Claim'}
        </button>
      </form>

      <h2>Claim History</h2>
      {claimHistory.length === 0 ? (
        <p>No claims submitted yet.</p>
      ) : (
        <table className="claim-history">
          <thead>
            <tr>
              <th>Ref. No</th>
              <th>Date</th>
              <th>Status</th>
              <th>Amount</th>
              <th>Description</th>
              <th>Remarks</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {claimHistory.map((claim) => (
              <tr key={claim.id}>
                <td>{claim.claimNumber}</td>
                <td>{claim.claimDate}</td>
                <td className={`status ${claim.status.toLowerCase().replace(' ', '-')}`}>
                  {claim.status}
                </td>
                <td>₹{claim.amountClaimed}</td>
                <td>{claim.description}</td>
                <td>{claim.remarks || '—'}</td>
                <td>
                  {claim.status === 'UNDER_REVIEW' && (
                    <button onClick={() => handleCancel(claim.id)}>Cancel</button>
                  )}
                  <button onClick={() => handleDownloadAck(claim.id)}>Download Ack</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}
