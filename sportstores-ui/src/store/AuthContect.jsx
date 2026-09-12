import { createContext, useEffect, useContext, useReducer, useCallback } from "react";
import { jwtDecode } from "jwt-decode";

export const AuthContext = createContext();

export const useAuth = () => useContext(AuthContext);

const LOGIN_SUCCESS = "LOGIN_SUCCESS";
const LOGOUT = "LOGOUT";

const authReducer = (prevState, action) => {
    switch (action.type) {
        case LOGIN_SUCCESS:
            return { ...prevState, jwtToken: action.payload.jwtToken, user: action.payload.user, isAuthenticated: true };
        case LOGOUT:
            return { ...prevState, jwtToken: null, user: null, isAuthenticated: false };
        default:
            return prevState;
    }
};

const isTokenExpired = (token) => {
    try {
        const { exp } = jwtDecode(token);
        return Date.now() >= exp * 1000;
    } catch {
        return true;
    }
};

export const AuthProvider = ({ children }) => {
    const initialAuthState = (() => {
        try {
            const jwtToken = localStorage.getItem("jwtToken");
            const user = localStorage.getItem("user");
            if (jwtToken && user && user !== "undefined" && !isTokenExpired(jwtToken)) {
                return { jwtToken, user: JSON.parse(user), isAuthenticated: true };
            }
        } catch (error) {
            console.error("Failed to load from localStorage:", error);
        }
        return { jwtToken: null, user: null, isAuthenticated: false };
    })();

    const [authState, dispatch] = useReducer(authReducer, initialAuthState);

    useEffect(() => {
        try {
            if (authState.isAuthenticated) {
                localStorage.setItem("jwtToken", authState.jwtToken);
                localStorage.setItem("user", JSON.stringify(authState.user));
            } else {
                localStorage.removeItem("jwtToken");
                localStorage.removeItem("user");
            }
        } catch (error) {
            console.error("Failed to save to localStorage:", error);
        }
    }, [authState]);

    const logout = useCallback(() => dispatch({ type: LOGOUT }), []);

    // Listen for token-expired event fired by axios interceptor
    useEffect(() => {
        const handler = () => logout();
        window.addEventListener("token-expired", handler);
        return () => window.removeEventListener("token-expired", handler);
    }, [logout]);

    const loginSuccess = (jwtToken, user) => {
        dispatch({ type: LOGIN_SUCCESS, payload: { jwtToken, user } });
    };

    return (
        <AuthContext.Provider value={{ jwtToken: authState.jwtToken, user: authState.user, isAuthenticated: authState.isAuthenticated, loginSuccess, logout, isTokenExpired }}>
            {children}
        </AuthContext.Provider>
    );
};