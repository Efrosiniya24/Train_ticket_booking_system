import React, { createContext, useState, useContext, useEffect } from 'react';
import axios from 'axios';

const AuthContext = createContext();

export const useAuth = () => useContext(AuthContext);
export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const token = localStorage.getItem('accessToken');
    const role = localStorage.getItem('userRole');
    const id = localStorage.getItem('userId');

    if (token && role && id) {
      setUser({ token, role, id });
    }

    setLoading(false);
  }, []);

  const login = async (email, password) => {
    try {
      const response = await axios.post('http://localhost:8080/train/auth/signIn', { email, password });
      console.log('Response data:', response.data);

      localStorage.setItem('accessToken', response.data.access);
      localStorage.setItem('userRole', response.data.role);
      localStorage.setItem('userId', response.data.id);

      setUser({ token: response.data.access, role: response.data.role, id: response.data.id });
    } catch (error) {
      console.error('Login failed:', error);
    }
  };

  const logout = () => {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('userRole');
    localStorage.removeItem('userId'); 
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ user, login, logout, loading }}>
      {!loading && children}
    </AuthContext.Provider>
  );
};
