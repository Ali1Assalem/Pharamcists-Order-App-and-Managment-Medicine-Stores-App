<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;


use App\Notifications;

class notificationsController extends Controller
{
    function notifications(Request $request){
    
        $notifications=new Notifications();
        $notifications->userEmail      = $request->userEmail;
        $notifications->to             = $request->to;
        $notifications->img            = $request->img;
        $notifications->data           = $request->data;
        $notifications->orderName      = $request->orderName;
        $notifications->orderQty       = $request->orderQty;
        $notifications->orderPrice     = $request->orderPrice;

        $notifications->save();
        return $notifications;
        
    }

    function getNotificationsByTo(Request $request){
        return notifications::where("to",$request->to)->get();
     }




}