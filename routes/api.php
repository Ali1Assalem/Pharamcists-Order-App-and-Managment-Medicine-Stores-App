<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

//Route::middleware('auth:api')->get('/user', function (Request $request) {
//    return $request->user();
//});
Route::post('register','registerController@register');
Route::post('login','loginController@index');
Route::post('logout','loginController@logout');
Route::get('company','companyController@company');
Route::get('banner','bannerController@banner');
Route::post('product','productController@product');
Route::post('searchProduct','productController@searchProduct');
Route::post('search','productController@search');
Route::post('menu','menuController@menu');
Route::post('update_profile','loginController@update_profile');
Route::post('get_profile','loginController@saveUserInfo');

Route::post('order','orderController@order');
Route::post('getorder','orderController@getOrderByStatues');
Route::post('cancelorder','orderController@cancelOrder');
Route::post('getImageByEmail','orderController@getImageByEmail');


Route::post('companyName','companyController@companyName');
Route::get('companyNameWithProductId','companyController@companyproduct');
Route::post('update_info','loginController@update_user_info');
Route::post('getCompanyNameByEmail','companyController@getCompanyNameByEmail');
Route::post('updateProfile','loginController@upload');

//Update Menu
Route::post('uploadMenuImage','menuController@uploadMenuImage');
Route::post('add_menu','menuController@add_menu');
Route::post('delete_menu','menuController@delete_menu');
Route::post('update_menu','menuController@update_menu');

//Update Product
Route::post('uploadProductImage','productController@uploadProductImage');
Route::post('add_product','productController@add_product');
Route::post('delete_product','productController@delete_product');
Route::post('update_product','productController@update_product');
Route::post('qty_product','productController@update_qty_product');


Route::post('getOrderByStatueswithEmail','orderController@getOrderByStatueswithEmail');
Route::post('update_statues','orderController@update_statues');

Route::post('uploadCompanyImage','companyController@uploadCompanyImage');
Route::post('add_company','companyController@add_company');

Route::post('getToken','loginController@getToken');
Route::post('getTokenByEmail','loginController@getTokenByEmail');
Route::post('updateToken','loginController@updateToken');

Route::post('notifications','notificationsController@notifications');
Route::post('getNotificationsByTo','notificationsController@getNotificationsByTo');

Route::post('comment','commentController@comment');
Route::post('getComment','commentController@getComment');
Route::post('getProductRate','commentController@getProductRate');
Route::get('getProductHighRate','commentController@getProductHighRate');



//php artisan serve --host=192.168.1.222
//php artisan serve --host=192.168.137.1 --port=8000