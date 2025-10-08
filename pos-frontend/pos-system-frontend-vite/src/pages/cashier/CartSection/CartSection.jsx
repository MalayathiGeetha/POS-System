import React from "react";
import { Button } from "@/components/ui/button";
import { Trash2, ShoppingCart } from "lucide-react";

const CartSection = ({ cartItems, clearCart }) => {
  // Calculate totals
  const subtotal = cartItems.reduce((acc, item) => acc + item.price * item.qty, 0);
  const tax = subtotal * 0.05; // Example: 5% tax
  const total = subtotal + tax;

  return (
    <div className="flex flex-col h-full p-4">
      {/* Header */}
      <div className="flex items-center justify-between mb-4 border-b pb-2">
        <h2 className="text-lg font-semibold flex items-center gap-2">
          <ShoppingCart className="w-5 h-5 text-gray-600" /> Cart
        </h2>
        <Button
          variant="destructive"
          size="sm"
          onClick={clearCart}
          disabled={cartItems.length === 0}
        >
          <Trash2 className="w-4 h-4 mr-1" /> Clear
        </Button>
      </div>

      {/* Cart Items */}
      <div className="flex-1 overflow-y-auto space-y-3">
        {cartItems.length === 0 ? (
          <p className="text-gray-500 text-center mt-10">No items in cart</p>
        ) : (
          cartItems.map((item, index) => (
            <div
              key={index}
              className="flex justify-between items-center border rounded-lg p-3"
            >
              <div>
                <p className="font-medium">{item.name}</p>
                <p className="text-sm text-gray-500">Qty: {item.qty}</p>
              </div>
              <p className="font-semibold">₹{item.price * item.qty}</p>
            </div>
          ))
        )}
      </div>

      {/* Totals Section */}
      <div className="border-t pt-3 mt-4 space-y-2">
        <div className="flex justify-between text-sm text-gray-600">
          <span>Subtotal:</span>
          <span>₹{subtotal.toFixed(2)}</span>
        </div>
        <div className="flex justify-between text-sm text-gray-600">
          <span>Tax (5%):</span>
          <span>₹{tax.toFixed(2)}</span>
        </div>
        <div className="flex justify-between text-lg font-semibold border-t pt-2">
          <span>Total:</span>
          <span>₹{total.toFixed(2)}</span>
        </div>

        <Button
          className="w-full mt-4"
          disabled={cartItems.length === 0}
        >
          Proceed to Checkout
        </Button>
      </div>
    </div>
  );
};

export default CartSection;
