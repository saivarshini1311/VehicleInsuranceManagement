import React, { useState } from 'react';
import './Documents.css';

export default function Documents() {
  const [documents, setDocuments] = useState([
    {
      name: 'Aadhaar Card',
      type: 'Identity',
      uploadedDate: '2025-07-01',
      status: 'Verified',
      url: '/docs/aadhaar.pdf',
    },
    {
      name: 'RC Book',
      type: 'Vehicle',
      uploadedDate: '2025-07-03',
      status: 'Pending',
      url: '/docs/rc.pdf',
    },
    {
      name: 'Policy Document',
      type: 'Insurance',
      uploadedDate: '2025-07-05',
      status: 'Verified',
      url: '/docs/policy.pdf',
    },
    {
      name: 'FIR Copy',
      type: 'Claim',
      uploadedDate: '2025-07-06',
      status: 'Rejected',
      url: '/docs/fir.pdf',
    },
  ]);

  const [uploadForm, setUploadForm] = useState({
    name: '',
    type: '',
    file: null,
  });

  const handleChange = (e) => {
    const { name, value, files } = e.target;
    setUploadForm((prev) => ({
      ...prev,
      [name]: files ? files[0] : value,
    }));
  };

  const handleUpload = (e) => {
    e.preventDefault();

    if (!uploadForm.name || !uploadForm.type || !uploadForm.file) {
      alert('Please fill in all fields.');
      return;
    }

    const newDoc = {
      name: uploadForm.name,
      type: uploadForm.type,
      uploadedDate: new Date().toISOString().slice(0, 10),
      status: 'Pending',
      url: URL.createObjectURL(uploadForm.file), // for demo only
    };

    setDocuments([...documents, newDoc]);
    setUploadForm({ name: '', type: '', file: null });

    alert('Document uploaded successfully!');
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
          placeholder="Document Name (e.g., PAN Card)"
          required
        />
        <select name="type" value={uploadForm.type} onChange={handleChange} required>
          <option value="">Select Category</option>
          <option value="Identity">Proof of Identity</option>
          <option value="Vehicle">Vehicle Document</option>
          <option value="Insurance">Insurance Document</option>
          <option value="Claim">Claim Document</option>
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
            <th>Document Name</th>
            <th>Type</th>
            <th>Uploaded Date</th>
            <th>Status</th>
            <th>Download</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {documents.map((doc, index) => (
            <tr key={index}>
              <td>{doc.name}</td>
              <td>{doc.type}</td>
              <td>{doc.uploadedDate}</td>
              <td className={`status ${doc.status.toLowerCase()}`}>{doc.status}</td>
              <td>
                <a href={doc.url} target="_blank" rel="noopener noreferrer" className="download-btn">View</a>
              </td>
              <td>
                <button className="action-btn">Replace</button>
                <button className="action-btn delete">Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
