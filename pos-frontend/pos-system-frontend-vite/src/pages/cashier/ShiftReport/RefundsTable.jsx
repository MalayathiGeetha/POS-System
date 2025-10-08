import React from 'react'
import {
  Card,
  CardContent,
} from "@/components/ui/card"


import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";


const shiftData={
    refunds:[
        {
            id:234,
            orderId:2,
            reason:"wrond product received",
            amount:699
        }
    ]
}

const RefundsTable = () => {
  return (
    <Card>
            <CardContent>
                <h2 className="text-xl font-semibold mb-4">Refunds</h2>
                <Table>
                    <TableHeader>
                        <TableRow>
                            <TableHead className="w-[150px]">Refund ID</TableHead>
                            <TableHead className="w-[150px]">Order ID</TableHead>
                            <TableHead className="w-[150px]">Reason</TableHead>
                            <TableHead className="text-right">Amount</TableHead>
                        </TableRow>
    
                    </TableHeader>
                    <TableBody>
                        {shiftData.refunds.map((refund)=>(
                            <TableRow key={refund.id}>
                                <TableCell>RFD - {refund.id}</TableCell>
                                <TableCell>ORD - {refund.orderId}</TableCell>
                                <TableCell>{refund.reason}</TableCell>
                                <TableCell className="text-right">₹{refund.amount}</TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </CardContent>
          
        </Card>
  )
}

export default RefundsTable
