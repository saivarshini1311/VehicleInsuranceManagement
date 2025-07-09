import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Home from './pages/Home';
import Login from './pages/Login';
import Register from './pages/Register';
import UserDashboard from './pages/UserDashboard';

import Profile from './pages/Profile';
import Vehicles from './pages/Vehicles';
import Proposals from './pages/Proposals';
import Policies from './pages/Policies';
import Claims from './pages/Claims';
import Documents from './pages/Documents';
import Quotes from './pages/Quotes';
import Notifications from './pages/Notifications';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* Public Routes */}
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />

        {/* User Dashboard with Nested Routes */}
        <Route path="/user-dashboard" element={<UserDashboard />}>
          <Route path="profile" element={<Profile />} />
          <Route path="vehicles" element={<Vehicles />} />
          <Route path="proposals" element={<Proposals />} />
          <Route path="policies" element={<Policies />} />
          <Route path="claims" element={<Claims />} />
          <Route path="documents" element={<Documents />} />
          <Route path="quotes" element={<Quotes />} />
          <Route path="notifications" element={<Notifications />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
