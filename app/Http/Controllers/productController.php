<?php

namespace App\Http\Controllers;
use Illuminate\Support\Facades\DB;

use Illuminate\Http\Request;
use App\product;

class productController extends Controller
{
    function product(Request $request){

        return DB::table('product')
        ->select('product.*','company.name as company_name')
        ->join('company', 'company.id', '=', 'product.company_id')
        ->where(['product.menu_id'=>$request->menu_id,
        'product.company_id'=>$request->company_id])
        ->get();

    }

    /*

    $users = DB::table('orders')
            ->join('payments', 'orders.user_id', '=', 'payments.userID')
            ->where('orders.user_id', '2')
            ->where('payments.userID', '2')
            ->select('orders.*', 'payments.*')
            ->get();

    */

   
    function search(Request $request){

        return DB::table('product')
        ->select('product.*','company.name as company_name')
        ->join('company', 'company.id', '=', 'product.company_id')
        ->where("product.name","like","%".$request->name."%")
        ->get();

    }

    function searchProduct(Request $request){
        return product::where("name","like","%".$request->name."%")
        ->where("menu_id",$request->menu_id)
        ->where("company_id",$request->company_id)
        ->get();
    }

    public function uploadProductImage(Request $request){
        $image=$request->image->getClientOriginalName();
        $image=date('Hs').rand(1,999).'-'.$image;
        $request->image->storeAs('public/Pharmacy/Product',$image);

        return response()->json([
            'success' => 'uploaded successfully',
            'path' => 'http://192.168.137.1:8000/storage/Pharmacy/Product/'.$image
        ]);
    }
    
    public function add_product(Request $request){
        $product=new product();
        $product->img   = $request->img;
        $product->name   = $request->name;
        $product->price   = $request->price;
        $product->company_id    = $request->company_id;
        $product->menu_id    = $request->menu_id;
        $product->qty    = $request->qty;

        $product->save();
        echo json_encode("Added Successfully");
    }

    public function delete_product(Request $request){
        $product= product::where("id",$request->id)->first();
        $product->delete();
        echo json_encode("Deleted Successfully");
    }

    public function update_product(Request $request){
        product::where("id",$request->id)
       ->update(array('name' => $request->name,
                      'img'=> $request->img,
                      'price'=> $request->price,
                      'qty' => $request->qty,
                      'menu_id'=> $request->menu_id)); 
             
        echo json_encode("Updated Successfully");
    }

    public function update_qty_product(Request $request){
        $product= product::find($request->id);
        if($request->qty <= $product->qty && $request->qty !=0)
        {   
            $product->qty= $product->qty - $request->qty;
            $product->save();
            echo json_encode("Successfully");
        }else{
            echo json_encode("Sorry not enough products");
         }

        }
}