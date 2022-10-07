<?php

namespace App\Http\Controllers;

use App\User;
use Illuminate\Support\Facades\Validator;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Str;
class registerController extends Controller
{
    public function register(Request $request){

        
        $validator=Validator::make($request->all(),[
        'name'=>'required|max:191|string',
        'email'=>'required|max:191|unique:users|string',
        'password'=>'required|max:191|string'
        ]);

        if($validator->fails()){
            return response()->json([
                'success' => 'Wrong data' ]);
        }else{
            $data=User::create([
                'name'=>$request->name,
                'email'=>$request->email,
                'password'=>Hash::make($request->password),
                'api_token'=>''
            ]); 
            return $data; 
        
        }

    }
}
