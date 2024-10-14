import React, { useState, useContext } from "react";
import { useNavigate } from 'react-router-dom';
import "../Login/Login.css";
import { AuthContext } from "./AuthContext"; 

const Login = ({ onBackClick }) => {
  const [loginData, setLoginData] = useState({ username: "", password: "" });
  const [error, setError] = useState(null);
  const navigate = useNavigate();
  const { login } = useContext(AuthContext); 

  const handleChange = (e) => {
    setLoginData({ ...loginData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!loginData.username || !loginData.password) {
      alert("Por favor, complete todos los campos.");
      return;
    }

    if (loginData.username.length < 3) {
      alert("El nombre de usuario es muy corto.");
      return;
    }

    if (loginData.password.length < 6) {
      alert("La contraseña es muy corta.");
      return;
    }

    console.log("Datos de inicio de sesión:", loginData);

    try {
      const response = await fetch("http://localhost:4002/api/v1/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(loginData),
      });

      if (response.ok) {
        const data = await response.json();
        alert("Inicio de sesión exitoso");
        
        localStorage.setItem("token", data.token);
        localStorage.setItem("userId", data.userId);

        login(data.token, data.userId);
         
        localStorage.setItem("token", data.token); 

        navigate("/");
      } else {
        const err = await response.json();
        setError(err.message || "Error en el inicio de sesión");
      }
    } catch (error) {
      setError("Error de conexión con el servidor");
    }
  };

  const handleBack = () => {
    navigate('/options'); 
  };

  return (
    <div className="login-container">
      <form className="login-form" onSubmit={handleSubmit}>
        <h2 className="titulo">Iniciar Sesión</h2>
        {error && <div className="error">{error}</div>}
        <label className="label">Usuario:</label>
        <input
          type="text"
          name="username"
          className="input"
          placeholder="Ingrese su usuario"
          value={loginData.username}
          onChange={handleChange}
        />

        <label className="label">Contraseña:</label>
        <input
          type="password"
          name="password"
          className="input"
          placeholder="Ingrese su contraseña"
          value={loginData.password}
          onChange={handleChange}
        />

        <div className="buttons-container">
          <button type="submit" className="button">Iniciar Sesión</button>
          <button type="button" className="button" onClick={handleBack}>Volver Atrás</button>
        </div>
      </form>
    </div>
  );
};

export default Login;
