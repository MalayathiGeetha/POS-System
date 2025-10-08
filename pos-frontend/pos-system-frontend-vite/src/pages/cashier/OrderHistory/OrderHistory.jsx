import OrderTable from "./OrderTable";
import {
  Dialog,
  DialogHeader,
  DialogFooter,
  DialogContent,
  DialogTitle,
  DialogDescription,
} from "@/components/ui/dialog";
import OrderDetails from "./OrderDetails/OrderDetails";
import { Button } from "@/components/ui/button";
import { Download } from "lucide-react";
import { useState } from "react";

const OrderHistory = () => {
  const [showOrderInvoiceDialog, setShowOrderInvoiceDialog] = useState(false);
  const [selectedOrder, setSelectedOrder] = useState(null);

  const handleViewOrderDetails = (order) => {
    setSelectedOrder(order);
    setShowOrderInvoiceDialog(true);
  };

  return (
    <div className="h-full flex flex-col">
      <div className="flex-1 p-4 overflow-auto">
        <OrderTable handleViewOrderDetails={handleViewOrderDetails} />
      </div>

      <Dialog open={showOrderInvoiceDialog} onOpenChange={setShowOrderInvoiceDialog}>
        <DialogContent className="max-w-4xl">
          <DialogHeader>
            <DialogTitle>Order Invoice</DialogTitle>
            <DialogDescription>
              View detailed information for the selected order below.
            </DialogDescription>
          </DialogHeader>

          <OrderDetails selectedOrder={selectedOrder} />

          <DialogFooter>
            <Button>
              <Download className="mr-2 h-4 w-4" />
              Download PDF
            </Button>
          </DialogFooter>
        </DialogContent>
      </Dialog>
    </div>
  );
};

export default OrderHistory;
