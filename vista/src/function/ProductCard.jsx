import React, { useContext } from 'react';
import { CartContext } from '../../function/Cart'; 

const ProductCard = ({ product }) => {
const { addToCart } = useContext(CartContext); 

return (
    <div className="product-card">
    <img src={product.image} alt={product.name} />
    <h3>{product.name}</h3>
    <p>{product.description}</p>
    <button onClick={addToCart}>Agregar al carrito</button>
    </div>
);
};

export default ProductCard;
