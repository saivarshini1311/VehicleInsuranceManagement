import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';

import Home from './pages/Home';
import Login from './pages/Login';
import Register from './pages/Register';

// User Pages
import UserDashboard from './pages/UserDashboard';
import DashboardHome from './pages/DashboardHome';
import Profile from './pages/Profile';
import Vehicles from './pages/Vehicles';
import Proposals from './pages/Proposals';
import Policies from './pages/Policies';
import Claims from './pages/Claims';
import Documents from './pages/Documents';
import Quotes from './pages/Quotes';
import Notifications from './pages/Notifications';
import QuoteDetails from './components/QuoteDetails';
import PaymentPage from './pages/PaymentPage';


// Admin Pages
import AdminDashboard from './pages/AdminDashboard';
import AdminDashboardHome from './pages/AdminDashboardHome';
import AdminDashboardReport from './pages/AdminDashboardReport';
import AdminUsers from './pages/AdminUserManagement';
import AdminVehicles from './pages/AdminVehicleManagement';
import AdminPolicies from './pages/AdminPolicyManagement';
import AdminQuotes from './pages/AdminQuoteManagement';
import AdminProposals from './pages/AdminProposalManagement';
import AdminClaims from './pages/AdminClaimManagement';
import AdminDocuments from './pages/AdminDocumentManagement';
import AdminNotifications from './pages/AdminReminders';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* Public Routes */}
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />

        {/* USER Dashboard */}
        <Route path="/user-dashboard" element={<UserDashboard />}>
          <Route index element={<DashboardHome />} />
          <Route path="profile" element={<Profile />} />
          <Route path="vehicles" element={<Vehicles />} />
          <Route path="proposals" element={<Proposals />} />
          <Route path="policies" element={<Policies />} />
          <Route path="claims" element={<Claims />} />
          <Route path="documents" element={<Documents />} />
          <Route path="quotes" element={<Quotes />} />
          <Route path="notifications" element={<Notifications />} />
         
        </Route>
        <Route path="/quote/view/:id" element={<QuoteDetails />} />
        <Route path="/user-dashboard/quote/view/:id" element={<QuoteDetails />} />
        <Route path="/payment/quote/:id" element={<PaymentPage />} />
        {/* ADMIN / OFFICER Dashboard */}
        <Route path="/officer-dashboard" element={<AdminDashboard />}>
          <Route index element={<AdminDashboardHome />} />
          <Route path="overview" element={<AdminDashboardReport />} />
          <Route path="users" element={<AdminUsers />} />
          <Route path="vehicles" element={<AdminVehicles />} />
          <Route path="policies" element={<AdminPolicies />} />
          <Route path="quotes" element={<AdminQuotes />} />
          <Route path="proposals" element={<AdminProposals />} />
          <Route path="claims" element={<AdminClaims />} />
          <Route path="documents" element={<AdminDocuments />} />
          <Route path="notifications" element={<AdminNotifications />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
