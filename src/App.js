import './App.css';
import { useState, useEffect } from 'react';
import { BrowserRouter, Routes, Route, useNavigate } from "react-router-dom";

import { ChakraProvider } from '@chakra-ui/react'

import Header from "./components/Header";
import Home from "./pages/home/Home";
import Login from "./pages/login/Login";
import Register from "./pages/register/Register";

function App() {  
  const [isLoggedIn, setIsLoggedin] = useState(false);
  useEffect(() => {
    // 토큰이 확인되면 로그인 상태로 취급
    const token = localStorage.getItem("access");
    if (token) {
      setIsLoggedin(true);
    }
    console.log('로그인 여부:', isLoggedIn);
    console.log('로그인 토큰:', token);
  }, [isLoggedIn]);

  const onLoggedIn = () => {
    setIsLoggedin(true);
  }
  
  const onLoggedOut = () => {
    setIsLoggedin(false);    
  }

  return (
    <ChakraProvider>
      <BrowserRouter>
        <div className="App">
          {isLoggedIn && <Header />}
          <Routes>
            <Route path="/" element={<Login onLoggedIn={onLoggedIn} />} />
            <Route path="/home" element={<Home isLoggedIn={isLoggedIn} onLoggedOut={onLoggedOut} />} />
            <Route path="/register" element={<Register />} />            
          </Routes>
        </div>
      </BrowserRouter>
    </ChakraProvider>
  );
}

export default App;
