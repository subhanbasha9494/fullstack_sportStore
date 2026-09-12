import apiClient from "./apiClient";

const cartService = {
    getCart: () => apiClient.get("/cart"),
    addToCart: (productId, quantity) => apiClient.post("/cart", { productId, quantity }),
    removeFromCart: (productId) => apiClient.delete(`/cart/${productId}`),
    clearCart: () => apiClient.delete("/cart"),
};

export default cartService;
