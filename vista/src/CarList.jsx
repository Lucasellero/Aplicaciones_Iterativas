import React, { useState, useEffect } from "react";
import CarCard from "./CarCard"; // un componente que muestre cada auto individualmente

const CarList = () => {
  const [cars, setCars] = useState([]);
  const [error, setError] = useState(null);

  // URL del endpoint en tu backend para obtener la lista de autos
  const url = 'http://localhost:8080/car/all'; // Asegúrate que el backend esté corriendo en este puerto

  useEffect(() => {
    fetch(url)
      .then(response => {
        if (!response.ok) {
          throw new Error("Error al obtener los datos");
        }
        return response.json();
      })
      .then(data => setCars(data))
      .catch(error => setError(error.message));
  }, []);

  if (error) {
    return <div>Error: {error}</div>;
  }

  return (
    <div>
      <h1>Lista de Autos</h1>
      <div>
        {cars.map(car => (
          <CarCard
            key={car.carId}
            manufacturer={car.manufacturer}
            modelName={car.modelName}
            modelYear={car.modelYear}
            color={car.color}
            price={car.price}
            stock={car.stock}
          />
        ))}
      </div>
    </div>
  );
};

export default CarList;
