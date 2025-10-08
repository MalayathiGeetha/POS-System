import React from 'react'
import CustomerCard from './CustomerCard'

const customers=[
    {id:1,fullName:'John Doe',email:'john@example.com',phone:'123-456-7890',loyaltyPoints:150,totalOrders:15,totalSpent:700,averageOrderValue:1160},
    {id:2,fullName:'Jane Smith',email:'janen@example.com',phone:'125-456-7890',loyaltyPoints:200,totalOrders:10,totalSpent:750,averageOrderValue:1565},
    {id:3,fullName:'Alice Johnson',email:'alice@example.com',phone:'343-456-7890',loyaltyPoints:250,totalOrders:20,totalSpent:650,averageOrderValue:1270},
    {id:4,fullName:'Bob Brown',email:'bob@example.com',phone:'897-456-7890',loyaltyPoints:300,totalOrders:5,totalSpent:150,averageOrderValue:1455},
]
const CustomerList = ({setSelectedCustomer}) => {
  return (
    <div className='flex-1 overflow-auto'>
        <div className='divide-y'>
            {customers.map(customer=>(<CustomerCard className="cursor-pointer hover:bg-accent"
             setSelectedCustomer={setSelectedCustomer}
                key={customer.id} customer={customer}/>))}
        </div>
      
    </div>
  )
}

export default CustomerList
