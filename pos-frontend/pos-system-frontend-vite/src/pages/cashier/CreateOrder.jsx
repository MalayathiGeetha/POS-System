// import Header from "./Header/Header";
// import { useState } from "react";
// import { Button } from "@/components/ui/button";
// import {
//   ShoppingCart,
//   Search,
//   Percent,
//   IndianRupee,
//   Menu,
//   X,
//   History,
//   RotateCcw,
//   Users,
//   FileText,
// } from "lucide-react";
// import { motion } from "framer-motion";

// const sampleProducts = [
//   {
//     id: 1,
//     name: "Men Geometric Print T-Shirt",
//     price: 599,
//     image:
//       "https://rukminim2.flixcart.com/image/612/612/xif0q/t-shirt/9/p/n/l-men-temu-polo-001-rizim-original-imahbdjcyxfqzwtm.jpeg?q=70",
//     category: "shirt",
//   },
//   {
//     id: 2,
//     name: "Men Slim Fit Checked Shirt",
//     price: 399,
//     image:
//       "https://rukminim2.flixcart.com/image/612/612/xif0q/t-shirt/9/p/n/l-men-temu-polo-001-rizim-original-imahbdjcyxfqzwtm.jpeg?q=70",
//     category: "t-shirt",
//   },
// ];

// const CreateOrder = () => {
//   const [cartItems, setCartItems] = useState([]);
//   const [searchTerm, setSearchTerm] = useState("");
//   const [isSidebarOpen, setIsSidebarOpen] = useState(false);

//   const addToCart = (product) => {
//     if (!cartItems.some((item) => item.id === product.id)) {
//       setCartItems([...cartItems, product]);
//     }
//   };

//   const clearCart = () => setCartItems([]);
//   const total = cartItems.reduce((acc, item) => acc + item.price, 0);

//   const filteredProducts = sampleProducts.filter((p) =>
//     p.name.toLowerCase().includes(searchTerm.toLowerCase())
//   );

//   const menuItems = [
//     { name: "POS Terminal", icon: <ShoppingCart /> },
//     { name: "Order History", icon: <History /> },
//     { name: "Returns / Refunds", icon: <RotateCcw /> },
//     { name: "Customers", icon: <Users /> },
//     { name: "Shift Summary", icon: <FileText /> },
//   ];

//   return (
//     <div className="flex flex-col min-h-screen bg-gray-50">
//       {/* Header */}
//       <div className="flex items-center justify-between bg-white shadow p-4">
//         <button
//           onClick={() => setIsSidebarOpen(true)}
//           className="text-gray-700 hover:text-blue-600"
//         >
//           <Menu size={28} />
//         </button>
//         <Header title="POS Terminal" subtitle="Create new order" />
//         <div className="w-8" />
//       </div>

//       {/* Sidebar */}
//       <motion.aside
//         initial={{ x: "-100%" }}
//         animate={{ x: isSidebarOpen ? 0 : "-100%" }}
//         transition={{ duration: 0.3 }}
//         className="fixed top-0 left-0 h-full w-64 bg-white shadow-lg z-30 flex flex-col"
//       >
//         <div className="flex items-center justify-between p-4 border-b">
//           <h1 className="text-xl font-bold text-blue-600">POS System</h1>
//           <button
//             onClick={() => setIsSidebarOpen(false)}
//             className="text-gray-600 hover:text-red-500"
//           >
//             <X size={24} />
//           </button>
//         </div>

//         <nav className="flex flex-col p-4 gap-3 flex-1">
//           {menuItems.map((item) => (
//             <button
//               key={item.name}
//               className="flex items-center gap-3 px-4 py-2 text-left rounded-lg hover:bg-blue-100 transition-all"
//             >
//               {item.icon}
//               <span className="font-medium">{item.name}</span>
//             </button>
//           ))}
//         </nav>

//         <div className="p-4 border-t">
//           <div className="bg-gray-50 p-3 rounded-lg text-sm text-gray-700">
//             <p>
//               <strong>Branch Info</strong>
//             </p>
//             <p>
//               Name: <span className="font-medium">Surat east branch</span>
//             </p>
//             <p>
//               Address: <span className="font-medium">Ambawadi choke, near Ashoka complex</span>
//             </p>
//           </div>
//           <button className="mt-3 w-full bg-red-600 text-white py-2 rounded-lg flex items-center justify-center gap-2 hover:bg-red-700">
//             End Shift & Logout
//           </button>
//         </div>
//       </motion.aside>

