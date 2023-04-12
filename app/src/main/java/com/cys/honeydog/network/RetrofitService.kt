package com.cys.honeydog.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface RetrofitService {

    //Retrofit 작업
    @Headers ("X-Naver-Client-Id: Ll1h5J47K1UoBmdujRuv","X-Naver-Client-Secret: M7gVhNxz2o")
    @GET("/v1/search/local.json?display=100")
    fun searchDataByJson(@Query ("query") query : String) :Call<RetRofitHospital>

}