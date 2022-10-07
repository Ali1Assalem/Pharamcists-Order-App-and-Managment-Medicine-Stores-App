<?php

namespace App\Http\Controllers;
use Illuminate\Support\Facades\DB;
use Illuminate\Http\Request;
use App\Menu;

class menuController extends Controller
{
    function menu(Request $request){
        return Menu::where("company_id",$request->company_id)->get();
    }

    public function uploadMenuImage(Request $request){
        $image=$request->image->getClientOriginalName();
        $image=date('Hs').rand(1,999).'-'.$image;
        $request->image->storeAs('public/Pharmacy/Menu',$image);

        return response()->json([
            'success' => 'uploaded successfully',
            'path' => 'http://192.168.137.1:8000/storage/Pharmacy/Menu/'.$image
        ]);
    }

    public function add_menu(Request $request){
        $menu=new Menu();
        $menu->link   = $request->link;
        $menu->name   = $request->name;
        $menu->company_id    = $request->company_id;
        $menu->save();
        echo json_encode("Added Successfully");
    }


        public function delete_menu(Request $request){
        $menu= Menu::where("id",$request->id)->first();
        $menu->delete();
        echo json_encode("Deleted Successfully");
    }

    public function update_menu(Request $request){
        Menu::where("id",$request->id)
       ->update(array('name' => $request->name,
                      'link'=> $request->link )); 
             
        echo json_encode("Updated Successfully");
    }

}
