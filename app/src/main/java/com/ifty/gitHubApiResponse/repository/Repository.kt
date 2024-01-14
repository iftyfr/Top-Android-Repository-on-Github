package com.ifty.gitHubApiResponse.repository

import com.ifty.gitHubApiResponse.BuildConfig
import com.ifty.gitHubApiResponse.api.MyRetrofitBuilder.apiService
import com.ifty.gitHubApiResponse.model.GitHubApiResponse
import com.ifty.gitHubApiResponse.view.components.UIState
import kotlinx.coroutines.*

object Repository {

    var job: CompletableJob? = null


    suspend fun getRepositories(): UIState<GitHubApiResponse> {
        val job = Job()
        return withContext(Dispatchers.IO + job) {
            try {
                val response = apiService.searchRepositories("android", "stars")
                if (response.isSuccessful) {
                    UIState.Success(response.body()!!)
                } else {
                    UIState.Error(Exception("Error getting data"))
                }
            } catch (e: Exception) {
                UIState.Error(e)
            }finally {
                job.complete()
            }
        }
    }

    fun cancelJobs(){
        job?.cancel()
    }

}
















