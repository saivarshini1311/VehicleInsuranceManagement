import React, { useEffect, useState } from 'react';
import './Notifications.css';

export default function Notifications() {
  const [notifications, setNotifications] = useState([
    {
      id: 1,
      type: 'Policy Expiry',
      message: 'Your vehicle policy for TN09 AB 1234 is expiring on 15 July 2025.',
      date: '2025-07-08',
    },
    {
      id: 2,
      type: 'Premium Due',
      message: 'Upcoming premium for Policy #102 is due on 12 July 2025.',
      date: '2025-07-07',
    },
    {
      id: 3,
      type: 'Quote Expiry',
      message: 'Quote for Proposal #222 will expire on 10 July 2025.',
      date: '2025-07-06',
    },
    {
      id: 4,
      type: 'Claim Approved',
      message: 'Your claim #CLM12211 has been approved for ₹12,000.',
      date: '2025-07-04',
    },
    {
      id: 5,
      type: 'New Quote Available',
      message: 'New quote is available for Proposal #225.',
      date: '2025-07-03',
    },
  ]);

  return (
    <div className="notifications-container">
      <h2>🔔 Notifications & Reminders</h2>
      {notifications.length === 0 ? (
        <p className="no-notify">No new notifications.</p>
      ) : (
        <ul className="notifications-list">
          {notifications.map((note) => (
            <li key={note.id} className={`notify-item ${note.type.toLowerCase().replace(/\s/g, '-')}`}>
              <div className="notify-header">
                <span className="notify-type">{note.type}</span>
                <span className="notify-date">{note.date}</span>
              </div>
              <p className="notify-msg">{note.message}</p>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}
