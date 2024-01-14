package com.ifty.gitHubApiResponse.view.components

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(private val packageName: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val modifiedRequest = originalRequest.newBuilder()
            .header("package", packageName)
            .build()

        return chain.proceed(modifiedRequest)
    }
}
