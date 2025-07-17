import React from 'react';
import { useNavigate } from 'react-router-dom';
import { motion } from 'framer-motion';
import './Home.css';

function Home() {
  const navigate = useNavigate();

  return (
    <div className="home-container">
      <motion.div
        className="hero-section"
        initial={{ opacity: 0, y: -50 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 1 }}
      >
        <h1>Welcome to <span className="brand">DriveSure</span></h1>
        <p>Your trusted partner for Vehicle Insurance</p>
        <div className="hero-buttons">
          <button onClick={() => navigate('/login')} className="btn btn-primary">Login</button>
          <button onClick={() => navigate('/register')} className="btn btn-outline-light ms-3">Register</button>
        </div>
      </motion.div>

      <motion.div
        className="hero-image"
        initial={{ opacity: 0, x: 100 }}
        animate={{ opacity: 1, x: 0 }}
        transition={{ duration: 1.2 }}
      >
        <img src="/assets/Motor_insurance.jpg" alt="Vehicle Insurance" />
      </motion.div>
    </div>
  );
}

export default Home;
