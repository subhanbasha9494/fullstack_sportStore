import { StrictMode, useEffect } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'
import { createBrowserRouter, RouterProvider, createRoutesFromElements, Route, Navigate } from 'react-router-dom';
import About from './components/About.jsx';
import Contact from './components/Contact.jsx';
import Login, {loginAction} from './components/Login.jsx';
import Cart from './components/Cart.jsx';
import ErrorPage from './components/ErrorPage.jsx';
import Home from './components/Home.jsx';
import ProductDetail from './components/ProductDetail.jsx';
import { ToastContainer, Bounce, toast } from "react-toastify";
import { CartProvider } from './store/cartContext.jsx';
import { AuthProvider, useAuth } from './store/AuthContect.jsx';
import Register, { registerAction } from './components/Register.jsx';
import Checkout from './components/Checkout.jsx';

const ProtectedRoute = ({ children }) => {
  const { isAuthenticated, isTokenExpired, jwtToken } = useAuth();
  const isAllowed = isAuthenticated && !isTokenExpired(jwtToken);

  useEffect(() => {
    if (!isAllowed && !sessionStorage.getItem("loginToastShown") && !sessionStorage.getItem("isLogout")) {
      sessionStorage.setItem("loginToastShown", "true");
      toast.error("Please login to continue.");
    }
    sessionStorage.removeItem("isLogout");
  }, [isAllowed]);

  if (!isAllowed) return <Navigate to="/login" replace />;
  sessionStorage.removeItem("loginToastShown");
  return children;
};

const routeDefinitions = createRoutesFromElements(
  <Route path="/" element={<App />} errorElement={<ErrorPage />}>
    <Route index element={<ProtectedRoute><Home /></ProtectedRoute>} />
    <Route path="/home" element={<ProtectedRoute><Home /></ProtectedRoute>} />
    <Route path="/about" element={<ProtectedRoute><About /></ProtectedRoute>} />
    <Route path="/contact" element={<ProtectedRoute><Contact /></ProtectedRoute>} />
    <Route path="/login" element={<Login />} action={loginAction} />
    <Route path="/cart" element={<ProtectedRoute><Cart /></ProtectedRoute>} />
    <Route path="/register" element={<Register />} action={registerAction} />
    <Route path="/checkout" element={<ProtectedRoute><Checkout /></ProtectedRoute>} />
    <Route path="/products/:productId" element={<ProtectedRoute><ProductDetail /></ProtectedRoute>} />
  </Route>
);

const appRouter = createBrowserRouter(routeDefinitions);

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <AuthProvider>
      <CartProvider>
        <RouterProvider router={appRouter} />
      </CartProvider>
    </AuthProvider>
    <ToastContainer
      position="top-center"
      autoClose={3000}
      hideProgressBar={false}
      newestOnTop={false}
      draggable
      pauseOnHover
      theme={localStorage.getItem("theme") === "dark" ? "dark" : "light"}
      transition={Bounce}
    />
  </StrictMode>,
)
