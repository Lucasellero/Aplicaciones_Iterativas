import React, { useContext, useEffect, useState } from 'react';
import './FeaturedProducts.css';
import { CartContext } from '../../function/Cart';
import axios from 'axios';
import { AuthContext } from '../Login/AuthContext';

const FeaturedProducts = () => {
  const { addToCart, toggleFavorite, favorites, filters, searchTerm } = useContext(CartContext);
  const [products, setProducts] = useState([]);
  const [visibleProducts, setVisibleProducts] = useState(3);
  const [loading, setLoading] = useState(true);
  const { isAuthenticated, userDetails } = useContext(AuthContext); 
  useEffect(() => {
    const fetchProducts = async () => {
      try {
        const response = await axios.get('http://localhost:4002/car/all');
        const updatedProducts = response.data.map((product) => {
          if (Math.random() < 0.3) {
            const randomDiscount = Math.random() * (0.20 - 0.05) + 0.05;
            const discountPrice = product.price - (product.price * randomDiscount);
            return {
              ...product,
              discountPrice: discountPrice.toFixed(2)
            };
          }
          return product;
        });

        setProducts(updatedProducts);
      } catch (error) {
        console.error("Error fetching products:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchProducts();
  }, [filters, searchTerm]);

  const handleAddToCart = (product) => {
    console.log('isAuthenticated', isAuthenticated);
    console.log('userDetails', userDetails); 

    if (isAuthenticated) {
      if (userDetails && userDetails.userId) {
        addToCart(product, userDetails.userId);  
      } else {
        alert('No se encontró el ID del usuario');
      }
    } else {
      alert('Debes iniciar sesión para agregar productos al carrito');
    }
  };

  const handleFavorite = (product) => {
    if (isAuthenticated) {
      toggleFavorite(product.carId, product);
    } else {
      alert('Debes iniciar sesión para agregar productos a favoritos');
    }
  };

  const handleShowMore = () => {
    setVisibleProducts((prevCount) => prevCount + 6);
  };

  if (loading) {
    return <div>Loading...</div>;
  }

  const filteredProducts = products.filter(product => {
    return (
      (!filters.manufacturer || (product.manufacturer && product.manufacturer.toLowerCase() === filters.manufacturer.toLowerCase())) &&
      (!filters.model || (product.modelName && product.modelName.toLowerCase() === filters.model.toLowerCase())) &&
      (!filters.price ||
        (filters.price === 'bajo' ? product.price < 20000 : filters.price === 'alto' ? product.price >= 20000 : true)) &&
      (!filters.color || (product.color && product.color.toLowerCase() === filters.color.toLowerCase())) &&
      (!filters.year || product.year === filters.year) &&
      (!searchTerm || (product.modelName && product.modelName.toLowerCase().includes(searchTerm.toLowerCase())))
    );
  });

  return (
    <section className="featured-products">
      <h2>Productos Destacados</h2>
      <div className="products-grid">
        {filteredProducts.slice(0, visibleProducts).map((product) => {
          return (
            <div key={product.carId} className="product-card">
              {product.url ? (
                (product.url.startsWith('http://') || product.url.startsWith('https://')) ? (
                  <img src={product.url} alt={product.modelName} />
                ) : (
                  <div className="placeholder">URL no válida</div>
                )
              ) : (
                <div className="placeholder">Imagen no disponible</div>
              )}

              <h3>
                {product.modelName}
                <span
                  className="favorite-icon"
                  role="img"
                  aria-label="favorite"
                  onClick={() => handleFavorite(product)}
                >
                  <i className={`fas fa-heart ${favorites[product.carId] ? 'filled' : 'empty'}`}></i>
                </span>
              </h3>
              <p>
                {product.discountPrice ? (
                  <>
                    <span className="original-price">${product.price.toFixed(2)}</span>
                    <span className="discounted">${product.discountPrice}</span>
                  </>
                ) : (
                  `$${product.price.toFixed(2)}`
                )}
              </p>

              <button onClick={() => handleAddToCart(product)}>Agregar al carrito</button>
            </div>
          );
        })}
      </div>
      {visibleProducts < filteredProducts.length && (
        <button className="view-more" onClick={handleShowMore}>Ver más</button>
      )}
    </section>
  );
};

export default FeaturedProducts;
