import React, { useState } from 'react';
import FilterBar from './FilterBar';

const CarListing = () => {
  const [filteredCars, setFilteredCars] = useState([]);

  return (
    <div>
      <FilterBar setFilteredCars={setFilteredCars} />
      
      <div>
        {filteredCars.length > 0 ? (
          <ul>
            {filteredCars.map((car) => (
              <li key={car.id}>{car.manufacturer} - {car.modelName}</li>
            ))}
          </ul>
        ) : (
          <p>No se encontraron autos.</p>
        )}
      </div>
    </div>
  );
};

export default CarListing;
