import React, { useState } from 'react';
import './Claims.css';

export default function Claims() {
  const [formData, setFormData] = useState({
    vehicleId: '',
    policyId: '',
    accidentDetails: '',
    damageProof: null,
  });

  const [claimHistory, setClaimHistory] = useState([
    {
      refNumber: 'CLM12345',
      date: '2025-07-01',
      status: 'Under Review',
      amount: '₹15,000',
      remarks: 'Pending verification',
    },
    {
      refNumber: 'CLM12211',
      date: '2025-06-20',
      status: 'Approved',
      amount: '₹12,000',
      remarks: 'Approved by officer',
    },
  ]);

  const handleChange = (e) => {
    const { name, value, files } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: files ? files[0] : value,
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // TODO: Handle API submission
    console.log("Submitting claim:", formData);
    alert('Claim submitted successfully!');
  };

  return (
    <div className="claims-container">
      <h2>Submit a Claim</h2>
      <form className="claim-form" onSubmit={handleSubmit}>
        <label>
          Select Vehicle:
          <select name="vehicleId" value={formData.vehicleId} onChange={handleChange} required>
            <option value="">-- Select --</option>
            <option value="1">TN09 AB 1234 - Car</option>
            <option value="2">TN10 CD 5678 - Bike</option>
          </select>
        </label>

        <label>
          Select Policy:
          <select name="policyId" value={formData.policyId} onChange={handleChange} required>
            <option value="">-- Select --</option>
            <option value="101">Policy #101</option>
            <option value="102">Policy #102</option>
          </select>
        </label>

        <label>
          Accident Details:
          <textarea
            name="accidentDetails"
            value={formData.accidentDetails}
            onChange={handleChange}
            placeholder="Describe the incident..."
            required
          />
        </label>

        <label>
          Upload Damage Proof:
          <input type="file" name="damageProof" onChange={handleChange} accept=".jpg,.png,.pdf" required />
        </label>

        <button type="submit">Submit Claim</button>
      </form>

      <h2>Claim History</h2>
      <table className="claim-history">
        <thead>
          <tr>
            <th>Ref. Number</th>
            <th>Date</th>
            <th>Status</th>
            <th>Approved Amount</th>
            <th>Remarks</th>
          </tr>
        </thead>
        <tbody>
          {claimHistory.map((claim, index) => (
            <tr key={index}>
              <td>{claim.refNumber}</td>
              <td>{claim.date}</td>
              <td className={`status ${claim.status.toLowerCase().replace(' ', '-')}`}>{claim.status}</td>
              <td>{claim.amount}</td>
              <td>{claim.remarks}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
