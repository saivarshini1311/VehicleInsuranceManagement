import React, { useEffect, useState } from 'react';
import './UserDashboard.css';

function DashboardHome() {
  const [username, setUsername] = useState('User');

  useEffect(() => {
    const storedName = localStorage.getItem('name');
    if (storedName) {
      setUsername(storedName);
    }
  }, []);

  return (
    <div className="welcome-banner">
      <h1>Hi, {username}! Welcome to your DriveSure Dashboard</h1>
      <p>
        Secure your journey with our comprehensive vehicle insurance solutions.
        From daily rides to long hauls — we cover cars, bikes, trucks, and camper vans.
      </p>

      <div className="features">
        <div className="feature-card">
          <h3>Importance of Insurance</h3>
          <p>Protect your vehicle from accidents, theft, and damages with our trusted policies.</p>
        </div>
        <div className="feature-card">
          <h3>Vehicle-Based Policies</h3>
          <p>Tailored plans for Car, Bike, Truck, and Camper Van with add-ons and custom coverage.</p>
        </div>
        <div className="feature-card">
          <h3>Premium & Quotes</h3>
          <p>Get instant quotes based on your proposal. Premiums designed to fit your budget.</p>
        </div>
        <div className="feature-card">
          <h3>Claims Made Simple</h3>
          <p>Hassle-free claim process. File and track claims with live status updates.</p>
        </div>
        <div className="feature-card">
          <h3>Benefits</h3>
          <p>Enjoy peace of mind, legal compliance, and savings with every DriveSure policy.</p>
        </div>
      </div>
    </div>
  );
}

export default DashboardHome;
