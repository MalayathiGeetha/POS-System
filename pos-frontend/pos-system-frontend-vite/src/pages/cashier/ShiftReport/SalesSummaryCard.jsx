import React from 'react'
import {
  Card,
  CardAction,
  CardContent,
} from "@/components/ui/card"


const shiftData={
    cashier:{
        fullName:"Pablo"
    },
    shiftStart:"Aug 8, 2025, 09:34 AM",
    shiftEnd:"",
    totalOrders:59,
    totalSales:6999,
    totalRefunds:2199,
    netSales:48000
    
}

const SalesSummaryCard = () => {
  return (
    
        <Card>
                <CardContent>
                    <h2 className='text-xl font-semibold mb-4'>Sales Summary</h2>
                    <div className='space-y-2'>
                        <div className='flex justify-between'>
                            <span className='text-muted-foreground'>Total Orders: </span>
                            <span className='font-medium'>{shiftData.totalOrders}</span>
                        </div>
                        <div className='flex justify-between'>
                            <span className='text-muted-foreground'>Total Sales: </span>
                            <span className='font-medium'>₹{shiftData.totalSales}</span>
                        </div>
                        <div className='flex justify-between'>
                            <span className='text-muted-foreground'>Total Refunds: </span>
                            <span className='font-medium text-red-500'>-{shiftData.totalRefunds}</span>
                        </div>
                        <div className='flex justify-between border-t'>
                            <span className='text-muted-foreground '>Net Sales: </span>
                            <span className='font-medium'>₹{shiftData.netSales}</span>
                        </div>
                    </div>
        
                </CardContent>
            </Card>
      
    
  )
}

export default SalesSummaryCard
