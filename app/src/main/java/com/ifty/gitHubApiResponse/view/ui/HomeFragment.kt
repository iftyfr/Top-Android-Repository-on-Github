package com.ifty.gitHubApiResponse.view.ui

import RepositoryAdapter
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ifty.gitHubApiResponse.R
import com.ifty.gitHubApiResponse.ViewModel.MainViewModel
import com.ifty.gitHubApiResponse.databinding.FragmentHomeBinding
import com.ifty.gitHubApiResponse.model.GitHubApiResponse
import com.ifty.gitHubApiResponse.model.Items
import com.ifty.gitHubApiResponse.view.components.ProgressNew
import com.ifty.gitHubApiResponse.view.components.UIState


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var viewModel: MainViewModel

    private var progressBarHandlerNew: Dialog? = null

    private lateinit var repositoryAdapter: RepositoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)


        progressBarHandlerNew = ProgressNew(requireContext()).progressDialog()

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]


        viewModel.getRepositories()

        viewModel.dataState.observe(this) { state ->
            when (state) {
                is UIState.Loading -> showLoading()
                is UIState.Success -> showDataDetails(state.data)
                is UIState.Error -> showError(state.exception)
            }
        }

        repositoryAdapter = RepositoryAdapter { onRepositoryItemClick(it) }
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = repositoryAdapter

        return binding.root
    }


    private fun showLoading() {
        progressBarHandlerNew?.show()
    }

    private fun showDataDetails(gitHubApiResponse: GitHubApiResponse) {
        progressBarHandlerNew?.dismiss()
        Log.d("showDetailsTAG", "showUserDetails: $gitHubApiResponse")

        val repositories = gitHubApiResponse.items ?: emptyList<Items>()
        repositoryAdapter.setRepositories(repositories)

    }

    private fun showError(exception: Exception) {
        progressBarHandlerNew?.dismiss()
    }


    override fun onDestroy() {
        super.onDestroy()
        viewModel.cancelJobs()
    }

    private fun onRepositoryItemClick(repository: Items) {
        val bundle = Bundle()
        bundle.putSerializable("repository", repository)
        findNavController().navigate(R.id.action_homeFragment_to_detailsFragment,bundle)


    }

}