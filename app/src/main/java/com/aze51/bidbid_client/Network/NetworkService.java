package com.aze51.bidbid_client.Network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Leekh on 2016-06-29.
 */
public interface NetworkService {

    @POST("content/join")
    Call<Join> newMember(@Body Join join);
    @POST("content/login")
    Call<Login> getMember(@Body Login login);
    @GET("content/product")
    Call<List<Product>> getContents();
    @GET("content/check_id/{user_id}")
    Call<String> getID(@Path("user_id") String user_id);
    @GET("content/detail/{product_id}")
    Call<Product> getContent(@Path("product_id") long id);
    @GET("certify/{phonenum}")
    Call<String> getPhoneCertification(@Path("phonenum") long phoneNum);

}
