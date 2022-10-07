package com.Ali.PharmacistsApp.Retrofit;


import com.Ali.PharmacistsApp.Model.DataMessage;
import com.Ali.PharmacistsApp.Model.MyResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IFCMService {
    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAAiuFpN0A:APA91bFxJdCt-5u0X7u1n4m3dP8lvM2DXHIJzMIPMgtcAFIvPEBcBp-bLErKXCqoOraX42wz0avSZKg1bMaFrO2CybpSuBmJdkvzlgc0ER0IeqLvsnvNSDC63ju8J9H3CnthaI8Z6lGS"
    })

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body DataMessage body);
}
