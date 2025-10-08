import { useState } from 'react'
import Sidebar from './sidebar/Sidebar'
import { ReceiptIcon } from "lucide-react"
import {RotateCcw } from "lucide-react"
import {Clock} from 'lucide-react'
import {ShoppingCart} from 'lucide-react'
import { Outlet } from "react-router-dom";
import { useSidebar } from '../../content/hooks/useSidebar'
import { SidebarProvider } from '../../content/SidebarProvider'
const navItems=[
    {
        path:"/cashier",
        icon:<ShoppingCart size={20}/>,
        label:"POS Terminal"
    },
    {
        path:"/cashier/orders",
        icon:<Clock size={20}/>,
        label:"Order History"
    },    
    {
        path:"/cashier/returns",
        icon:<RotateCcw size={20}/>,
        label:"Returns/Refunds"
    },
    {
        path:"/cashier/shift-summary",
        icon:<ReceiptIcon size={20}/>,
        label:"Shift Summary"
    }
]
const LayoutContent=()=>{
     const {sidebarOpen,setSidebarOpen}=useSidebar();
    return(
        <div className="flex h-screen bg-background">

            {sidebarOpen && <div className="fixed inset-0 bg-black/40">
            </div>}

            <div className={`fixed z-30 h-full transition-transform duration-200 ${sidebarOpen?"translate-x-0":"-translate-x-full"} `}>
                <Sidebar navItems={navItems} onClose={()=>setSidebarOpen(false)}/>
            </div>

            <div className="flex-1 overflow-auto">
                <Outlet/>
            </div>

        </div>
    )
}



const CashierDashboardLayout = () => {
  return (
    <SidebarProvider>
        <LayoutContent/>
    </SidebarProvider>
  )
}

export default  CashierDashboardLayout;