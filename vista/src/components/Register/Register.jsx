import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import {
  validateUsername,
  validatePassword,
  validateConfirmPassword,
  validateEmail,
  validateRole,
  validateName,
  validateAddress,
  validatePhoneNumber
} from '../Login/Validate';
import "../Register/Register.css";

const Register = () => {
  const [formData, setFormData] = useState({
    username: "",
    password: "",
    confirmPassword: "",
    email: "",
    role: "",
    name: "", 
    surname: "", 
    home_address: "",
    phone_number: "" 
  });
  const [errors, setErrors] = useState({});
  const [tempErrorMessage, setTempErrorMessage] = useState("");
  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
    setErrors({ ...errors, [e.target.name]: undefined });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const validationErrors = {};

    
    if (!validateUsername(formData.username)) {
      validationErrors.username = "El nombre de usuario no puede estar vacío";
    }
    if (!validatePassword(formData.password)) {
      validationErrors.password = "La contraseña debe tener más de 8 caracteres, al menos una mayúscula y un número";
    }
    if (!validateConfirmPassword(formData.password, formData.confirmPassword)) {
      validationErrors.confirmPassword = "Las contraseñas no coinciden";
    }
    if (!validateEmail(formData.email)) {
      validationErrors.email = "El correo electrónico debe contener un '@'";
    }
    if (!validateRole(formData.role)) {
      validationErrors.role = "Seleccione un rol válido.";
    }
    if (!validateName(formData.name)) {
      validationErrors.name = "El nombre debe contener solo letras";
    }
    if (!validateName(formData.surname)) {
      validationErrors.surname = "El apellido debe contener solo letras";
    }
    if (!validateAddress(formData.home_address)) {
      validationErrors.home_address = "La dirección no puede estar vacía";
    }
    if (!validatePhoneNumber(formData.phone_number)) {
      validationErrors.phone_number = "El número de teléfono debe contener solo números";
    }

    if (Object.keys(validationErrors).length > 0) {
      setErrors(validationErrors);
      setTempErrorMessage("Por favor, complete todos los campos requeridos.");
      return; 
    }

    try {
      const response = await fetch("http://localhost:4002/api/v1/auth/register", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(formData),
      });

      if (response.ok) {
        alert("Registro exitoso");
        navigate('/login');
      } else {
        const err = await response.json();
        setTempErrorMessage(err.message || "Error en el registro");
      }
    } catch (error) {
      setTempErrorMessage("Error de conexión con el servidor");
    }
  };

  const handleBack = () => {
    navigate("/options");
  };

  return (
    <div className="register-container">
      <form onSubmit={handleSubmit} className="register-form">
        <h2>Registrarse</h2>
        {tempErrorMessage && <span className="temp-error">{tempErrorMessage}</span>}

        <label htmlFor="name">Nombre:</label>
        <input
          type="text"
          name="name" 
          id="name"
          value={formData.name}
          onChange={handleChange}
          placeholder="Ingrese su nombre"
        />
        {errors.name && <span className="error">{errors.name}</span>}

        <label htmlFor="surname">Apellido:</label>
        <input
          type="text"
          name="surname"
          id="surname"
          value={formData.surname}
          onChange={handleChange}
          placeholder="Ingrese su apellido"
        />
        {errors.surname && <span className="error">{errors.surname}</span>}

        <label htmlFor="home_address">Dirección:</label>
        <input
          type="text"
          name="home_address"
          id="home_address"
          value={formData.home_address}
          onChange={handleChange}
          placeholder="Ingrese su dirección"
        />
        {errors.home_address && <span className="error">{errors.home_address}</span>}

        <label htmlFor="phone_number">Número de teléfono:</label>
        <input
          type="text"
          name="phone_number" 
          id="phone_number"
          value={formData.phone_number}
          onChange={handleChange}
          placeholder="Ingrese su número de teléfono"
        />
        {errors.phone_number && <span className="error">{errors.phone_number}</span>}

        <label htmlFor="email">Correo electrónico:</label>
        <input
          type="email"
          name="email"
          id="email"
          value={formData.email}
          onChange={handleChange}
          placeholder="Ingrese su correo electrónico"
        />
        {errors.email && <span className="error">{errors.email}</span>}

        <label htmlFor="role">Rol:</label>
        <select
          name="role"
          id="role"
          value={formData.role}
          onChange={handleChange}
        >
          <option value="">Seleccione un rol</option>
          <option value="ADMIN">ADMIN</option>
          <option value="USER">USER</option>
        </select>
        {errors.role && <span className="error">{errors.role}</span>}

        <label htmlFor="username">Nombre de usuario:</label>
        <input
          type="text"
          name="username"
          id="username"
          value={formData.username}
          onChange={handleChange}
          placeholder="Ingrese su nombre de usuario"
        />
        {errors.username && <span className="error">{errors.username}</span>}

        <label htmlFor="password">Contraseña:</label>
        <input
          type="password"
          name="password"
          id="password"
          value={formData.password}
          onChange={handleChange}
          placeholder="Ingrese su contraseña"
        />
        {errors.password && <span className="error">{errors.password}</span>}

        <label htmlFor="confirmPassword">Confirmar contraseña:</label>
        <input
          type="password"
          name="confirmPassword"
          id="confirmPassword"
          value={formData.confirmPassword}
          onChange={handleChange}
          placeholder="Confirme su contraseña"
        />
        {errors.confirmPassword && <span className="error">{errors.confirmPassword}</span>}
      </form>

      <div className="buttons-container">
        <button className="button" type="submit" onClick={handleSubmit}>Registrarse</button>
        <button className="button" type="button" onClick={handleBack}>Volver Atrás</button>
      </div>
    </div>
  );
};

export default Register;
