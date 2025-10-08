import OrderTable from './OrderTable'
import OrderDetailsSection from './OrderDetailsSection'
import ReturnItemSection from './ReturnItemSection'
import ReturnReceiptDialog from './ReturnReceiptDialog'
import {useState} from "react"


const RefundPage=()=>{

    const [selectOrder,setSelectOrder]=useState(null);
    const [showReturnReceiptDialog,setShowReturnReceiptDialog]=useState(false)
    const handleSelectOrder=(order)=>setSelectOrder(order)
    return(
        <div className='h-full flex flex-col'>
            <div className='p-4 bg-card border-b'>
                <h1 className='text-2xl font-bolc'>Return/Refund</h1>

            </div>

            <div className='foverflow-hidden'>
                {!selectOrder? (<OrderTable handleSelectOrder={handleSelectOrder}/>):(<div className='flex'>
                    <OrderDetailsSection selectOrder={selectOrder} handleSelectOrder={handleSelectOrder}/>
                    <ReturnItemSection selectOrder={selectOrder} setShowReturnReceiptDialog={setShowReturnReceiptDialog}/>
                </div>) }
                
            </div>
            {selectOrder && <ReturnReceiptDialog showReturnReceiptDialog={showReturnReceiptDialog} setShowReturnReceiptDialog={setShowReturnReceiptDialog} selectOrder={selectOrder}/>}
        </div>
    )
}

export default RefundPage;