//       {/* Overlay */}
//       {isSidebarOpen && (
//         <div
//           className="fixed inset-0 bg-black bg-opacity-30 z-20"
//           onClick={() => setIsSidebarOpen(false)}
//         />
//       )}

//       {/* Main Layout */}
//       <div className="flex flex-1">
//         {/* Left Section - Product List */}
//         <div className="w-2/3 p-6">
//           <div className="mb-4 flex items-center gap-2">
//             <input
//               type="text"
//               placeholder="Search products or scan barcode (F1)"
//               value={searchTerm}
//               onChange={(e) => setSearchTerm(e.target.value)}
//               className="w-full p-3 rounded-xl border border-gray-300"
//             />
//             <Button variant="outline">
//               <Search size={18} />
//             </Button>
//           </div>

//           <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4">
//             {filteredProducts.map((product) => (
//               <div
//                 key={product.id}
//                 onClick={() => addToCart(product)}
//                 className="bg-white rounded-2xl shadow-sm p-3 cursor-pointer hover:shadow-lg transition-all"
//               >
//                 <img
//                   src={product.image}
//                   alt={product.name}
//                   className="h-32 w-full object-cover rounded-xl"
//                 />
//                 <h3 className="font-semibold text-gray-700 mt-2 text-sm">
//                   {product.name}
//                 </h3>
//                 <div className="flex justify-between items-center mt-1">
//                   <span className="font-bold text-green-600">
//                     ₹{product.price}
//                   </span>
//                   <span className="text-gray-500 text-xs">
//                     {product.category}
//                   </span>
//                 </div>
//               </div>
//             ))}
//           </div>
//         </div>

//         {/* Right Section - Cart / Summary */}
//         <div className="w-1/3 bg-white border-l border-gray-200 p-6 flex flex-col">
//           <h2 className="text-lg font-semibold flex items-center gap-2 mb-3">
//             <ShoppingCart size={18} /> Cart ({cartItems.length} items)
//           </h2>

//           <div className="flex-1 overflow-y-auto">
//             {cartItems.length === 0 ? (
//               <div className="text-gray-400 text-center mt-10">
//                 Cart is empty <br />
//                 <span className="text-sm">Add products to start an order</span>
//               </div>
//             ) : (
//               cartItems.map((item) => (
//                 <div
//                   key={item.id}
//                   className="flex justify-between items-center py-2 border-b"
//                 >
//                   <span className="text-sm">{item.name}</span>
//                   <span className="font-semibold text-green-600">
//                     ₹{item.price}
//                   </span>
//                 </div>
//               ))
//             )}
//           </div>

//           <div className="mt-4 space-y-3">
//             <div>
//               <label className="text-sm text-gray-600">Discount</label>
//               <div className="flex gap-2 mt-1">
//                 <input
//                   type="number"
//                   placeholder="Discount amount"
//                   className="w-full p-2 border rounded-lg"
//                 />
//                 <Button variant="outline">
//                   <Percent size={16} />
//                 </Button>
//                 <Button variant="outline">
//                   <IndianRupee size={16} />
//                 </Button>
//               </div>
//             </div>

//             <div>
//               <label className="text-sm text-gray-600">Order Note</label>
//               <textarea
//                 placeholder="Add order note..."
//                 className="w-full p-2 border rounded-lg"
//               />
//             </div>
//           </div>

//           <div className="mt-5 border-t pt-3">
//             <div className="flex justify-between text-lg font-semibold">
//               <span>Total Amount</span>
//               <span className="text-green-600">₹{total}</span>
//             </div>

//             <div className="mt-4 flex gap-3">
//               <Button className="flex-1 bg-green-600 hover:bg-green-700 text-white">
//                 Process Payment
//               </Button>
//               <Button variant="outline" className="flex-1" onClick={clearCart}>
//                 Hold Order
//               </Button>
//             </div>
//           </div>
//         </div>
//       </div>
//     </div>
//   );
// };

// export default CreateOrder;
// import CartSection from './CartSection/CartSection'
// import CustomerPaymentSection from './CustomerPaymentSection/CustomerPaymentSection'
// import ProductSection from './ProductSection/ProductSection'
import Header from './Header/Header';


const CreateOrder=()=>{
    return(
        <div className='h-full flex flex-col bg-background'>
            <Header/>
            <div className='flex-1 flex overflow-hidden'>
                {/* <ProductSection/>
                <CartSection/>
                <CustomerPaymentSection/> */}
            </div>
        </div>
    )
}

export default CreateOrder