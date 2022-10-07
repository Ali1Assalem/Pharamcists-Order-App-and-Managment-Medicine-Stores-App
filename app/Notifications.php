<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Notifications extends Model
{
    protected $table="Notifications";
    protected $fillable = ['id', 'userEmail', 'img','to','data'
      ,'orderName','orderQty','orderPrice'];
}
