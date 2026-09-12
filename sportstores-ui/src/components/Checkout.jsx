import { useState } from "react";
import { loadStripe } from "@stripe/stripe-js";
import { Elements, CardNumberElement, CardExpiryElement, CardCvcElement, useStripe, useElements } from "@stripe/react-stripe-js";
import { useCart } from "../store/cartContext";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import PageTitle from "./PageTitle";

const stripePromise = loadStripe(import.meta.env.VITE_STRIPE_PUBLIC_KEY);

const cardElementOptions = {
    style: {
        base: {
            fontSize: "16px",
            color: "#e2e8f0",
            "::placeholder": { color: "#94a3b8" },
        },
        invalid: { color: "#f87171" },
    },
};

const CheckoutForm = ({ totalAmount }) => {
    const stripe = useStripe();
    const elements = useElements();
    const { clearCart } = useCart();
    const navigate = useNavigate();
    const [isProcessing, setIsProcessing] = useState(false);

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!stripe || !elements) return;

        setIsProcessing(true);
        const cardElement = elements.getElement(CardNumberElement);

        const { error, paymentMethod } = await stripe.createPaymentMethod({
            type: "card",
            card: cardElement,
        });

        if (error) {
            toast.error(error.message);
            setIsProcessing(false);
            return;
        }

        // Simulate successful payment (test mode)
        toast.success("Payment successful! Order placed.");
        await clearCart();
        navigate("/home");
    };

    const inputClass = "w-full px-4 py-3 rounded-md bg-gray-700 border border-gray-500 focus:outline-none focus:border-light text-lighter";

    return (
        <form onSubmit={handleSubmit} className="space-y-6">
            <p className="text-center text-light font-semibold text-lg">
                Amount to be charged: <span className="text-white">${totalAmount}</span>
            </p>

            <div>
                <label className="block text-light font-semibold mb-2">Card Number</label>
                <div className={inputClass}>
                    <CardNumberElement options={cardElementOptions} />
                </div>
            </div>

            <div>
                <label className="block text-light font-semibold mb-2">Expiry Date</label>
                <div className={inputClass}>
                    <CardExpiryElement options={cardElementOptions} />
                </div>
            </div>

            <div>
                <label className="block text-light font-semibold mb-2">CVC</label>
                <div className={inputClass}>
                    <CardCvcElement options={cardElementOptions} />
                </div>
            </div>

            <button
                type="submit"
                disabled={!stripe || isProcessing}
                className="w-full py-3 bg-light dark:bg-lighter text-black font-semibold text-lg rounded-md hover:opacity-90 transition"
            >
                {isProcessing ? "Processing..." : "Pay Now"}
            </button>
        </form>
    );
};

export default function Checkout() {
    const { cart } = useCart();
    const totalAmount = cart.reduce((acc, item) => acc + parseFloat(item.price || 0) * item.quantity, 0).toFixed(2);

    return (
        <div className="min-h-[852px] flex items-center justify-center font-primary dark:bg-darkbg">
            <div className="bg-gray-800 shadow-lg rounded-lg w-full max-w-md px-8 py-8">
                <PageTitle title="Complete Your Payment" />
                <Elements stripe={stripePromise}>
                    <CheckoutForm totalAmount={totalAmount} />
                </Elements>
            </div>
        </div>
    );
}
