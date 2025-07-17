import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Documents.css';

export default function Documents() {
  const userId = localStorage.getItem('userId');
  const [documents, setDocuments] = useState([]);
  const [uploadForm, setUploadForm] = useState({
    name: '',
    type: '',
    file: null,
  });

  const [editingDocId, setEditingDocId] = useState(null);
  const [editedData, setEditedData] = useState({
    type: '',
    status: '',
  });

  const fetchDocuments = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/api/documents/user/${userId}`);
      setDocuments(response.data);
    } catch (error) {
      console.error('Error fetching documents:', error);
    }
  };

  useEffect(() => {
    fetchDocuments();
  }, []);

  const handleChange = (e) => {
    const { name, value, files } = e.target;
    setUploadForm((prev) => ({
      ...prev,
      [name]: files ? files[0] : value,
    }));
  };

  const handleUpload = async (e) => {
    e.preventDefault();
    const token = localStorage.getItem('token');
    const formData = new FormData();
    formData.append('name', uploadForm.name);
    formData.append('type', uploadForm.type);
    formData.append('file', uploadForm.file);
    formData.append('userId', userId);

    try {
      await axios.post('http://localhost:8080/api/documents/upload', formData, {
        headers: { Authorization: `Bearer ${token}` },
      });
      alert('Document uploaded successfully!');
      fetchDocuments();
      setUploadForm({ name: '', type: '', file: null });
    } catch (error) {
      console.error('Upload failed:', error.response?.data || error.message);
      alert('Upload failed!');
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm('Are you sure you want to delete this document?')) return;
    try {
      await axios.delete(`http://localhost:8080/api/documents/${id}`);
      alert('Deleted successfully');
      fetchDocuments();
    } catch (error) {
      console.error('Delete failed:', error);
      alert('Delete failed!');
    }
  };

  const handleEditClick = (doc) => {
    setEditingDocId(doc.id);
    setEditedData({
      type: doc.type || '',
      status: doc.status || '',
    });
  };

  const handleEditChange = (e) => {
    const { name, value } = e.target;
    setEditedData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleEditSave = async (id) => {
    try {
      await axios.put(`http://localhost:8080/api/documents/${id}`, editedData);
      alert('Document updated successfully!');
      setEditingDocId(null);
      fetchDocuments();
    } catch (error) {
      console.error('Update failed:', error);
      alert('Update failed!');
    }
  };

  const handleEditCancel = () => {
    setEditingDocId(null);
    setEditedData({ type: '', status: '' });
  };

  return (
    <div className="documents-wrapper">
      <h2>My Documents</h2>

      <form className="upload-form" onSubmit={handleUpload}>
        <input
          type="text"
          name="name"
          value={uploadForm.name}
          onChange={handleChange}
          placeholder="Document Name"
          required
        />
        <select name="type" value={uploadForm.type} onChange={handleChange} required>
          <option value="">Select Category</option>
          <option value="Identity">Identity</option>
          <option value="Vehicle">Vehicle</option>
          <option value="Insurance">Insurance</option>
          <option value="Claim">Claim</option>
        </select>
        <input
          type="file"
          name="file"
          accept=".pdf,.jpg,.jpeg,.png"
          onChange={handleChange}
          required
        />
        <button type="submit">Upload</button>
      </form>

      <table className="documents-table">
        <thead>
          <tr>
            <th>Name</th>
            <th>Type</th>
            <th>Date</th>
            <th>Status</th>
            <th>View</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {documents.length === 0 ? (
            <tr><td colSpan="6">No documents found.</td></tr>
          ) : (
            documents.map((doc) => (
              <tr key={doc.id}>
                <td>{doc.name || 'N/A'}</td>
                <td>
                  {editingDocId === doc.id ? (
                    <select name="type" value={editedData.type} onChange={handleEditChange}>
                      <option value="Identity">Identity</option>
                      <option value="Vehicle">Vehicle</option>
                      <option value="Insurance">Insurance</option>
                      <option value="Claim">Claim</option>
                    </select>
                  ) : (
                    doc.type || 'N/A'
                  )}
                </td>
                <td>{doc.uploadDate || 'N/A'}</td>
                <td className={`status ${doc.status ? doc.status.toLowerCase() : 'unknown'}`}>
                  {doc.status || 'Unknown'}
                </td>

                <td>
                  <a
                    href={doc.fileUrl || `http://localhost:8080/api/documents/download/${doc.id}`}
                    target="_blank"
                    rel="noopener noreferrer"
                    className="download-btn"
                  >
                    View
                  </a>
                </td>
                <td>
                  {editingDocId === doc.id ? (
                    <>
                      <button className="action-btn save" onClick={() => handleEditSave(doc.id)}>Save</button>
                      <button className="action-btn cancel" onClick={handleEditCancel}>Cancel</button>
                    </>
                  ) : (
                    <>
                      <button className="action-btn edit" onClick={() => handleEditClick(doc)}>Edit</button>
                      <button className="action-btn delete" onClick={() => handleDelete(doc.id)}>Delete</button>
                    </>
                  )}
                </td>
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  );
}
