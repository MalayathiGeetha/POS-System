import {Route,Routes} from 'react-router'
import  CashierDashboardLayout from '@/pages/cashier/CashierDashboardLayout'
import CreateOrder from '@/pages/cashier/CreateOrder'
import CustomerLookup from '@/pages/cashier/Customer Management/CustomerLookup'
import ShiftSummaryPage from '@/pages/cashier/ShiftReport/ShiftSummaryPage'
import OrderHistory from '@/pages/cashier/OrderHistory/OrderHistory'
import RefundPage from '@/pages/cashier/Refund/RefundPage'
import { SidebarProvider } from '../content/SidebarProvider'
const CashierRoutes=()=>{
    return(
        <Routes>

            <Route path='/' element={<CashierDashboardLayout/>}>
                <Route index element={<CreateOrder/>}/>
                <Route path='orders' element={<OrderHistory/>}/>
                <Route path='customers' element={<CustomerLookup/>}/>
                <Route path='returns' element={<RefundPage/>}/>
                <Route path='shift-summary' element={<ShiftSummaryPage/>}/>
            </Route>
        </Routes>
    )
}

export default CashierRoutes