import React, { useState } from 'react';
import { Link, Outlet } from 'react-router-dom';
import './UserDashboard.css';
import { FaBars } from 'react-icons/fa';

function UserDashboard() {
  const [sidebarOpen, setSidebarOpen] = useState(false);

  const toggleSidebar = () => {
    setSidebarOpen(!sidebarOpen);
  };

  return (
    <div className="dashboard-wrapper">
      {/* Sidebar Toggle Button */}
      <button className="menu-toggle" onClick={toggleSidebar}>
        <FaBars />
      </button>

      {/* Sidebar */}
      <aside className={`sidebar ${sidebarOpen ? 'open' : ''}`}>
        <h2>DriveSure</h2>
        <nav>
          <Link to="profile">Profile</Link>
          <Link to="vehicles">Vehicles</Link>
          <Link to="proposals">Proposals</Link>
          <Link to="policies">Policies</Link>
          <Link to="claims">Claims</Link>
          <Link to="documents">Documents</Link>
          <Link to="quotes">Quotes</Link>
          <Link to="notifications">Notifications</Link>
        </nav>
      </aside>

      {/* Main Content */}
      <main className="dashboard-content">
        <div className="welcome-banner">
          <h1>Welcome to DriveSure User Dashboard</h1>
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

        {/* Render nested route screens */}
        <div className="nested-content">
          <Outlet />
        </div>
      </main>
    </div>
  );
}

export default UserDashboard;
