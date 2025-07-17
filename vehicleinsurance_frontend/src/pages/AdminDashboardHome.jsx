import React, { useEffect, useState } from 'react';
import './UserDashboard.css';

function AdminDashboardHome() {
  const [username, setUsername] = useState('Officer');

  useEffect(() => {
    const storedName = localStorage.getItem('name');
    if (storedName) {
      setUsername(storedName);
    }
  }, []);

  return (
    <div className="welcome-banner">
      <h1>Welcome back, {username}! DriveSure Admin Dashboard</h1>
      <p>
        Manage proposals, policies, claims, and users across the DriveSure insurance platform.
        Stay on top of critical actions with real-time analytics and review tools.
      </p>

      <div className="features">
        <div className="feature-card">
          <h3>Proposal Management</h3>
          <p>Review proposals, approve or reject with reasons, and generate quotes instantly.</p>
        </div>

        <div className="feature-card">
          <h3>Quote Management</h3>
          <p>Monitor all quotes — update premiums, cancel unaccepted ones, or mark as expired.</p>
        </div>

        <div className="feature-card">
          <h3>Policy Management</h3>
          <p>View active and expired policies, renew or cancel them, and download documents.</p>
        </div>

        <div className="feature-card">
          <h3>Claim Management</h3>
          <p>Track all claim requests, verify documents, and manage approvals or settlements.</p>
        </div>

        <div className="feature-card">
          <h3>Vehicle & User Management</h3>
          <p>Access all registered vehicles and users. Update, block, or flag suspicious entries.</p>
        </div>

        <div className="feature-card">
          <h3>Document Center</h3>
          <p>Review and verify uploaded documents — RCs, insurance files, accident proofs, etc.</p>
        </div>

        <div className="feature-card">
          <h3>Notifications</h3>
          <p>Send reminders for expiring policies, pending quotes, and unsettled claims.</p>
        </div>

        <div className="feature-card">
          <h3>Dashboard Overview</h3>
          <p>Access analytics on revenue, total users, policy count, and claim settlement ratios.</p>
        </div>
      </div>
    </div>
  );
}

export default AdminDashboardHome;
