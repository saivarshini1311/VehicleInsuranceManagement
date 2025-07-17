import React, { useEffect, useState } from 'react';
import './AdminDashboard.css';

function AdminDocumentManagement() {
  const [documents, setDocuments] = useState([]);
  const [typeFilter, setTypeFilter] = useState('');
  const [statusFilter, setStatusFilter] = useState('');
  useEffect(() => {
    const mockDocs = [
      {
        id: 1,
        type: 'AADHAAR',
        status: 'PENDING',
        uploadDate: '2025-07-15',
        url: 'https://example.com/aadhaar1.pdf',
        user: { name: 'Jeeva A', email: 'jeeva111@gmail.com' }
      },
      {
        id: 2,
        type: 'PAN',
        status: 'PENDING',
        uploadDate: '2025-07-14',
        url: 'https://example.com/pan2.pdf',
        user: { name: 'Saivarshini', email: 'sai@gmail.com' }
      },
      {
        id: 1,
        type: 'RC_BOOK',
        status: 'REJECTED',
        uploadDate: '2025-07-15',
        url: 'https://example.com/rc3.pdf',
        user: { name: 'Jeeva A', email: 'jeeva111@gmail.com' }
      },
      {
        id: 2,
        type: 'INSURANCE_COPY',
        status: 'PENDING',
        uploadDate: '2025-07-14',
        url: 'https://example.com/insurance4.pdf',
        user: { name: 'Saivarshini', email: 'sai@gmail.com' }
      }
    ];
    setDocuments(mockDocs);
  }, []);

  const handleApprove = (id) => {
    const updated = documents.map(doc =>
      doc.id === id ? { ...doc, status: 'APPROVED' } : doc
    );
    setDocuments(updated);
    alert(`Document ${id} approved`);
  };

  const handleReject = (id) => {
    const reason = prompt("Enter rejection reason:");
    if (!reason) return;
    const updated = documents.map(doc =>
      doc.id === id ? { ...doc, status: 'REJECTED', rejectionReason: reason } : doc
    );
    setDocuments(updated);
    alert(`Document ${id} rejected`);
  };

  const handleDelete = (id) => {
    if (window.confirm('Are you sure you want to delete this document?')) {
      const filtered = documents.filter(doc => doc.id !== id);
      setDocuments(filtered);
      alert(`Document ${id} deleted`);
    }
  };

  const handleView = (url) => {
    window.open(url, '_blank');
  };

  const filteredDocuments = documents.filter(doc => {
    const matchType = typeFilter ? doc.type.toLowerCase() === typeFilter.toLowerCase() : true;
    const matchStatus = statusFilter ? doc.status.toLowerCase() === statusFilter.toLowerCase() : true;
    return matchType && matchStatus;
  });

  return (
    <div className="admin-container">
      <h2>Document Management</h2>

      <div className="filters">
        <label>Type:</label>
        <select value={typeFilter} onChange={(e) => setTypeFilter(e.target.value)}>
          <option value="">All</option>
          <option value="AADHAAR">Aadhaar</option>
          <option value="PAN">PAN</option>
          <option value="RC_BOOK">RC Book</option>
          <option value="INSURANCE_COPY">Insurance Copy</option>
          <option value="ACCIDENT_PROOF">Accident Proof</option>
        </select>

        <label>Status:</label>
        <select value={statusFilter} onChange={(e) => setStatusFilter(e.target.value)}>
          <option value="">All</option>
          <option value="PENDING">Pending</option>
          <option value="APPROVED">Approved</option>
          <option value="REJECTED">Rejected</option>
        </select>
      </div>

      <div className="document-list">
        {filteredDocuments.length === 0 ? (
          <p>No documents found.</p>
        ) : (
          filteredDocuments.map(doc => (
            <div key={doc.id} className="document-card">
              <h4>{doc.type.replace('_', ' ')} (ID: {doc.id})</h4>
              <p><strong>User:</strong> {doc.user?.name} ({doc.user?.email})</p>
              <p><strong>Status:</strong> {doc.status}</p>
              <p><strong>Uploaded:</strong> {doc.uploadDate}</p>
              {doc.rejectionReason && <p><strong>Reason:</strong> {doc.rejectionReason}</p>}

              <div className="actions">
                <button onClick={() => handleView(doc.url)} className="neutral-btn">🔍 View File</button>
                {doc.status === 'PENDING' && (
                  <>
                    <button onClick={() => handleApprove(doc.id)} className="approve-btn">✅ Approve</button>
                    <button onClick={() => handleReject(doc.id)} className="reject-btn">❌ Reject</button>
                  </>
                )}
                <button onClick={() => handleDelete(doc.id)} className="delete-btn">🗑️ Delete</button>
              </div>
            </div>
          ))
        )}
      </div>
    </div>
  );
}

export default AdminDocumentManagement;
