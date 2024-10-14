import React, { useContext } from 'react';
import './FilterBar.css';
import { CartContext } from '../../function/Cart';

const FilterBar = () => {
  const { filters, updateFilters } = useContext(CartContext);

  const handleFilterChange = (event) => {
    const { name, value } = event.target;

    updateFilters({ [name]: value || undefined });
  };

  return (
    <div className="filter-bar">
      <div className="filter-item">
      <select name="manufacturer" value={filters.manufacturer || ''} onChange={handleFilterChange}>
      <option value="">Marca</option>
          <option value="ford">Ford</option>
          <option value="chevrolet">Chevrolet</option> 
          <option value="fiat">Fiat</option>
          <option value="volkswagen">Volkswagen</option>
          <option value="peugeot">Peugeot</option>
          <option value="renault">Renault</option>
          <option value="toyota">Toyota</option>
          <option value="citroen">Citroen</option>

        </select>
      </div>
      <div className="filter-item">
        <select name="model" value={filters.model || ''} onChange={handleFilterChange}>
          <option value="">Modelo</option>
          <option value="focus">Focus</option>
          <option value="camaro">Camaro</option>
          <option value="128 europa">128 Europa</option>
          <option value="duna">Duna</option>
          <option value="corsa">Corsa</option>
        </select>
      </div>
      <div className="filter-item">
        <select name="price" value={filters.price || ''} onChange={handleFilterChange}>
          <option value="">Precio</option>
          <option value="bajo">Bajo</option>
          <option value="alto">Alto</option>
        </select>
      </div>
      <div className="filter-item">
        <select name="color" value={filters.color || ''} onChange={handleFilterChange}>
          <option value="">Color</option>
          <option value="rojo">Rojo</option>
          <option value="azul">Azul</option>
          <option value="beige">Beige</option>
          <option value="blanco">Blanco</option>
          <option value="gris">Gris</option>
          <option value="negro">Negro</option>
          <option value ="verde">Verde</option>
          <option value="celeste">Celeste</option>
        </select>
      </div>
      <div className="filter-item">
        <select name="year" value={filters.year || ''} onChange={handleFilterChange}>
          <option value="">Año</option>
          <option value="2020">2020</option>
          <option value="1998">1998</option>
          <option value="1978">1978</option>
        </select>
      </div>
    </div>
  );
};

export default FilterBar;
