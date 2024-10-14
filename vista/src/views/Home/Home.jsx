import React from 'react';
import Navbar from '../../components/Navbar/Navbar';
import HeroSection from '../../components/HeroSection/HeroSection';
import FeaturedProducts from '../../components/FeaturedProducts/FeaturedProducts';
import Footer from '../../components/Footer/Footer';
import './Home.css';
import FilterBar from '../../components/FilterBar/FilterBar';
import 'font-awesome/css/font-awesome.min.css';
import { useState } from 'react';



const Home = () => {
  const [filteredProducts, setFilteredProducts] = useState([]);
  
    return (
        <div className="home">
          <Navbar />
          <FilterBar setFilteredProducts = {setFilteredProducts}/>
          <HeroSection />
          <FeaturedProducts products = {filteredProducts}/>
          <Footer />
        </div>
    );
  };
  
  export default Home;