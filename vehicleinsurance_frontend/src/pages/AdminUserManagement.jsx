import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './AdminDashboard.css';

function AdminUserManagement() {
  const [users, setUsers] = useState([]);
  const [editUserId, setEditUserId] = useState(null);
  const [editForm, setEditForm] = useState({});
  const token = localStorage.getItem('token');

  useEffect(() => {
    fetchUsers();
  }, []);

  const fetchUsers = async () => {
    try {
      const res = await axios.get('http://localhost:8080/api/users', {
        headers: { Authorization: `Bearer ${token}` }
      });
      setUsers(res.data);
    } catch (err) {
      console.error('Failed to load users', err);
    }
  };

  const filteredUsers = users.filter(user => user.role === 'USER');

  const handleBlock = async (userId) => {
    if (!window.confirm('Block this user?')) return;
    try {
      await axios.post(`http://localhost:8080/api/users/block/${userId}`, {}, {
        headers: { Authorization: `Bearer ${token}` }
      });
      alert('User blocked');
      fetchUsers();
    } catch (err) {
      alert('Error blocking user');
    }
  };

  const handleDelete = async (userId) => {
    if (!window.confirm('Are you sure you want to delete this user?')) return;
    try {
      await axios.delete(`http://localhost:8080/api/users/${userId}`, {
        headers: { Authorization: `Bearer ${token}` }
      });
      alert('User deleted');
      fetchUsers();
    } catch (err) {
      alert('Failed to delete user');
    }
  };

  const handleEdit = (user) => {
    setEditUserId(user.id);
    setEditForm({
      name: user.name,
      address: user.address,
    });
  };

  const handleUpdate = async (userId) => {
    try {
      await axios.put(`http://localhost:8080/api/users/update/${userId}`, editForm, {
        headers: { Authorization: `Bearer ${token}` }
      });
      alert('User updated');
      setEditUserId(null);
      fetchUsers();
    } catch (err) {
      alert('Failed to update user');
    }
  };

  return (
    <div className="admin-container">
      <h2>User Management</h2>

      <div className="user-list">
        {filteredUsers.length === 0 ? (
          <p>No users found.</p>
        ) : (
          filteredUsers.map(user => (
            <div key={user.id} className="user-card">
              <h4>{user.name} ({user.email})</h4>
              <p><strong>Role:</strong> {user.role}</p>
              <p><strong>Aadhaar:</strong> {user.aadharNumber}</p>
              <p><strong>PAN:</strong> {user.panNumber}</p>
              <p><strong>DOB:</strong> {user.dob}</p>
              <p><strong>Address:</strong> {user.address}</p>

              {editUserId === user.id ? (
                <div className="edit-form">
                  <input
                    type="text"
                    placeholder="Name"
                    value={editForm.name}
                    onChange={(e) => setEditForm({ ...editForm, name: e.target.value })}
                  />
                  <input
                    type="text"
                    placeholder="Address"
                    value={editForm.address}
                    onChange={(e) => setEditForm({ ...editForm, address: e.target.value })}
                  />
                  <button onClick={() => handleUpdate(user.id)} className="approve-btn">Save</button>
                  <button onClick={() => setEditUserId(null)} className="neutral-btn">Cancel</button>
                </div>
              ) : (
                <div className="actions">
                  <button onClick={() => handleEdit(user)} className="approve-btn">Edit</button>
                  <button onClick={() => handleBlock(user.id)} className="reject-btn">Block</button>
                  <button onClick={() => handleDelete(user.id)} className="reject-btn">Delete</button>
                </div>
              )}
            </div>
          ))
        )}
      </div>
    </div>
  );
}

export default AdminUserManagement;
