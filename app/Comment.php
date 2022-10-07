<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Comment extends Model
{
    protected $table="comment";
    protected $fillable = ['id', 'user_id','product_id','value' ,'name','comment'];
}
