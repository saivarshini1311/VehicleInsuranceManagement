// src/components/Navbar.jsx
import React from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import './Navbar.css';
import { toast } from 'react-toastify';

function Navbar() {
  const location = useLocation();
  const navigate = useNavigate();

  const isAuthPage = location.pathname === '/login' || location.pathname === '/register';

  const handleLogout = () => {
    localStorage.clear();
    toast.success('You have been logged out');
    navigate('/');
  };

  return (
    <nav className="navbar">
      <div className="navbar-left">
        <img src="/assets/DriveSure_Logo.png" alt="DriveSure Logo" className="navbar-logo" />
      </div>

      {isAuthPage && (
        <div className="navbar-right">
          {location.pathname === '/login' && (
            <Link to="/register" className="auth-button yellow-button">Register</Link>
          )}
          {location.pathname === '/register' && (
            <Link to="/login" className="auth-button yellow-button">Login</Link>
          )}
          <button className="auth-button red-button" onClick={handleLogout}>Logout</button>
        </div>
      )}
    </nav>
  );
}

export default Navbar;
