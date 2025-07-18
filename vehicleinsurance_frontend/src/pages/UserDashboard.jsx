import React, { useState, useEffect } from 'react';
import { Link, Outlet, useNavigate } from 'react-router-dom';
import './UserDashboard.css';
import { FaBars } from 'react-icons/fa';
import { toast } from 'react-toastify';

function UserDashboard() {
  const [sidebarOpen, setSidebarOpen] = useState(false);
  const [username, setUsername] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    const storedName = localStorage.getItem('name');
    setUsername(storedName || 'User');
  }, []);

  const toggleSidebar = () => {
    setSidebarOpen(!sidebarOpen);
  };

  const handleLogout = () => {
    localStorage.clear();
    toast.success('You have been logged out');
    navigate('/');
  };

  return (
    <div className="dashboard-wrapper">
      <div className={`menu-toggle-container ${sidebarOpen ? 'white' : 'black'}`} onClick={toggleSidebar}>
        <FaBars className="hamburger-icon" />
        {sidebarOpen && <span className="menu-label">Menu</span>}
      </div>

      {sidebarOpen && (
        <aside className="sidebar open">
          <nav>
            <Link to="profile">Profile</Link>
            <Link to="vehicles">Vehicles</Link>
            <Link to="proposals">Proposals</Link>
            <Link to="policies">Policies</Link>
            <Link to="claims">Claims</Link>
            <Link to="documents">Documents</Link>
            <Link to="quotes">Quotes</Link>
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

export default UserDashboard;
