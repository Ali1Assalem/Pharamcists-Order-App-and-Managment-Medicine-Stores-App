<?php

namespace App\Http\Controllers;
use Illuminate\Support\Facades\DB;

use Illuminate\Http\Request;
use App\Order;
use App\User;

class orderController extends Controller
{
    function order(Request $request){
    
            $order=new Order();
            $order->orderStatus   = $request->orderStatus;
            $order->orderDetail   = $request->orderDetail;
            $order->orderPrice    = $request->orderPrice;
            $order->orderComment  = $request->orderComment;
            $order->orderAddress  = $request->orderAddress;
            $order->userEmail     = $request->userEmail;
            $order->paymentMethod = $request->paymentMethod;
            $order->storeId       = $request->storeId;

            $order->save();
            return $order;
            
        }


     function getOrderByStatues(Request $request){
        return order::where("orderStatus",$request->orderStatus)->where("userEmail",$request->userEmail)->get();
     }

     function getImageByEmail(Request $request){
      $user=User::where("email",$request->email)->first();
      if($user->image != null){
         $image = $user->image;
         return $user;
      }
   }

     function cancelOrder(Request $request){
       DB::table('Order')
      ->where('orderId', $request->orderId)  
      ->limit(1) 
      ->update(array('orderStatus' => $request->orderStatus));
        
      echo json_encode( 'Cancelled Successfully..');
   }

   function getOrderByStatueswithEmail(Request $request){
      return order::where("orderStatus",$request->orderStatus)->where("storeId",$request->storeId)->get();
   }

   function update_statues(Request $request){
      Order::where("orderId",$request->orderId)
       ->update(array(
          'orderStatus' => $request->orderStatus)); 

      echo json_encode( 'Updated Successfully..');    
   }
}
