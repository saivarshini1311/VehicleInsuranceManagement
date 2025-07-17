import React, { useState, useEffect } from 'react';
import { Link, Outlet, useNavigate } from 'react-router-dom';
import './UserDashboard.css'; 
import { FaBars } from 'react-icons/fa';
import { toast } from 'react-toastify';

function AdminDashboard() {
  const [sidebarOpen, setSidebarOpen] = useState(false);
  const [username, setUsername] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    const storedName = localStorage.getItem('name');
    setUsername(storedName || 'Officer');
  }, []);

  const toggleSidebar = () => {
    setSidebarOpen(!sidebarOpen);
  };

  const handleLogout = () => {
    localStorage.clear();
    toast.success('Logged out successfully');
    navigate('/login');
  };

  return (
    <div className="dashboard-wrapper">
      <div
        className={`menu-toggle-container ${sidebarOpen ? 'white' : 'black'}`}
        onClick={toggleSidebar}
      >
        <FaBars className="hamburger-icon" />
        {sidebarOpen && <span className="menu-label">Menu</span>}
      </div>

      {sidebarOpen && (
        <aside className="sidebar open">
          <nav>
            <Link to="overview">Dashboard</Link>
            <Link to="proposals">Proposals</Link>
            <Link to="quotes">Quotes</Link>
            <Link to="policies">Policies</Link>
            <Link to="claims">Claims</Link>
            <Link to="vehicles">Vehicles</Link>
            <Link to="users">Users</Link>
            <Link to="documents">Documents</Link>
            <Link to="notifications">Notifications</Link>
            <button onClick={handleLogout} className="logout-button">Logout</button>
          </nav>
        </aside>
      )}

      <main className={`dashboard-content ${sidebarOpen ? 'with-sidebar' : ''}`}>
        <Outlet />
      </main>
    </div>
  );
}

export default AdminDashboard;
