<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class product extends Model
{
    protected $table="product";
    protected $fillable = ['id', 'name', 'img','price','rate','qty','menu_id','company_id'];
}
