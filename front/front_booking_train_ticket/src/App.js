import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import MainPage from './pages/main/main';
import SignIn from './pages/authorization/SignIn';
import SignUp from './pages/authorization/SignUp';
import RoutePage from './pages/route/route';
import { AuthProvider } from './components/AuthProvider';
import Train from './pages/train/train';
import SearchRoute from './pages/searchRoute/searchRoute';
import TrainBookingSeats from './pages/trainBookingSeats/trainBookingSeats';
import UsersBooking from './pages/usersBooking/usersBooking';

function App() {
  return (
    <AuthProvider>
      <Router>
        <Routes>
          <Route path="/" element={<MainPage/>}></Route>
          <Route path="/signIn" element={<SignIn/>}></Route>
          <Route path="/signUp" element={<SignUp/>}></Route>
          <Route path="/route" element={<RoutePage/>}></Route>
          <Route path="/train" element={<Train/>}></Route>
          <Route path="/searchRoute" element={<SearchRoute/>}></Route>
          <Route path="/chooseSeats" element={<TrainBookingSeats/>}></Route>
          <Route path="/usersBooking" element={<UsersBooking/>}></Route>
        </Routes>
      </Router>
    </AuthProvider>
  );
}

export default App;
