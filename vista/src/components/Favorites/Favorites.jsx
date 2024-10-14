import React, { useContext } from 'react';
import { CartContext } from '../../function/Cart'; 
import '../Favorites/Favorites.css';
import { useNavigate } from 'react-router-dom';

const Favorites = () => {
  const { favorites } = useContext(CartContext);
  const navigate = useNavigate();

  return (
    <section className="favorites">
      <h2>Mis Autos Favoritos</h2>
      <button onClick={() => navigate('/')} className="back-button">Volver a la tienda</button>
      <div className="favorites-grid">
        {Object.keys(favorites).length > 0 ? (
          Object.values(favorites).map((product) => (
            <div key={product.carId} className="favorite-card">
              {product.url ? (
                (product.url.startsWith('http://') || product.url.startsWith('https://')) ? (
                  <img src={product.url} alt={product.modelName} />
                ) : (
                  <div className="placeholder">URL no válida</div>
                )
              ) : (
                <div className="placeholder">Imagen no disponible</div>
              )}
              <h3>{product.modelName}</h3>
              <p>{product.discountPrice ? `$${product.discountPrice}` : `$${product.price.toFixed(2)}`}</p>
            </div>
          ))
        ) : (
          <p>No tienes autos favoritos</p>
        )}
      </div>
    </section>
  );
};

export default Favorites;
