import './App.css';
import {BrowserRouter, Routes, Route} from "react-router-dom";

import { ChakraProvider } from '@chakra-ui/react'

import Header from "./components/Header";
import Home from "./pages/home/Home";
import Login from "./pages/login/Login";
import Register from "./pages/register/Register";

function App() {
  return (    
      <ChakraProvider>
        <BrowserRouter>
          <div className="App">
            <Header />
            <Routes>
              <Route path="/" element={<Login />} />
              <Route path="/home" element={<Home />} />
              <Route path="/register" element={<Register />} />
            </Routes>         
          </div>
        </BrowserRouter>    
      </ChakraProvider>
  );
}

export default App;
