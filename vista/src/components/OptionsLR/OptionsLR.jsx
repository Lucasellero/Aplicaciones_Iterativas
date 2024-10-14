import React from "react";
import { useNavigate } from "react-router-dom";
import './OptionsLR.css'; 

const OptionsLR = () => {
  const navigate = useNavigate();
  
  const handleLogin = () => {
    navigate("/login"); 
  };

  const handleRegister = () => {
    navigate("/register"); 
  };

  const handleBack = () => {
    navigate("/"); 
  };

  return (
    <div className="options-container">
      <div className="options-box">
        <h1 >Bienvenido</h1>
        <p >Por favor, elige una opción:</p>
        <div className="buttons-container">
          <button className="button" onClick={handleLogin}>
            Iniciar Sesión
          </button>
          <button className="button" onClick={handleRegister}>
            Registro
          </button>
          </div>
          <button className="button back-button" onClick={handleBack}>
            Volver Atrás
          </button>
        </div>
      </div>
  );
};

export default OptionsLR;
