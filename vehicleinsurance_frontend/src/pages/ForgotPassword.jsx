import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import API from '../api/api';
import './Auth.css';
import Navbar from '../components/Navbar';

function ForgotPassword() {
  const [email, setEmail] = useState('');
  const [message, setMessage] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const res = await API.post('/auth/forgot-password', { email });

      if (res.status === 200) {
        setMessage('Password reset instructions have been sent to your email.');
        setError('');
      }
    } catch (err) {
      setError('Failed to send reset instructions. Please check your email.');
      setMessage('');
    }
  };

  return (
    <>
      <Navbar />
      <div className="auth-container">
        <div className="auth-card">
          <h2>Forgot Password</h2>
          <p>Enter your registered email to receive reset instructions.</p>

          {message && <div className="success-msg">{message}</div>}
          {error && <div className="error-msg">{error}</div>}

          <form onSubmit={handleSubmit}>
            <input
              type="email"
              placeholder="Enter your email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />

            <button type="submit">Send Reset Link</button>
          </form>

          <p>
            Remembered your password?{' '}
            <span className="link-text" onClick={() => navigate('/login')}>
              Go to Login
            </span>
          </p>
        </div>
      </div>
    </>
  );
}

export default ForgotPassword;
