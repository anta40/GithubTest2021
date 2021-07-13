package com.anta40.app.githubtest2021.data

import com.anta40.app.githubtest2021.model.GithubUser
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {

    @GET("repos/{owner}/{repo}/contributors\n")
    suspend fun getUserInfo(
        @Path("owner") github_repo_owner: String,
        @Path("repo") github_repo_name: String
    ): Response<GithubUser>

}