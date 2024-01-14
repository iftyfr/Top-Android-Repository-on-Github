package com.ifty.gitHubApiResponse.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ifty.gitHubApiResponse.model.GitHubApiResponse
import com.ifty.gitHubApiResponse.view.components.UIState
import com.ifty.gitHubApiResponse.repository.Repository
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val _dataState = MutableLiveData<UIState<GitHubApiResponse>>()
    val dataState: LiveData<UIState<GitHubApiResponse>> get() = _dataState

    fun getRepositories() {
        viewModelScope.launch {
            _dataState.value = UIState.Loading
            _dataState.value = Repository.getRepositories()
        }
    }

    fun cancelJobs(){
        Repository.cancelJobs()
    }

}















