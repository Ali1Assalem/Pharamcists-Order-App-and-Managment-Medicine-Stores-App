<?php

namespace App\Http\Controllers;
use App\Http\Controllers\Controller;
use Illuminate\Support\Str;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Hash;

use App\User;
use App\company;


class loginController extends Controller
{
    public function index(Request $request){
        if(Auth::attempt(['email'=>$request->input('email'),
        'password'=>$request->input('password')])){
         
            $user=auth()->user();
            $user->api_token='';
            $user->save();
            return $user;   
        }
        else{ return response()->json([
            'success' => 'Wrong data' ]);
        }

    }

    public function logout(){
        if(auth()->user()){
            $user=auth()->user();
            $user->api_token=null;
            $user->save();
        }
    }
/*
    public function update_profile(Request $request){
        $user=User::find($request->id);
        $file_name=time().'.'.$request->image->extension();
        $request->image->move(public_path('images'),$file_name);
        $path="public/images/$file_name";
        $user->image=$path;
        $user->update();
    }*/


   public function update_profile(Request $request){
        $user=User::find($request->id);

        $user->image=$request->image;
        $user->save();
        echo json_encode( "uploaded success");
    }

/*
    public function get_profile(Request $request){
        $user=User::find($request->id);
        return $user->image;
    }*/

        // this function saves user name,lastname and photo
        public function saveUserInfo(Request $request){
            $user = User::find($request->id);
            $user->name = $request->name;
            $photo = '';
            //check if user provided photo
            if($request->photo!=''){
                // user time for photo name to prevent name duplication
                $photo = time().'.jpg';
                // decode photo string and save to storage/profiles
                file_put_contents('storage/profile/'.$photo,base64_decode($request->photo));
                $user->image = $photo;
            }
    
            $user->update();
    
            return response()->json([
                'success' => true,
                'photo' => $photo
            ]);
           
        }
/*
        public function upload(Request $request){
            if(!$request->hasFile('image')) {
                return response()->json(['upload_file_not_found'], 400);
            }
            $file = $request->file('image');
            if(!$file->isValid()) {
                return response()->json(['invalid_file_upload'], 400);
            }
            $path = public_path() . '/uploads/images/';
            $file->move($path, $file->getClientOriginalName());
            return response()->json(compact('path'));
        } */

        public function upload(Request $request){
            $image=$request->image->getClientOriginalName();
            $image=date('Hs').rand(1,999).'-'.$image;
            $request->image->storeAs('public/Pharmacy/users_profiles',$image);

            User::where("email","like","%".$request->email."%")
            ->limit(1) 
            ->update(array('image' => 'users_profiles/'.$image));

            return response()->json([
                'success' => 'uploaded successfully',
                'path' => 'users_profiles/'.$image
            ]);
        }
            /* android path is http://192.168.137.1:8000/storage/Pharmacy/users_profiles/145514-download.jpg */



            
            
            public function update_user_info(Request $request){
            
             User::where("email","like","%".$request->email."%")
                ->limit(1) 
               ->update(array('name' => $request->name,
                              'email'=> $request->new_email,
                              'password' => Hash::make($request->password)));
                              

                              //return one object
                              //->get() return more rows
             $user=User::where("email",$request->new_email)->first();
             return $user;
        }
        

        function getToken(Request $request){
            $company=company::where("id",$request->id)->first();
            $email=$company->email;

            $user=User::where("email",$email)->first();
            return $user;
         }

         function getTokenByEmail(Request $request){
            $user=User::where("email",$request->email)->first();
            return $user;
         }

         public function updateToken(Request $request){
            
            User::where("email","like","%".$request->email."%")
               ->limit(1) 
              ->update(array('api_token' => $request->api_token));
                             
            $user=User::where("email",$request->email)->first();
            echo json_encode("token updated...");
       }
}

