import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Quotes.css';

export default function Quotes() {
  const [quotes, setQuotes] = useState([]);
  const userId = localStorage.getItem('userId');
  const token = localStorage.getItem('token');
  const BASE_URL = 'http://localhost:8080';

  useEffect(() => {
    fetchQuotes();
  }, []);

  const fetchQuotes = () => {
    axios.get(`${BASE_URL}/api/quotes/user/${userId}`, {
      headers: { Authorization: `Bearer ${token}` },
    })
    .then(res => setQuotes(res.data))
    .catch(err => console.error("Error fetching quotes:", err));
  };

  const handlePay = (quoteId) => {
    // Redirect to payment page
    window.location.href = `/payment/quote/${quoteId}`;
  };

  const handleDownloadReceipt = (paymentId) => {
    window.open(`${BASE_URL}/api/payments/${paymentId}/receipt`, '_blank');
  };

  return (
    <div className="quote-container">
      <h2>Quote Management</h2>

      {quotes.length === 0 ? (
        <p>No quotes found.</p>
      ) : (
        quotes.map((q) => (
          <div key={q.id} className="quote-card">
            <h3>{q.vehicle?.registrationNumber} - {q.vehicle?.brand} {q.vehicle?.model}</h3>
            <p><strong>Premium:</strong> ₹{q.premiumAmount}</p>
            <p><strong>Duration:</strong> {q.coverageDuration} months</p>
            <p><strong>Conditions:</strong> {q.conditions}</p>
            <p><strong>Status:</strong> <span className={`status ${q.paid ? 'paid' : 'unpaid'}`}>{q.paid ? 'Paid' : 'Unpaid'}</span></p>

            {!q.paid ? (
              <button onClick={() => handlePay(q.id)}>💳 Accept & Pay</button>
            ) : (
              <button onClick={() => handleDownloadReceipt(q.paymentId)}>📥 Download Receipt</button>
            )}
          </div>
        ))
      )}
    </div>
  );
}
