package com.aze51.bidbid_client.Network;



import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
/**
 * Created by Leekh on 2016-06-29.
 */
public interface NetworkService {

    @POST("content/join")
    Call<Join> newMember(@Body Join join);

    @POST("content/login")
    Call<Login> getMember2(@Body Login login);

    @POST("sign/in")
    Call<Login> getMember(@Body Login login);

    @GET("content/product")
    Call<List<Product>> getContents();

    @GET("content/check_id/{user_id}")
    Call<String> getID(@Path("user_id") String user_id);

    @GET("content/detail/{product_id}")
    Call<Product> getContent(@Path("product_id") long id);

    // SMS 인증
    @GET("certify/{phonenum}")
    Call<String> getPhoneCertification(@Path("phonenum") long phoneNum);
    @POST("content/bid")
    Call<Auction> finishbid(@Body Auction auction);
    @GET("search/{inputContents}")
    Call<List<Product>> searchContents(@Path("inputContents") String intputContents);
    @GET("product")
    Call<List<List<Product>>> getProducts();
    @GET("userinfo/{user_id}")
    Call<List<Product>> getMyPage(@Path("user_id") String userId);
    // check session
    @GET("/sign")
    Call<User> getSession();
    // 로그아웃
    @GET("/sign/out")
    Call<User> logout();

}
