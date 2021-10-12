package com.example.ezcommerce.Interface;

import com.example.ezcommerce.Model.Data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Api {
    String BASE_URL = "https://u73olh7vwg.execute-api.ap-northeast-2.amazonaws.com/";

    @GET("staging/book?nim=2201793804&nama=Helen Castello")
    Call<Data> getData();

    @GET("staging/book/{id}/?nim=2201793804&nama=Helen Castello")
    Call<Data> getDetailById(@Path("id") int id);


}
