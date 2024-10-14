import React, { useContext, useEffect, useState } from 'react';
import { CartContext } from '../../function/Cart';
import '../Carrito/Carrito.css';
import { useNavigate } from 'react-router-dom';

const Carrito = () => {
  const { cartItems, removeFromCart, addToCart, userDetails, setCartItems } = useContext(CartContext);
  const navigate = useNavigate();
  const [quantity, setQuantity] = useState(1);

  useEffect(() => {
    if (userDetails && userDetails.cartId) {
      fetch(`http://localhost:4002/api/v1/carrito/my-cart?cartId=${userDetails.cartId}`, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      })
      .then(response => response.json())
      .then(data => {
        setCartItems(data.items);  
      })
      .catch(error => console.error("Error fetching cart data:", error));
    } else {
      console.log('No cartId found for user');
    }
  }, [userDetails, setCartItems]);
  

  const total = cartItems.length > 0 
    ? cartItems.reduce((acc, car) => acc + parseFloat(car.price) * car.quantity, 0).toFixed(2)
    : 0;

  const handleBack = () => {
    navigate(-1);
  };

  const handleRemove = (productId) => {
    removeFromCart(userDetails.cartId, productId);  
  };

  const handleAddToCart = (car) => {
    if (quantity > 0) {
      addToCart(userDetails.cartId, car.carId, quantity);  
      setQuantity(1);  
    } else {
      alert("Por favor, selecciona una cantidad mayor a 0");
    }
  };
  
  return (
    <section className="cart">
      <h2>Carrito de Compras</h2>
      <button onClick={handleBack} className="back-button">Volver Atrás</button>
      
      {cartItems.length > 0 ? (
        <div className="cart-grid">
          {cartItems.map((car) => (
            <div key={car.carId} className="cart-card">
              {car.url ? (
                <img src={car.url} alt={car.name} className="car-image" />
              ) : (
                <img src="/default-image.jpg" alt="default" className="car-image" />
              )}
              <h3>{car.name}</h3>
              <p>
                ${car.price.toLocaleString()} x {car.quantity} = ${(
                  car.price * car.quantity
                ).toFixed(2)}
              </p>
              <button className="remove-button" onClick={() => handleRemove(car.carId)}>
                Eliminar 1
              </button>
            </div>
          ))}
        </div>
      ) : (
        <p>No hay autos en el carrito.</p>
      )}

<div className="add-to-cart">
  <input
    type="number"
    value={quantity}
    min="1"
    onChange={(e) => setQuantity(e.target.value)} 
  />
  <button onClick={() => handleAddToCart(cartItems[0])}>Agregar al carrito</button>
</div>


      {cartItems.length > 0 && (
        <div className="total">
          <h3>Total: ${total}</h3>
        </div>
      )}
    </section>
  );
};

export default Carrito;
