package com.anta40.app.githubtest2021.data

import com.anta40.app.githubtest2021.model.GithubUserResponse

interface GithubApiClient {
    suspend fun getContributorsList(repo_owner: String, repo_nane: String,
                                    page: Int, pageSize: Int): Resource<GithubUserResponse>

}