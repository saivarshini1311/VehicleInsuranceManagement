import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import API from '../api/api';
import './Auth.css';
import Navbar from '../components/Navbar';

function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const res = await API.post('/auth/login', { email, password });

      localStorage.setItem('token', res.data.token);
      localStorage.setItem('role', res.data.role);
      localStorage.setItem('userId', res.data.id);
      localStorage.setItem('email', email);
      localStorage.setItem('name', res.data.name);

      setError('');

      const isOfficerOrAdminEmail = email.endsWith('@officer.com') || email.endsWith('@admin.com');

      if (res.data.role === 'OFFICER') {
        if (isOfficerOrAdminEmail) {
          navigate('/officer-dashboard');
        } else {
          setError('Unauthorized login. Officer must use an @officer.com or @admin.com email.');
        }
      } else {
        if (!isOfficerOrAdminEmail) {
          navigate('/user-dashboard');
        } else {
          setError('Unauthorized login. Users should not use @officer.com or @admin.com emails.');
        }
      }
    } catch (err) {
      setError('Invalid email or password');
    }
  };

  return (
    <>
      <Navbar />
      <div className="auth-container">
        <div className="auth-card">
          <h2>Login to DriveSure</h2>
          {error && <div className="error-msg">{error}</div>}

          <form onSubmit={handleLogin}>
            <input
              type="email"
              placeholder="Email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />

            <input
              type="password"
              placeholder="Password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />

            <button type="submit">Login</button>
          </form>

          <div className="auth-links">
            <p>
              <span onClick={() => navigate('/forgot-password')} className="link-text">
                Forgot Password?
              </span>
            </p>
            <p>
              Don’t have an account?{' '}
              <span onClick={() => navigate('/register')} className="link-text">
                Register
              </span>
            </p>
          </div>
        </div>
      </div>
    </>
  );
}

export default Login;
