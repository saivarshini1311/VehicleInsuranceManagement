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
            <h3>
              {q.vehicle?.registrationNumber ? `${q.vehicle.registrationNumber} - ` : ""}
              {q.vehicle?.brand} {q.vehicle?.model}
            </h3>

            <p><strong>Premium:</strong> ₹{q.premiumAmount}</p>
            <p><strong>Duration:</strong> {q.coverageDuration || "1"} month</p>
            <p><strong>Conditions:</strong> {q.conditions || "Standard conditions apply."}</p>
            <p><strong>Status:</strong>
              <span className={`status ${q.status === 'PAID' ? 'paid' : 'unpaid'}`}>
                {q.status === 'PAID' ? 'Paid' : 'Unpaid'}
              </span>
            </p>

            {(q.status === 'UNPAID' || q.status === 'PENDING_PAYMENT') && (
              <button className="pay-now-button" onClick={() => handlePay(q.id)}>
                Pay Now
              </button>
            )}

            {q.status === 'PAID' && q.payment?.id && (
              <button className="receipt-button" onClick={() => handleDownloadReceipt(q.payment.id)}>
                Download Receipt
              </button>
            )}

            <button
              className="details-button"
              onClick={() => window.location.href = `/user-dashboard/quote/view/${q.id}`}
            >
              View Details
            </button>
          </div>
        ))
      )}
    </div>
  );
}
