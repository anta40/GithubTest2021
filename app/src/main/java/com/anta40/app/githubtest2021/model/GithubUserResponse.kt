package com.anta40.app.githubtest2021.model

import com.google.gson.annotations.SerializedName

data class GithubUserResponse(
    @SerializedName("total_count") var totalCount: Long,
    @SerializedName("incomplete_results") var incompleteResults: Boolean,
    @SerializedName("items") var items: List<GithubUser>
)
