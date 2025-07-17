import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';

function QuoteDetails() {
  const { id } = useParams();
  const navigate = useNavigate();
  const token = localStorage.getItem('token');

  const [quote, setQuote] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  const BASE_URL = 'http://localhost:8080';

  useEffect(() => {
    const fetchQuote = async () => {
      try {
        const response = await axios.get(`${BASE_URL}/api/quotes/${id}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        setQuote(response.data);
      } catch (err) {
        setError('Failed to load quote details.');
      } finally {
        setLoading(false);
      }
    };

    fetchQuote();
  }, [id, token]);

  const handlePay = () => {
    navigate(`/payment/quote/${id}`);
  };

  const handleDownloadReceipt = (paymentId) => {
    window.open(`${BASE_URL}/api/payments/${paymentId}/receipt`, '_blank');
  };

  if (loading) return <p>Loading quote details...</p>;
  if (error) return <p style={{ color: 'red' }}>{error}</p>;
  if (!quote) return <p>No quote found for ID {id}.</p>;

  return (
    <div className="quote-details">
      <h2>Quote Details (ID: {quote.id})</h2>

      <p><strong>User:</strong> {quote.user?.name} ({quote.user?.email})</p>
      <p><strong>Vehicle:</strong> {quote.vehicle?.brand} {quote.vehicle?.model} ({quote.vehicle?.registrationNumber})</p>

      <p><strong>Status:</strong> {quote.status}</p>
      <p><strong>Premium Amount:</strong> ₹{quote.premiumAmount}</p>
      <p><strong>Expiry Date:</strong> {quote.expiryDate}</p>
      <p><strong>Coverage Type:</strong> {quote.coverageType || 'Not specified'}</p>
      <p><strong>Remarks:</strong> {quote.remarks || 'None'}</p>

      {/* Show Pay Now if unpaid */}
      {['UNPAID', 'PENDING_PAYMENT'].includes(quote.status?.toUpperCase()) && (
        <button className="pay-now-button" onClick={handlePay}>
          Pay Now
        </button>
      )}

      {/* Show Download Receipt if paid */}
      {quote.status === 'PAID' && quote.payment?.id && (
        <button className="receipt-button" onClick={() => handleDownloadReceipt(quote.payment.id)}>
          Download Receipt
        </button>
      )}
    </div>
  );
}

export default QuoteDetails;
