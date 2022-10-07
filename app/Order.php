<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Order extends Model
{
    protected $table="order";
    protected $fillable = ['orderId', 'orderStatus', 'orderDetail','orderPrice',
    'orderComment','orderAddress','userEmail','paymentMethod','companyId'];
}