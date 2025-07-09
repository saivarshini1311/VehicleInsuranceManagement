import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import API from '../api/api';
import './Auth.css'; 

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

    setError('');
    if (res.data.role === 'OFFICER') {
      navigate('/officer-dashboard');
    } else {
      navigate('/user-dashboard');
    }
  } catch (err) {
    setError('Invalid email or password');
  }
};

  return (
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

        <p>
          Don’t have an account?{' '}
          <span onClick={() => navigate('/register')}>Register</span>
        </p>
      </div>
    </div>
  );
}

export default Login;
