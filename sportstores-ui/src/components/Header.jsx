import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faShoppingBasket, faTags, faSun, faMoon } from "@fortawesome/free-solid-svg-icons";
import { useState, useEffect } from "react";
import { Link, NavLink, useNavigate } from "react-router-dom";
import { useCart } from "../store/cartContext.jsx";
import { useAuth } from "../store/AuthContect.jsx";
import { toast } from "react-toastify";

export default function Header() {

  const [theme, setTheme] = useState(() => {
    return localStorage.getItem("theme") === "dark" ? "dark" : "light";
  });

  const { totalQuantity } = useCart();
  const { isAuthenticated, logout, user } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    if (theme === "dark") {
      document.documentElement.classList.add("dark");
    } else {
      document.documentElement.classList.remove("dark");
    }
  }, [theme]);

  const toggleTheme = () => {
    setTheme((prevTheme) => {
      const newTheme = prevTheme === "light" ? "dark" : "light";
      localStorage.setItem("theme", newTheme);
      return newTheme;
    });
  };

  const handleLogout = () => {
    logout();
    sessionStorage.setItem("isLogout", "true");
    toast.success("Logged out successfully.");
    navigate("/login");
  };

  const navLinkClass = "text-center text-lg font-primary font-semibold text-primary dark:text-light hover:text-dark dark:hover:text-lighter";

  return (
    <header className="border-b border-gray-300 dark:border-gray-600 sticky top-0 z-20 bg-normalbg dark:bg-darkbg">
      <div className="flex items-center justify-between mx-auto max-w-[1152px] px-6 py-4">
        <Link to="/" className={navLinkClass}>
          <FontAwesomeIcon icon={faTags} className="h-8 w-8" />
          <span className="font-bold">Sport Stickers</span>
        </Link>
        <nav className="flex items-center py-2 z-10">
          <button
            className="flex items-center justify-center mx-3 w-8 h-8 rounded-full border border-primary dark:border-light transition duration-300 hover:bg-gray-300 dark:hover:bg-gray-600"
            aria-label="Toggle theme"
            onClick={toggleTheme}
          >
            <FontAwesomeIcon icon={theme === "dark" ? faMoon : faSun} className="w-4 h-4 dark:text-light text-primary" />
          </button>
          <ul className="flex space-x-6">
            <li>
              <NavLink to="/home" className={({ isActive }) => isActive ? `underline ${navLinkClass}` : navLinkClass}>
                Home
              </NavLink>
            </li>
            <li>
              <NavLink to="/about" className={({ isActive }) => isActive ? `underline ${navLinkClass}` : navLinkClass}>
                About
              </NavLink>
            </li>
            <li>
              <NavLink to="/contact" className={({ isActive }) => isActive ? `underline ${navLinkClass}` : navLinkClass}>
                Contact
              </NavLink>
            </li>
            {isAuthenticated ? (
              <>
                <li>
                  <span className={navLinkClass}>Hi, {user?.name}</span>
                </li>
                <li>
                  <button onClick={handleLogout} className={navLinkClass}>
                    Logout
                  </button>
                </li>
              </>
            ) : (
              <li>
                <NavLink to="/login" className={({ isActive }) => isActive ? `underline ${navLinkClass}` : navLinkClass}>
                  Login
                </NavLink>
              </li>
            )}
            <li>
              <Link to="/cart" className="relative text-primary py-2">
                <FontAwesomeIcon icon={faShoppingBasket} className="text-primary dark:text-light w-6" />
                <div className="absolute -top-2 -right-6 text-xs bg-yellow-400 text-black font-semibold rounded-full px-2 py-1 leading-none">
                  {totalQuantity}
                </div>
              </Link>
            </li>
          </ul>
        </nav>
      </div>
    </header>
  );
}