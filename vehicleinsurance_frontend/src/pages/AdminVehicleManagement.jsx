import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './AdminDashboard.css';

function AdminVehicleManagement() {
  const [vehicles, setVehicles] = useState([]);
  const [typeFilter, setTypeFilter] = useState('');
  const [searchName, setSearchName] = useState('');
  const [searchReg, setSearchReg] = useState('');
  const [editingVehicleId, setEditingVehicleId] = useState(null);
  const [editForm, setEditForm] = useState({});
  const [addForm, setAddForm] = useState({});
  const token = localStorage.getItem('token');

  useEffect(() => {
    fetchVehicles();
  }, []);

  const fetchVehicles = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/vehicles', {
        headers: { Authorization: `Bearer ${token}` },
      });
      setVehicles(response.data);
    } catch (error) {
      console.error('Failed to fetch vehicles', error);
    }
  };

  const handleDelete = async (vehicleId) => {
    if (!window.confirm('Are you sure you want to remove this vehicle?')) return;

    try {
      await axios.delete(`http://localhost:8080/api/vehicles/${vehicleId}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      alert('Vehicle removed.');
      fetchVehicles();
    } catch (error) {
      alert('Failed to remove vehicle');
    }
  };

  const handleEdit = (vehicle) => {
    setEditingVehicleId(vehicle.id);
    setEditForm({ ...vehicle });
  };

  const handleUpdate = async (vehicleId) => {
    try {
      await axios.put(`http://localhost:8080/api/vehicles/${vehicleId}`, editForm, {
        headers: { Authorization: `Bearer ${token}` },
      });
      alert('Vehicle updated');
      setEditingVehicleId(null);
      fetchVehicles();
    } catch (error) {
      alert('Update failed');
    }
  };

  const handleAdd = async () => {
    try {
      await axios.post('http://localhost:8080/api/vehicles/register', addForm, {
        headers: { Authorization: `Bearer ${token}` },
      });
      alert('Vehicle added');
      setAddForm({});
      fetchVehicles();
    } catch (error) {
      alert('Failed to add vehicle');
    }
  };

  const filteredVehicles = vehicles.filter(vehicle =>
    (typeFilter ? vehicle.type.toLowerCase() === typeFilter.toLowerCase() : true) &&
    (searchName ? vehicle.ownerName?.toLowerCase().includes(searchName.toLowerCase()) : true) &&
    (searchReg ? vehicle.registrationNumber?.toLowerCase().includes(searchReg.toLowerCase()) : true)
  );

  return (
    <div className="admin-container">
      <h2>Vehicle Management</h2>

      <div className="filters">
        <label>Filter by Type:</label>
        <select value={typeFilter} onChange={(e) => setTypeFilter(e.target.value)}>
          <option value="">All</option>
          <option value="Car">Car</option>
          <option value="Bike">Bike</option>
          <option value="Truck">Truck</option>
          <option value="Camper">Camper</option>
        </select>

        <input
          type="text"
          placeholder="Search by owner name"
          value={searchName}
          onChange={(e) => setSearchName(e.target.value)}
          className="search-box"
        />

        <input
          type="text"
          placeholder="Search by registration number"
          value={searchReg}
          onChange={(e) => setSearchReg(e.target.value)}
          className="search-box"
        />
      </div>

      <div className="add-form">
        <h3>Add New Vehicle</h3>
        <input type="text" placeholder="Brand" value={addForm.brand || ''} onChange={(e) => setAddForm({ ...addForm, brand: e.target.value })} />
        <input type="text" placeholder="Model" value={addForm.model || ''} onChange={(e) => setAddForm({ ...addForm, model: e.target.value })} />
        <input type="number" placeholder="Year" value={addForm.year || ''} onChange={(e) => setAddForm({ ...addForm, year: e.target.value })} />
        <input type="text" placeholder="Type" value={addForm.type || ''} onChange={(e) => setAddForm({ ...addForm, type: e.target.value })} />
        <input type="text" placeholder="Registration Number" value={addForm.registrationNumber || ''} onChange={(e) => setAddForm({ ...addForm, registrationNumber: e.target.value })} />
        <input type="date" placeholder="Purchase Date" value={addForm.purchaseDate || ''} onChange={(e) => setAddForm({ ...addForm, purchaseDate: e.target.value })} />
        <input type="number" placeholder="Owner ID" value={addForm.ownerId || ''} onChange={(e) => setAddForm({ ...addForm, ownerId: e.target.value })} />
        <button className="approve-btn" onClick={handleAdd}>Add Vehicle</button>
      </div>

      <div className="vehicle-list">
        {filteredVehicles.length === 0 ? (
          <p>No vehicles found.</p>
        ) : (
          filteredVehicles.map(vehicle => (
            <div key={vehicle.id} className={`vehicle-card ${vehicle.policyStatus === 'EXPIRED' ? 'expired' : ''}`}>
              <h4>Vehicle ID: {vehicle.id}</h4>
              <p><strong>Type:</strong> {vehicle.type}</p>
              <p><strong>Brand:</strong> {vehicle.brand}</p>
              <p><strong>Model:</strong> {vehicle.model}</p>
              <p><strong>Year:</strong> {vehicle.year}</p>
              <p><strong>Registration:</strong> {vehicle.registrationNumber}</p>
              <p><strong>Purchase Date:</strong> {vehicle.purchaseDate}</p>
              <p><strong>Owner:</strong> {vehicle.ownerName}</p>
              <p><strong>Policy Status:</strong> {vehicle.policyStatus}</p>

              <button className="neutral-btn" onClick={() => window.open(`/admin/policy/${vehicle.id}`, '_blank')}>
                View Policy
              </button>

              {editingVehicleId === vehicle.id ? (
                <div className="edit-form">
                  <input type="text" placeholder="Brand" value={editForm.brand} onChange={(e) => setEditForm({ ...editForm, brand: e.target.value })} />
                  <input type="text" placeholder="Model" value={editForm.model} onChange={(e) => setEditForm({ ...editForm, model: e.target.value })} />
                  <input type="number" placeholder="Year" value={editForm.year} onChange={(e) => setEditForm({ ...editForm, year: e.target.value })} />
                  <input type="text" placeholder="Registration Number" value={editForm.registrationNumber} onChange={(e) => setEditForm({ ...editForm, registrationNumber: e.target.value })} />
                  <input type="date" placeholder="Purchase Date" value={editForm.purchaseDate} onChange={(e) => setEditForm({ ...editForm, purchaseDate: e.target.value })} />
                  <button onClick={() => handleUpdate(vehicle.id)} className="approve-btn">Save</button>
                  <button onClick={() => setEditingVehicleId(null)} className="neutral-btn">Cancel</button>
                </div>
              ) : (
                <div className="actions">
                  <button onClick={() => handleEdit(vehicle)} className="approve-btn">Edit</button>
                  <button onClick={() => handleDelete(vehicle.id)} className="reject-btn">Remove</button>
                </div>
              )}
            </div>
          ))
        )}
      </div>
    </div>
  );
}

export default AdminVehicleManagement;
