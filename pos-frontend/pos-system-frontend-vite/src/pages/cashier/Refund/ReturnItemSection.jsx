import { Label } from "@/components/ui/label";
import {Textarea} from "@/components/ui/textarea"
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import {useState} from 'react';
import {Button} from '@/components/ui/button'
import {Card,CardContent} from '@/components/ui/card'


const refundMethods=["UPI","CARD","CASH"]
const returnReason1=["Wrong Product","Damage Product","Not Interested anymore","other"]
const ReturnItemSection=({selectOrder,setShowReturnReceiptDialog})=>{
    const [returnReason,setReturnReason]=useState("");
    const [otherReason,setOtherReason]=useState("");
    const [refundMethod,setRefundMethod]=useState("UPI")
    const processRefund=()=>{
        setShowReturnReceiptDialog(true)
    }
    return(

        <div className='p-4 w-1/2'>
            <Card className={"mt-4"}>
                <CardContent className={"p-4"}>
                    <div className='space-y-4'>
                        <div>
                            <Label className={"mb-2 block"}>
                                Return Reason
                            </Label>
                            <Select value={returnReason} onValueChange={(value)=>setReturnReason(value)}>
                                <SelectTrigger className={"w-full "}>
                                    <SelectValue placeholder="Select A Reason..."/>
                                </SelectTrigger>
                                <SelectContent>
                                    {returnReason1.map((reason)=>(<SelectItem value={reason} key={reason}>{reason} </SelectItem>))}
                                </SelectContent>

                            </Select>
                        </div>
                        {
                            returnReason=="other" && (<div>
                                <Label className={"mb-2 block"}>Specify Reason</Label>
                                <Textarea id="other-reason" placeholder="please specify the return reason" value={otherReason} onValueChange={(value)=>setOtherReason(value)}/>
                            </div>)
                        }
                        <div> 
                            <Label>
                                Refund Method
                            </Label>
                            <Select value={refundMethod} onValueChange={(value)=>setRefundMethod(value)}>
                                <SelectTrigger className={"w-full "}>
                                    <SelectValue placeholder="Select a Refund Method..."/>
                                </SelectTrigger>
                                <SelectContent>
                                    {refundMethods.map((method)=>(<SelectItem value={method} key={method}>{method} </SelectItem>))}
                                </SelectContent>

                            </Select>
                        </div>

                        <div className="pt-5 border-t">
                            <div className="flex justify-baseline text-lg font-semibold">
                                <span>Total Refund Amount : </span>
                                <span>${selectOrder.totalAmount}</span>
                            </div>
                            <Button onClick={processRefund} className="w-full py-6 mt-5">
                                Process Refund
                            </Button>
                        </div>
                    </div>
                </CardContent>
            </Card>

        </div>
    )
}

export default ReturnItemSection