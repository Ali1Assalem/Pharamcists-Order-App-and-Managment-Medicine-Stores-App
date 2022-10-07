<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\banner;

class bannerController extends Controller
{
    function banner(){
        $index=banner::all();
        return $index;
    }
}
