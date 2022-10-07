<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;


use App\company;

class companyController extends Controller
{
    function company(){
        $index=company::all();
        return $index;
    }
    function companyName(Request $request){
        return company::where("id",$request->id)->get();
    }

    function companyproduct(){
        return DB::table('company')
        ->join('product', 'company.id', '=', 'product.company_id')
        ->select('company.name as company_name','product.id')
        ->get();
    }

    function getCompanyNameByEmail(Request $request){
        $company=Company::where("email",$request->email)->first();
        if($company==null){
            return response()->json([
                'success' => 'You should Add Store'
            ]);
        }else
        return $company;
    }

    public function uploadCompanyImage(Request $request){
        $image=$request->image->getClientOriginalName();
        $image=date('Hs').rand(1,999).'-'.$image;
        $request->image->storeAs('public/Pharmacy/Company',$image);

        return response()->json([
            'success' => 'uploaded successfully',
            'path' => 'http://192.168.137.1:8000/storage/Pharmacy/Company/'.$image
        ]);
    }

    public function add_company(Request $request){
        $company=new company();
        $company->img   = $request->img;
        $company->name   = $request->name;
        $company->email   = $request->email;
        $company->save();
        echo json_encode("Added Successfully");
    }

}
