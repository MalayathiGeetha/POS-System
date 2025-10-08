
import './App.css'
import {Button} from './components/ui/button'

import {Route,Routes} from 'react-router'
import CashierRoutes from './routes/CashierRoutes'
function App() {
  
  return (
    <>
      {/* <CreateOrder/> */}
      {/* <CustomerLookup/> */}

      {/* <ShiftSummaryPage/> */}
      {/* <OrderHistory/> */}
      {/* <RefundPage/> */}
      <Routes>
        <Route path='/cashier/*' element={<CashierRoutes/>}/>
      </Routes>

    
    </>
  )
}

export default App
