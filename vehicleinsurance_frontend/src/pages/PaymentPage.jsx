import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import './PaymentPage.css';

export default function PaymentPage() {
  const { id } = useParams(); // quoteId
  const [quote, setQuote] = useState(null);
  const token = localStorage.getItem('token');
  const BASE_URL = 'http://localhost:8080';
  const navigate = useNavigate();

  useEffect(() => {
    axios.get(`${BASE_URL}/api/quotes/${id}`, {
      headers: { Authorization: `Bearer ${token}` },
    })
      .then(res => setQuote(res.data))
      .catch(err => console.error("Error fetching quote:", err));
  }, [id]);

  const handleConfirmPayment = () => {
    axios.post(`${BASE_URL}/api/payments/pay/${id}`, {}, {
      headers: { Authorization: `Bearer ${token}` },
    })
      .then(res => {
        alert("Payment Successful");
        navigate('/user-dashboard/quotes');
      })
      .catch(err => {
        console.error("Payment error:", err);
        alert("Payment Failed");
      });
  };

  if (!quote) return <p>Loading quote details...</p>;

  return (
    <div className="payment-page">
      <h2>Confirm Payment</h2>
      <div className="payment-quote-card">
        <p><strong>Quote ID:</strong> {quote.id}</p>
        <p><strong>Vehicle:</strong> {quote.vehicle?.registrationNumber} - {quote.vehicle?.brand} {quote.vehicle?.model}</p>
        <p><strong>Premium:</strong> ₹{quote.premiumAmount}</p>
        <p><strong>Coverage:</strong> {quote.coverageDuration || 1} month</p>
        <button onClick={handleConfirmPayment} className="confirm-button">
          Confirm Payment
        </button>
      </div>
    </div>
  );
}
