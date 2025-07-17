import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './AdminDashboard.css';

function AdminQuoteManagement() {
  const [quotes, setQuotes] = useState([]);
  const [filter, setFilter] = useState('PENDING_PAYMENT');
  const [updateAmount, setUpdateAmount] = useState({});
  const token = localStorage.getItem('token');

  useEffect(() => {
    fetchQuotes();
  }, [filter]);

  const fetchQuotes = async () => {
    try {
      const res = await axios.get(`http://localhost:8080/api/quotes/status/${filter}`, {
        headers: { Authorization: `Bearer ${token}` }
      });
      setQuotes(res.data);
    } catch (err) {
      console.error('Failed to fetch quotes', err);
    }
  };

  const handleRegenerate = async (quoteId) => {
    const newPremium = updateAmount[quoteId];
    if (!newPremium || isNaN(newPremium)) {
      alert('Enter a valid premium amount.');
      return;
    }

    try {
      await axios.put(`http://localhost:8080/api/quotes/update/${quoteId}`, {
        premiumAmount: newPremium
      }, {
        headers: { Authorization: `Bearer ${token}` }
      });
      alert('Quote updated successfully');
      fetchQuotes();
    } catch (err) {
      alert('Failed to update quote');
    }
  };

  const handleCancel = async (quoteId) => {
    if (!window.confirm('Are you sure you want to cancel this quote?')) return;

    try {
      await axios.delete(`http://localhost:8080/api/quotes/cancel/${quoteId}`, {
        headers: { Authorization: `Bearer ${token}` }
      });
      alert('Quote canceled');
      fetchQuotes();
    } catch (err) {
      alert('Failed to cancel quote');
    }
  };

  return (
    <div className="admin-container">
      <h2>Quote Management</h2>

      <label>Filter by Status:</label>
      <select value={filter} onChange={(e) => setFilter(e.target.value)}>
        <option value="PENDING_PAYMENT">Pending Payment</option>
        <option value="ACCEPTED">Accepted</option>
        <option value="EXPIRED">Expired</option>
      </select>

      <div className="quote-list">
        {quotes.length === 0 ? (
          <p>No quotes found.</p>
        ) : (
          quotes.map((quote) => (
            <div key={quote.id} className="quote-card">
              <h4>Quote ID: {quote.id}</h4>
              <p><strong>Premium:</strong> ₹{quote.premiumAmount}</p>
              <p><strong>Valid Till:</strong> {quote.expiryDate}</p>
              <p><strong>Status:</strong> {quote.status}</p>

              {quote.status === 'PENDING_PAYMENT' && (
                <div className="actions">
                  <input
                    type="number"
                    placeholder="New premium"
                    value={updateAmount[quote.id] || ''}
                    onChange={(e) => setUpdateAmount({ ...updateAmount, [quote.id]: e.target.value })}
                    className="premium-input"
                  />
                  <button onClick={() => handleRegenerate(quote.id)} className="approve-btn">Update</button>
                  <button onClick={() => handleCancel(quote.id)} className="reject-btn">Cancel</button>
                </div>
              )}
            </div>

          ))
        )}
      </div>
    </div>
  );
}

export default AdminQuoteManagement;
