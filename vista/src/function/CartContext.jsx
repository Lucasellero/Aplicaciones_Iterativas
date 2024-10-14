import React, { createContext, useState } from 'react';

export const CartContext = createContext();

export const CartProvider = ({ children }) => {
    const [cartCount, setCartCount] = useState(0);
    const [searchTerm, setSearchTerm] = useState('');

    return (
        <CartContext.Provider value={{ cartCount, setCartCount, searchTerm, setSearchTerm }}>
            {children}
        </CartContext.Provider>
    );
};
