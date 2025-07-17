import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Vehicle.css';

export default function Vehicle() {
  const [vehicles, setVehicles] = useState([]);
  const [newVehicle, setNewVehicle] = useState({
    brand: '',
    model: '',
    registrationNumber: '',
    type: 'Car',
  });
  const [editingId, setEditingId] = useState(null);

  const userId = localStorage.getItem('userId');
  const token = localStorage.getItem('token');

  useEffect(() => {
    fetchVehicles();
  }, []);

  const fetchVehicles = () => {
    axios
      .get(`http://localhost:8080/api/vehicles/owner/${userId}`, {
        headers: { Authorization: `Bearer ${token}` },
      })
      .then((res) => setVehicles(res.data))
      .catch((err) => console.error('Error fetching vehicles:', err));
  };

  const handleInputChange = (e) => {
    setNewVehicle({ ...newVehicle, [e.target.name]: e.target.value });
  };

  const handleAddVehicle = () => {
    axios
      .post(
        'http://localhost:8080/api/vehicles/register',
        { ...newVehicle, ownerId: userId },
        { headers: { Authorization: `Bearer ${token}` } }
      )
      .then(() => {
        fetchVehicles();
        setNewVehicle({ brand: '', model: '', registrationNumber: '', type: 'Car' });
      })
      .catch((err) => console.error('Error adding vehicle:', err));
  };

  const handleDeleteVehicle = (id) => {
    axios
      .delete(`http://localhost:8080/api/vehicles/${id}`, {
        headers: { Authorization: `Bearer ${token}` },
      })
      .then(fetchVehicles)
      .catch((err) => console.error('Error deleting vehicle:', err));
  };

  const handleEditVehicle = (vehicle) => {
    setNewVehicle(vehicle);
    setEditingId(vehicle.id);
  };

  const handleUpdateVehicle = () => {
    axios
      .put(`http://localhost:8080/api/vehicles/${editingId}`, newVehicle, {
        headers: { Authorization: `Bearer ${token}` },
      })
      .then(() => {
        fetchVehicles();
        setEditingId(null);
        setNewVehicle({ brand: '', model: '', registrationNumber: '', type: 'Car' });
      })
      .catch((err) => console.error('Error updating vehicle:', err));
  };

  const getStatus = (vehicle) => {
    if (vehicle.policy) {
      const now = new Date();
      const expiry = new Date(vehicle.policy.expiryDate);
      return expiry >= now ? 'Active Policy' : 'Expired';
    } else if (vehicle.proposal) {
      return 'Proposal Submitted';
    } else {
      return 'Not Insured';
    }
  };

  return (
    <div className="vehicle-container">
      <h2>Vehicle Management</h2>

      <div className="vehicle-form">
        <h3>{editingId ? 'Edit Vehicle' : 'Add New Vehicle'}</h3>
        <input
          name="brand"
          placeholder="Brand"
          value={newVehicle.brand}
          onChange={handleInputChange}
        />
        <input
          name="model"
          placeholder="Model"
          value={newVehicle.model}
          onChange={handleInputChange}
        />
        <input
          name="registrationNumber"
          placeholder="Registration Number"
          value={newVehicle.registrationNumber}
          onChange={handleInputChange}
        />
        <select name="type" value={newVehicle.type} onChange={handleInputChange}>
          <option value="Car">Car</option>
          <option value="Bike">Bike</option>
          <option value="Truck">Truck</option>
          <option value="Camper">Camper</option>
        </select>
        {editingId ? (
          <button onClick={handleUpdateVehicle}>Update Vehicle</button>
        ) : (
          <button onClick={handleAddVehicle}>Add Vehicle</button>
        )}
      </div>

      <div className="vehicle-list">
        {vehicles.length === 0 ? (
          <p>No vehicles found. Add a new vehicle.</p>
        ) : (
          vehicles.map((v) => (
            <div key={v.id} className="vehicle-card">
              <h4>{v.type || 'Unknown Type'} </h4>
              <p><strong>Brand:</strong> {v.brand}</p>
              <p><strong>Model:</strong> {v.model}</p>
              <p><strong>Reg No:</strong> {v.registrationNumber}</p>
              <p><strong>Status:</strong> {getStatus(v)}</p>
              <div className="actions">
                <button onClick={() => window.location.href = `/policy/view/${v.id}`}>View Policy</button>
                <button onClick={() => window.location.href = `/user-dashboard/proposals`}>Submit Proposal</button>
                <button onClick={() => window.location.href = `/user-dashboard/claims`}>Submit Claim</button>
                <button onClick={() => handleEditVehicle(v)}>Edit</button>
                <button onClick={() => handleDeleteVehicle(v.id)}>Delete</button>
              </div>

            </div>
          ))
        )}
      </div>
    </div>
  );
}
