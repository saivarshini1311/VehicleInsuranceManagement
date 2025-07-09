import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Profile.css';

export default function UserProfile() {
  const [user, setUser] = useState({});
  const [editing, setEditing] = useState(false);
  const [passwords, setPasswords] = useState({ current: '', new: '', confirm: '' });
  const [avatar, setAvatar] = useState(null);

  const userId = localStorage.getItem('userId');
  const token = localStorage.getItem('token');
  const BASE_URL = 'http://localhost:8080';

  useEffect(() => {
    axios.get(`${BASE_URL}/api/users/${userId}`, {
      headers: { Authorization: `Bearer ${token}` },
    }).then(res => setUser(res.data));
  }, [userId]);

  const handleChange = (e) => {
    setUser({ ...user, [e.target.name]: e.target.value });
  };

  const handleUpdate = () => {
    axios.put(`${BASE_URL}/api/users/${userId}`, user, {
      headers: { Authorization: `Bearer ${token}` },
    }).then(() => setEditing(false));
  };

  const handlePasswordChange = () => {
    if (passwords.new !== passwords.confirm) {
      alert("New passwords do not match");
      return;
    }
    axios.put(`${BASE_URL}/api/users/${userId}/password`, passwords, {
      headers: { Authorization: `Bearer ${token}` },
    }).then(() => alert("Password updated"));
  };

  const handleAvatarChange = (e) => {
  const file = e.target.files[0];
  setAvatar(file);

  const formData = new FormData();
  formData.append("avatar", file);

  axios.post(`${BASE_URL}/api/users/${userId}/avatar`, formData, {
    headers: {
      Authorization: `Bearer ${token}`,
      'Content-Type': 'multipart/form-data',
    },
  })
  .then(() => {
    alert("Profile photo updated");
    // Refetch updated user info to get new avatarUrl
    return axios.get(`${BASE_URL}/api/users/${userId}`, {
      headers: { Authorization: `Bearer ${token}` },
    });
  })
  .then(res => {
    setUser(res.data); // Update user state with new avatar URL
  })
  .catch(err => {
    console.error("Avatar upload failed", err);
  });
};


  return (
    <div className="profile-container">
      <h2>My Profile</h2>

      {/* Profile Photo Upload */}
      <div className="avatar-section">
        <img
          src={user.avatarUrl ? `http://localhost:8080${user.avatarUrl}` : '/default-avatar.png'}
          alt="Avatar"
          className="avatar-img"
        />
        <input type="file" accept="image/*" onChange={handleAvatarChange} />
      </div>

      {/* Profile Fields */}
      <div className="profile-grid">
        {['name', 'email', 'address', 'dob'].map(field => (
          <div key={field}>
            <label>{field}</label>
            <input
              name={field}
              type={field === 'dob' ? 'date' : 'text'}
              value={user[field] || ''}
              onChange={handleChange}
              disabled={!editing}
            />
          </div>
        ))}

        {['aadharNumber', 'panNumber', 'role'].map(field => (
          <div key={field}>
            <label>{field}</label>
            <input
              value={user[field] || ''}
              disabled
            />
          </div>
        ))}
      </div>

      {/* Edit/Save Button */}
      <div className="profile-actions">
        {editing ? (
          <button onClick={handleUpdate} className="save-btn">Save</button>
        ) : (
          <button onClick={() => setEditing(true)} className="edit-btn">Edit</button>
        )}
      </div>

      {/* Password Section */}
      <div className="password-section">
        <h3>Change Password</h3>
        <div className="password-grid">
          <input
            type="password"
            placeholder="Current Password"
            value={passwords.current}
            onChange={e => setPasswords({ ...passwords, current: e.target.value })}
          />
          <input
            type="password"
            placeholder="New Password"
            value={passwords.new}
            onChange={e => setPasswords({ ...passwords, new: e.target.value })}
          />
          <input
            type="password"
            placeholder="Confirm Password"
            value={passwords.confirm}
            onChange={e => setPasswords({ ...passwords, confirm: e.target.value })}
          />
        </div>
        <button onClick={handlePasswordChange} className="password-btn">Update Password</button>
      </div>
    </div>
  );
}
