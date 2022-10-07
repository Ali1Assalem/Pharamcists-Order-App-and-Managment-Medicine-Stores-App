<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;

use App\User;
use App\Comment;
use App\product;
use App\company;

class commentController extends Controller
{
    function comment(Request $request){
    
        $comment=new Comment();
        $comment->user_id    = $request->user_id;
        $comment->name     = $request->name;
        $comment->value    = $request->value;
        $comment->comment  = $request->comment;
        $comment->product_id  = $request->product_id;
        $comment->save();

        $sum = Comment::where('product_id',$request->product_id)->sum('value');
        $count = Comment::where('product_id',$request->product_id)->count();
        $rate= $sum/$count;
        
        product::where("id",$request->product_id)
       ->update(array('rate' => $rate)); 

        echo json_encode( 'Comment Added Successfully..');

    }

    function getComment(Request $request){
     
        return DB::table('users')
        ->select('comment.*','users.image')
        ->join('comment', 'users.id', '=', 'comment.user_id')
        ->where(['comment.product_id'=>$request->product_id])
        ->get();
    }

    function getProductRate(Request $request){
        $product= product::where("id",$request->product_id)->first();
        if($product->rate!=null){
            return $product->rate;
        }else{
            echo json_encode("this product doesnt have rate yet...");
        }
    }

    function getProductHighRate(){
        return DB::table('product')
        ->select('product.*','company.name as company_name')
        ->join('company', 'company.id', '=', 'product.company_id')
        ->orderBy('rate', 'desc')->take(10)
        ->get();
    }
    
    /*


    DB::table('users')
    ->select('users.id','users.name','profiles.photo')
    ->join('profiles','profiles.id','=','users.id')
    ->where(['something' => 'something', 'otherThing' => 'otherThing'])
    ->get();


         $users = User::join('posts', 'users.id', '=', 'posts.user_id')
               ->get(['users.*', 'posts.descrption']);

         $deliveryCostTotal = Order::where('place_id',1)->sum('delivery_fees');

         */



}