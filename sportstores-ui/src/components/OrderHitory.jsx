import { useState, useEffect } from "react";
import orderService from "../api/orderService";
import { toast } from "react-toastify";
import PageTitle from "./PageTitle";

export default function OrderHistory() {
    const [orders, setOrders] = useState([]);

    useEffect(() => {
        orderService.getOrderHistory()
            .then(res => setOrders(res.data))
            .catch(() => toast.error("Failed to load orders"));
    }, []);

    return (
        <div className="max-w-4xl mx-auto px-4 py-8">
            <PageTitle title="My Orders" />
            {orders.length === 0 ? (
                <p className="text-center text-gray-500">No orders yet.</p>
            ) : (
                orders.map(order => (
                    <div key={order.orderId} className="border rounded-md p-4 mb-4">
                        <div className="flex justify-between">
                            <span>Order #{order.orderId}</span>
                            <span className="text-green-500">{order.status}</span>
                        </div>
                        <div>Total: ${order.totalAmount}</div>
                        <div>Date: {new Date(order.createdAt).toLocaleDateString()}</div>
                        <ul className="mt-2">
                            {order.items.map(item => (
                                <li key={item.productId}>
                                    {item.productName} × {item.quantity} — ${item.price}
                                </li>
                            ))}
                        </ul>
                    </div>
                ))
            )}
        </div>
    );
}
