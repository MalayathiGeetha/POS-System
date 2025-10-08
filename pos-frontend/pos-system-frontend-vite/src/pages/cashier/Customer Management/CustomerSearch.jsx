import React, { useState } from 'react'
import {Input} from '@/components/ui/input'
import {Button} from '@/components/ui/button'
import {SearchIcon} from 'lucide-react'
import { PlusIcon } from "lucide-react";


const CustomerSearch = () => {
    const [showCustomerForm,setShowCustomerForm]=useState(false);
  return (
    <div className='p-4 border-b'>
        <div className="flex gap-2">
            <div className="relative flex-1">
                
                <Input placeholder='Search Customer...' type={"text"} className={"py-5"}/>

            </div>
            <Button onClick={()=>setShowCustomerForm(true)} className={"py-5"}><PlusIcon className='h-4 w-4 mr-2'/> Add New</Button>
        </div>
        {/* <CustomerForm showCustomerForm={showCustomerForm} setShowCustomerForm={setShowCustomerForm}/> */}
      
    </div>
  )
}

export default CustomerSearch
