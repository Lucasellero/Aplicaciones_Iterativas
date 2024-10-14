import React, { useContext } from 'react';
import './Navbar.css';
import logo from '../../assets/images/Logo.jpg';
import { CartContext } from '../../function/Cart';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faUser } from '@fortawesome/free-solid-svg-icons';
import { Link } from 'react-router-dom';
import { AuthContext } from '../Login/AuthContext';
import { useNavigate } from 'react-router-dom';

const Navbar = () => {
    const { cartCount, setSearchTerm} = useContext(CartContext);
    const { isAuthenticated, logout} = useContext(AuthContext);
    const navigate = useNavigate();

    const handleSearchChange = (event) => {
        setSearchTerm(event.target.value);
    };

    const handleLogout = () => {
        logout();
        navigate('/');
    };

    return (
        <nav className="navbar">
            <div className="logo img">
                <img src={logo} alt="Logo" />
            </div>
            <div className="search-bar">
                <input
                    type="text"
                    placeholder="Buscar auto..."
                    onChange={handleSearchChange} 
                />
            </div>
            <div className="nav-links">
                {isAuthenticated && (<Link to="/favorites">❤️</Link>)}
                <span className="cart-icon">
                    {isAuthenticated ? (
                        <Link to="/carrito">🛒</Link>
                    ) : (
                        <Link to="/options">🛒</Link> 
                    )}
                    {cartCount > 0 && isAuthenticated && <span className="cart-count">{cartCount}</span>}
                </span>
                {isAuthenticated ? (
                <>
                <Link to="/user">
                    <FontAwesomeIcon icon={faUser} style={{ color: 'white' }} />
                </Link>
                <button onClick={handleLogout} className="logout-button">
                Cerrar sesión
                </button>
            </>
        ) : (
            <Link to="/options">
                <FontAwesomeIcon icon={faUser} style={{ color: 'white' }} />
            </Link>
        )}
            </div>
        </nav>
    );
};

export default Navbar;
