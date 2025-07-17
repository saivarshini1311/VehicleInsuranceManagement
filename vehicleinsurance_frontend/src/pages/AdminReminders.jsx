import React, { useEffect, useState } from 'react';
import './AdminDashboard.css';

const AdminReminders = () => {
  const [expiringPolicies, setExpiringPolicies] = useState([]);
  const [unpaidQuotes, setUnpaidQuotes] = useState([]);
  const [unsettledClaims, setUnsettledClaims] = useState([]);

  useEffect(() => {
    fetchReminders();
  }, []);

  const fetchReminders = () => {
    setExpiringPolicies([
      {
        id: 1,
        policyNumber: 'POL20250001',
        expiryDate: '2026-07-17',
        user: { name: 'Saivarshini', email: 'sai@gmail.com' }
      },
      {
        id: 2,
        policyNumber: 'POL20250002',
        expiryDate: '2026-07-17',
        user: { name: 'Jeeva A', email: 'jeeva111@gmail.com' }
      }
    ]);

    setUnpaidQuotes([
      {
        id: 4,
        premiumAmount: 1500,
        user: { name: 'Jeeva A', email: 'jeeva111@gmail.com' }
      }
    ]);

    setUnsettledClaims([
      {
        id: 1,
        claimAmount: 3500,
        status: 'UNDER_REVIEW',
        user: { name: 'Saivarshini', email: 'sai@gmail.com' }
      }
    ]);
  };

  const sendReminder = (type, id) => {
    alert(`Reminder sent for ${type} ID ${id}`);
  };

  const markAsResolved = (type, id) => {
    alert(`${type} ID ${id} marked as resolved`);
    if (type === 'policy') {
      setExpiringPolicies(prev => prev.filter(p => p.id !== id));
    } else if (type === 'quote') {
      setUnpaidQuotes(prev => prev.filter(q => q.id !== id));
    } else if (type === 'claim') {
      setUnsettledClaims(prev => prev.filter(c => c.id !== id));
    }
  };

  return (
    <div className="admin-reminders-container">
      <h2>Admin Notifications</h2>

      {/* Expiring Policies */}
      <div className="reminder-section">
        <h3>Expiring Policies</h3>
        {expiringPolicies.length === 0 ? (
          <p>No expiring policies.</p>
        ) : (
          <ul>
            {expiringPolicies.map((policy) => (
              <li key={policy.id}>
                <strong>{policy.policyNumber}</strong> — Expiry: {policy.expiryDate} <br />
                <span>User: {policy.user.name} ({policy.user.email})</span>
                <div className="actions">
                  <button onClick={() => sendReminder('policy', policy.id)}>Send Reminder</button>
                  <button onClick={() => markAsResolved('policy', policy.id)}>Mark Resolved</button>
                </div>
              </li>
            ))}
          </ul>
        )}
      </div>
      <div className="reminder-section">
        <h3>Unpaid Quotes</h3>
        {unpaidQuotes.length === 0 ? (
          <p>No unpaid quotes.</p>
        ) : (
          <ul>
            {unpaidQuotes.map((quote) => (
              <li key={quote.id}>
                <strong>Quote #{quote.id}</strong> — Premium: ₹{quote.premiumAmount} <br />
                <span>User: {quote.user.name} ({quote.user.email})</span>
                <div className="actions">
                  <button onClick={() => sendReminder('quote', quote.id)}>Send Reminder</button>
                  <button onClick={() => markAsResolved('quote', quote.id)}>Mark Resolved</button>
                </div>
              </li>
            ))}
          </ul>
        )}
      </div>
      <div className="reminder-section">
        <h3>Unsettled Claims</h3>
        {unsettledClaims.length === 0 ? (
          <p>No unsettled claims.</p>
        ) : (
          <ul>
            {unsettledClaims.map((claim) => (
              <li key={claim.id}>
                <strong>Claim #{claim.id}</strong> — ₹{claim.claimAmount} | Status: {claim.status} <br />
                <span>User: {claim.user.name} ({claim.user.email})</span>
                <div className="actions">
                  <button onClick={() => sendReminder('claim', claim.id)}>Send Reminder</button>
                  <button onClick={() => markAsResolved('claim', claim.id)}>Mark Resolved</button>
                </div>
              </li>
            ))}
          </ul>
        )}
      </div>
    </div>
  );
};

export default AdminReminders;
