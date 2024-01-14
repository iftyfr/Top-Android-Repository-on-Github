package com.ifty.gitHubApiResponse.api

import com.ifty.gitHubApiResponse.model.GitHubApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {

    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") query: String,
        @Query("sort") sort: String
    ): Response<GitHubApiResponse>

}