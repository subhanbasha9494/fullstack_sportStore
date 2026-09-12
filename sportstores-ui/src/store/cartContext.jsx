import { createContext, useContext, useReducer, useEffect } from "react";
import cartService from "../api/cartService";
import { useAuth } from "./AuthContect";
import imageMap from "../utils/imageMap";

export const CartContext = createContext();
export const useCart = () => useContext(CartContext);

const ADD_TO_CART = "ADD_TO_CART";
const REMOVE_FROM_CART = "REMOVE_FROM_CART";
const CLEAR_CART = "CLEAR_CART";
const SET_CART = "SET_CART";

const cartReducer = (prevCart, action) => {
    switch (action.type) {
        case ADD_TO_CART:
            const { product, quantity } = action.payload;
            const existingItem = prevCart.find((item) => item.productId === product.productId);
            if (existingItem) {
                return prevCart.map((item) =>
                    item.productId === product.productId
                        ? { ...item, quantity: item.quantity + quantity }
                        : item
                );
            }
            return [...prevCart, { ...product, quantity }];
        case REMOVE_FROM_CART:
            return prevCart.filter((item) => item.productId !== action.payload.productId);
        case CLEAR_CART:
            return [];
        case SET_CART:
            return action.payload;
        default:
            return prevCart;
    }
};

export const CartProvider = ({ children }) => {
    const [cart, dispatch] = useReducer(cartReducer, []);
    const { isAuthenticated, jwtToken, isTokenExpired } = useAuth();

    // Fetch cart from backend only when user logs in
    useEffect(() => {
        if (isAuthenticated && jwtToken && !isTokenExpired(jwtToken)) {
            cartService.getCart()
                .then((res) => {
                    const items = res.data.map((item) => ({
                        ...item,
                        price: parseFloat(item.price),
                        imageUrl: imageMap[item.imageUrl] || item.imageUrl,
                    }));
                    dispatch({ type: SET_CART, payload: items });
                })
                .catch(() => dispatch({ type: CLEAR_CART }));
        } else {
            dispatch({ type: CLEAR_CART });
        }
    }, [isAuthenticated]);

    // Clear cart immediately when token expires
    useEffect(() => {
        const handler = () => dispatch({ type: CLEAR_CART });
        window.addEventListener("token-expired", handler);
        return () => window.removeEventListener("token-expired", handler);
    }, []);

    const addToCart = async (product, quantity) => {
        dispatch({ type: ADD_TO_CART, payload: { product, quantity } });
        try {
            await cartService.addToCart(product.productId, quantity);
        } catch (err) {
            console.error("Failed to add to cart:", err);
        }
    };

    const removeFromCart = async (productId) => {
        dispatch({ type: REMOVE_FROM_CART, payload: { productId } });
        try {
            await cartService.removeFromCart(productId);
        } catch (err) {
            console.error("Failed to remove from cart:", err);
        }
    };

    const clearCart = async () => {
        dispatch({ type: CLEAR_CART });
        try {
            await cartService.clearCart();
        } catch (err) {
            console.error("Failed to clear cart:", err);
        }
    };

    const totalQuantity = cart.reduce((acc, item) => acc + item.quantity, 0);

    return (
        <CartContext.Provider value={{ cart, addToCart, removeFromCart, clearCart, totalQuantity }}>
            {children}
        </CartContext.Provider>
    );
};
