package com.ifty.gitHubApiResponse.api

import com.ifty.gitHubApiResponse.BuildConfig
import com.ifty.gitHubApiResponse.view.components.HeaderInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MyRetrofitBuilder {

    private const val BASE_URL: String = "https://api.github.com/"

    private val retrofitBuilder: Retrofit.Builder by lazy {
        val okHttpClient = createOkHttpClient(BuildConfig.APPLICATION_ID)
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiService: ApiService by lazy {

        retrofitBuilder
            .build()
            .create(ApiService::class.java)
    }

    private fun createOkHttpClient(packageName: String): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor(packageName))
            .build()
    }


}


