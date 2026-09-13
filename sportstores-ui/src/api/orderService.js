import apiClient from "./apiClient";

const orderService = {
    placeOrder: (orderData) => apiClient.post("/orders", orderData),
    getOrderHistory: () => apiClient.get("/orders"),
};

export default orderService;