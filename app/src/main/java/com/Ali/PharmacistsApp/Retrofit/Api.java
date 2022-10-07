package com.Ali.PharmacistsApp.Retrofit;

import com.Ali.PharmacistsApp.Model.Banner;
import com.Ali.PharmacistsApp.Model.Comment;
import com.Ali.PharmacistsApp.Model.Company;
import com.Ali.PharmacistsApp.Model.Image;
import com.Ali.PharmacistsApp.Model.Menu;
import com.Ali.PharmacistsApp.Model.Notifications;
import com.Ali.PharmacistsApp.Model.Order;
import com.Ali.PharmacistsApp.Model.Product;
import com.Ali.PharmacistsApp.Model.ProductWithCompany;
import com.Ali.PharmacistsApp.Model.User;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Api {
    @FormUrlEncoded
    @POST("api/register")
    Call<User> register(
            @Field("email") String email,
            @Field("password") String password,
            @Field("name") String name);

    @Multipart
    @POST("api/updateProfile")
    Call<Image> uploadFile(@Part MultipartBody.Part email, @Part MultipartBody.Part file);


    @FormUrlEncoded
    @POST("api/login")
    Call<User> login(
            @Field("email") String email,
            @Field("password") String password);


    @GET("api/company")
    Observable<List<Company>> getCompany();

    @FormUrlEncoded
    @POST("api/menu")
    Observable<List<Menu>> getMenuByCompanyID(
            @Field("company_id") float company_id);

    @FormUrlEncoded
    @POST("api/product")
    Observable<List<Product>> getProductByMenuID(
            @Field("menu_id") float menuId,
            @Field("company_id") float companyId);

    @FormUrlEncoded
    @POST("api/update_profile")
    Call<String> update_profile(
            @Field("id") int id,
            @Field("image") String image);

    @GET("api/banner")
    Observable<List<Banner>> getBanners();

    @FormUrlEncoded
    @POST("api/order")
    Call<Order> send_order(
            @Field("userEmail") String userEmail,
            @Field("storeId") String storeId,
            @Field("orderDetail") String orderDetail,
            @Field("orderStatus") int orderStatus,
            @Field("orderPrice") float orderPrice,
            @Field("orderComment") String orderComment,
            @Field("orderAddress") String orderAddress,
            @Field("paymentMethod") String paymentMethod);

    @FormUrlEncoded
    @POST("api/searchProduct")
    Observable<List<Product>> searchProduct(
            @Field("name") String name,
            @Field("menu_id") float menuId,
            @Field("company_id") float companyId);

   @FormUrlEncoded
    @POST("api/search")
    Observable<List<Product>> search(
            @Field("name") String name);


    @FormUrlEncoded
    @POST("api/getorder")
    Observable<List<Order>> getOrder(
            @Field("userEmail") String userEmail,
            @Field("orderStatus") String orderStatus);

    @FormUrlEncoded
    @POST("api/cancelorder")
    Call<String> cancel_order(
            @Field("orderId") String orderId,
            @Field("orderStatus") String orderStatus);

    @FormUrlEncoded
    @POST("api/update_info")
    Call<User> update_user_info(
            @Field("email") String email,
            @Field("password") String password,
            @Field("name") String name,
            @Field("new_email") String new_email);

    @FormUrlEncoded
    @POST("api/getToken")
    Call<User> getToken(
            @Field("id") String id);

    @FormUrlEncoded
    @POST("api/updateToken")
    Call<String> updateToken(
            @Field("email") String email,
            @Field("api_token") String api_token);

    @FormUrlEncoded
    @POST("api/notifications")
    Call<Notifications> notifications(
            @Field("userEmail") String userEmail,
            @Field("img") String img,
            @Field("to") String to,
            @Field("data") String data,
            @Field("orderName") String orderName,
            @Field("orderQty") String orderQty,
            @Field("orderPrice") String orderPrice);

    @FormUrlEncoded
    @POST("api/getNotificationsByTo")
    Observable<List<Notifications>> getNotificationsByTo(@Field("to") String to);

    @FormUrlEncoded
    @POST("api/comment")
    Call<String> addComment(
            @Field("user_id") String user_id,
            @Field("product_id") String product_id,
            @Field("value") String value,
            @Field("comment") String comment,
            @Field("name") String name);

    @FormUrlEncoded
    @POST("api/getComment")
    Observable<List<Comment>> getComment(@Field("product_id") String product_id);

    @FormUrlEncoded
    @POST("api/getProductRate")
    Call<String> getProductRate(@Field("product_id") String product_id);

    @GET("api/getProductHighRate")
    Observable<List<Product>> getProductHighRate();

    @FormUrlEncoded
    @POST("api/qty_product")
    Call<String> check_quantity_product(
            @Field("id") int id,
            @Field("qty") int qty);
}
