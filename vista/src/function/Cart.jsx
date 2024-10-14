import React, { createContext, useState, useEffect } from 'react';

export const CartContext = createContext();

export const CartProvider = ({ children }) => {
  const [cartItems, setCartItems] = useState([]);
  const [favorites, setFavorites] = useState({});
  const [searchTerm, setSearchTerm] = useState('');
  const [filters, setFilters] = useState({
    manufacturer: '',
    model: '',
    price: '',
    color: '',
    year: ''
  });
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [userDetails, setUserDetails] = useState(null);

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      fetchUserDetails(token);
    }
  }, []);

  const fetchUserDetails = async (token) => {
    try {
      const response = await fetch("http://localhost:4002/api/v1/auth/users", {
        headers: {
          "Authorization": `Bearer ${token}`
        }
      });
      const data = await response.json();
      if (data) {
        setIsAuthenticated(true);
        setUserDetails(data);
      }
    } catch (error) {
      setIsAuthenticated(false);
      setUserDetails(null);
      console.error('Error fetching user details:', error);
    }
  };

  const login = (token) => {
    localStorage.setItem("token", token);
    setIsAuthenticated(true);
    fetchUserDetails(token);
  };

  const logout = () => {
    localStorage.removeItem("token");
    setIsAuthenticated(false);
    setUserDetails(null);
  };

  const validateCartInputs = (userId, productId) => {
    if (!userId || !productId) {
      console.error('Error: userId or productId are missing.');
      return false;
    }
    return true;
  };

  const addToCart = async (productId) => {
    const userId = userDetails?.cartId;
    if (!validateCartInputs(userId, productId)) return;
  
    try {
      const productInCart = cartItems.find(item => item.productId === productId);
      if (productInCart) {
        await updateCartItemQuantity(userId, productId, productInCart.quantity + 1);
        return;
      }
  
      const response = await fetch(`http://localhost:4002/carrito/add-product?cartId=${userId}&productId=${productId}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${localStorage.getItem("token")}`,
        },
      });
  
      if (response.ok) {
        const data = await response.json();
        setCartItems(data.items);  
        console.log('Producto agregado al carrito:', data);
      } else {
        throw new Error('Failed to add product to cart');
      }
    } catch (error) {
      console.error('Error adding product to cart:', error);
    }
  };
  

  const removeFromCart = async (cartId, productId) => {
    if (!validateCartInputs(cartId, productId)) return;

    try {
      const response = await fetch(`http://localhost:4002/carrito/${cartId}/delete-product/${productId}`, {
        method: "DELETE",
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
          "Content-Type": "application/json",
        },
      });

      if (response.ok) {
        const data = await response.json();
        setCartItems(data.items); 
        console.log("Producto eliminado del carrito:", data);
      } else {
        throw new Error("Failed to remove product from cart");
      }
    } catch (error) {
      console.error("Error removing product from cart:", error);
    }
  };



  const toggleFavorite = (productId, productDetails) => {
    setFavorites(prevFavorites => {
      if (prevFavorites[productId]) {
        const updatedFavorites = { ...prevFavorites };
        delete updatedFavorites[productId];
        return updatedFavorites;
      }
      return {
        ...prevFavorites,
        [productId]: productDetails,
      };
    });
  };

  const isProductInFavorites = (productId) => {
    return favorites.hasOwnProperty(productId);
  };

  const updateFilters = (newFilters) => {
    setFilters((prevFilters) => ({
      ...prevFilters,
      ...newFilters,
    }));
  };

  const cartCount = cartItems.length;

  return (
    <CartContext.Provider value={{
      searchTerm,
      setSearchTerm,
      cartItems,
      cartCount,
      addToCart,
      removeFromCart,
      favorites,
      toggleFavorite,
      isProductInFavorites,
      filters,
      updateFilters,
      isAuthenticated,
      login,
      logout,
      userDetails 
    }}>
      {children}
    </CartContext.Provider>
  );
};

export default CartProvider;
