import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import Home from './views/Home/Home';
import Login from './components/Login/Login';
import Register from './components/Register/Register';
import OptionsLR from './components/OptionsLR/OptionsLR';
import Favorites from './components/Favorites/Favorites';
import { CartProvider } from './function/Cart';
import Carrito from './components/Carrito/Carrito';
import PostList from './components/Fetch/PostList';
import { AuthProvider } from './components/Login/AuthContext';
import { BrowserRouter, Routes, Route } from 'react-router-dom'; 

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <CartProvider>
      <AuthProvider>
        <BrowserRouter>
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
            <Route path="/options" element={<OptionsLR />} />
            <Route path="/favorites" element={<Favorites />} />
            <Route path="/carrito" element={<Carrito />} />
            <Route path="/postlist" element={<PostList />} />
          </Routes>
        </BrowserRouter>
      </AuthProvider>
    </CartProvider>
  </StrictMode>
);
