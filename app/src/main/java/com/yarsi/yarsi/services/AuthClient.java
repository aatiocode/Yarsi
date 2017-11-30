package com.yarsi.yarsi.services;

import com.yarsi.yarsi.model.AuthPojo;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by Y700FROG on 30/11/2017.
 */

public interface AuthClient {

    @Multipart
    @POST("token")
    Call<AuthPojo> login(@Part("username") RequestBody username, @Part("password") RequestBody password, @Part("grant_type") RequestBody grant_type);

//    Call<AuthPojo> login(@Header("Authorization") String auth, @Body Login login);

    @GET("secretinfo")
    Call<ResponseBody> getSecret(@Header("Authorization") String authToken);
}
