import React, { createContext, useState, useEffect, useContext } from 'react';
import axios from 'axios';

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [token, setToken] = useState(null);  
  const [userDetails, setUserDetails] = useState(null);

  useEffect(() => {
    const storedToken = localStorage.getItem("token");
    if (storedToken) {
      setToken(storedToken); 
      fetchUserDetails(storedToken);
      setIsAuthenticated(true); 
    } else {
      setIsAuthenticated(false);
    }
  }, [token]);

  const fetchUserDetails = async (token) => {
    try {
      const response = await axios.get('http://localhost:4002/api/v1/auth/users', {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      setUserDetails(response.data);
    }
    catch (error) {
      console.error("Error fetching user details:", error);
    }
  }

  const login = (newToken) => {
    localStorage.setItem("token", newToken);  
    setToken(newToken);  
    fetchUserDetails(newToken);
    setIsAuthenticated(true);  
  };

  const logout = () => {
    localStorage.removeItem("token"); 
    setToken(null);  
    setUserDetails(null)
    setIsAuthenticated(false);  
  };

  return (
    <AuthContext.Provider value={{ isAuthenticated, login, logout, token,  userDetails }}>

      {children}
    </AuthContext.Provider>
  );
};
