package com.ifty.gitHubApiResponse.view.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ifty.gitHubApiResponse.R
import com.ifty.gitHubApiResponse.databinding.FragmentDetailsBinding
import com.ifty.gitHubApiResponse.model.Items

import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding

    private lateinit var repository: Items

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        repository = arguments?.getSerializable("repository") as Items


        displayRepositoryDetails(repository)
        Log.d("showDetailsTAG", "showDetails: $repository")
        return binding.root
    }

    private fun displayRepositoryDetails(repository: Items) {
        binding.tvRepoName.text = "Repository Name: ${repository.name}"
        binding.tvRepoOwner.text = "Owner Name: ${repository.owner?.login ?: "N/A"}"
        binding.tvDescription.text =
            "Description: ${repository.description ?: "No description available"}"
        binding.tvStarsCount.text = "Stars: ${repository.stargazersCount?.toString() ?: "0"}"
        binding.tvForksCount.text = "Forks: ${repository.forksCount?.toString() ?: "0"}"
        binding.tvLanguage.text = "Language: ${repository.language ?: "Unknown"}"

        binding.tvRepoUrl.text = "Repository URL: ${repository.url ?: "N/A"}"
        binding.tvOwnerUrl.text = "Owner URL: ${repository.owner?.htmlUrl ?: "N/A"}"

        val lastUpdateDateTime = repository.updatedAt
        val formattedDateTime = formatDateTime(lastUpdateDateTime)
        binding.tvLastUpdateDateTime.text = "Last Update: $formattedDateTime"

        Glide.with(requireContext())
            .load(repository.owner?.avatarUrl)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(binding.ivOwnerAvatar)
    }

    private fun formatDateTime(dateTime: String?): String {
        if (dateTime.isNullOrEmpty()) return "N/A"
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")
        val date = inputFormat.parse(dateTime)
        val outputFormat = SimpleDateFormat("MM-dd-yyyy HH:mm", Locale.getDefault())
        return date?.let { outputFormat.format(it) } ?: "N/A"
    }

}




