import React from "react";
import { Menu } from "lucide-react"; // Sidebar icon
import { Button } from "@/components/ui/button";

import {Avatar,AvatarFallback,AvatarImage} from "@/components/ui/avatar";
import {AlignJustify} from "lucide-react"
import { useSidebar } from "../../../content/hooks/useSidebar";

// const Header = () => {
//   return (
//     <header className="relative w-full bg-white shadow-sm flex items-center justify-between px-6 py-3 border-b">
//       {/* Left Section */}
//       <div className="flex items-center gap-3">
//       </div>

//       {/* Center Section (Horizontally Centered) */}
//       <div className="absolute left-1/2 transform -translate-x-1/2 text-center">
//         <h1 className="text-lg font-semibold">POS Terminal</h1>
//         <p className="text-sm text-gray-500">Create new order</p>
//       </div>

//       {/* Right Section */}
//       <div className="text-sm text-gray-500">
//         <span className="font-medium">Shortcuts:</span>
//         <span className="ml-2">F1: Search</span> | 
//         <span className="ml-2">F2: Discount</span> | 
//         <span className="ml-2">F3: Customer</span> | 
//         <span className="ml-2">Ctrl + Enter: Payment</span>
//       </div>
//     </header>
//   );
// };


const Header=()=>{
  const {setSidebarOpen}=useSidebar();
  return(
    <div className="bg-card border-b px-6 py-4">
      <div className="flex items-center justify-between">
        <div>
          <Button onClick={()=>setSidebarOpen(true)}>
            <AlignJustify/>

          </Button>
        </div>
        <div>
          <h1 className="text-2xl font-bold text-foreground">POS Terminal</h1>
          <p className="text-sm text-muted-foreground">create new order</p>
        </div>
      </div>
    </div>
  )
}
export default Header;
