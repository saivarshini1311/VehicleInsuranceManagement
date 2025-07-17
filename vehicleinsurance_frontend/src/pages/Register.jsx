import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import API from '../api/api';
import './Auth.css';
import Navbar from '../components/Navbar'; 

function Register() {
  const [form, setForm] = useState({
    name: '',
    email: '',
    password: '',
    address: '',
    dob: '',
    aadharNumber: '',
    panNumber: '',
  });

  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleRegister = async (e) => {
    e.preventDefault();

    const payload = { ...form, role: 'USER' }; // Force USER role

    try {
      await API.post('/auth/register', payload);
      navigate('/login');
    } catch (err) {
      setError('Registration failed. Try again.');
    }
  };

return (
  <>
    <Navbar />
    <div className="auth-container">
      <div className="auth-card">
        <h2>Register as User</h2>
        {error && <div className="error-msg">{error}</div>}

        <form onSubmit={handleRegister}>
          <input
            name="name"
            value={form.name}
            onChange={handleChange}
            placeholder="Full Name"
            required
          />
          <input
            type="email"
            name="email"
            value={form.email}
            onChange={handleChange}
            placeholder="Email"
            required
          />
          <input
            type="password"
            name="password"
            value={form.password}
            onChange={handleChange}
            placeholder="Password"
            required
          />
          <input
            name="address"
            value={form.address}
            onChange={handleChange}
            placeholder="Address"
            required
          />
          <input
            name="dob"
            value={form.dob}
            onChange={handleChange}
            placeholder="Date of Birth (YYYY-MM-DD)"
          />
          <input
            name="aadharNumber"
            value={form.aadharNumber}
            onChange={handleChange}
            placeholder="Aadhar Number"
          />
          <input
            name="panNumber"
            value={form.panNumber}
            onChange={handleChange}
            placeholder="PAN Number"
          />

          <button type="submit">Register</button>
        </form>

        <p>
          Already registered?{' '}
          <span onClick={() => navigate('/login')}>Login</span>
        </p>
      </div>
    </div>
  </>
);
}
export default Register;
