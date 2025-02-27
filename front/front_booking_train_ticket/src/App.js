import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import MainPage from './pages/main/main';
import SignIn from './pages/authorization/SignIn';
import SignUp from './pages/authorization/SignUp';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<MainPage/>}></Route>
        <Route path="/signIn" element={<SignIn/>}></Route>
        <Route path="/signUp" element={<SignUp/>}></Route>
      </Routes>
    </Router>
  );
}

export default App;
