import {EyeIcon} from "lucide-react"
import { Printer } from "lucide-react";

import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";

import {Button} from '@/components/ui/button'
const orders=[
    {
        id:1,
        createdAt:"Oct 8,2025, 12:37 PM",
        customer:{
            fullName:"Pablo panday",
            phone:"1234567890"
        },
        totalAmount:2499,
        paymentType:"CASH",
        status:"COMPLETED",
        items:[
            {
                id:2,
                product:{
                    image:"https://rukminim2.flixcart.com/image/612/612/xif0q/t-shirt/9/p/n/l-men-temu-polo-001-rizim-original-imahbdjcyxfqzwtm.jpeg?q=70",
                    name:"Men Geometric Print",
                    sellingPrice:499,
                    sku:"SHRT-S-COTTON-BLU-2025"
                },
                quantity:2,
            }
        ]
    }
]
const OrderTable=({handleSelectOrder})=>{
    return(
        <div>  
            <Table>
                            <TableHeader>
                                <TableRow>
                                    <TableHead className="">Order ID</TableHead>
                                    <TableHead className="">Date/Time</TableHead>
                                    <TableHead className="">Customer</TableHead>
                                    <TableHead className="">Amount</TableHead>
                                    <TableHead className="">Payment Type</TableHead>
                                    <TableHead className="">Status</TableHead>
                                    <TableHead className="text-right">Action</TableHead>
                                </TableRow>
            
                            </TableHeader>
                            <TableBody>
                                {orders.map((order)=>(
                                    <TableRow key={order.id}>
                                        <TableCell>{order.id}</TableCell>
                                        <TableCell>{order.createdAt}</TableCell>
                                        <TableCell>{order.customer?.fullName}</TableCell>
                                        <TableCell>{order.totalAmount}</TableCell>
                                        <TableCell>{order.paymentType}</TableCell>
                                        <TableCell>{order.status}</TableCell>
                                        <TableCell className="text-right">
                                                <Button onClick={()=>handleSelectOrder(order)} >
                                                    select for return
                                                </Button>
                                        </TableCell>
                                    </TableRow>
                                ))}
                            </TableBody>
                        </Table>
        </div>
    )
}

export default OrderTable