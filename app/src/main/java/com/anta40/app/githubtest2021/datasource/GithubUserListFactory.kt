package com.anta40.app.githubtest2021.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.anta40.app.githubtest2021.data.GithubApiClient
import com.anta40.app.githubtest2021.model.GithubUser

class GithubUserListFactory(private val repo_owner: String, private val repo_name: String, private val githubApiClient:
                            GithubApiClient
): DataSource.Factory<Int, GithubUser>() {

    val liveData: MutableLiveData<GithubUserListDataSource> = MutableLiveData()

    override fun create(): DataSource<Int, GithubUser> {
        val usersListDataSource = GithubUserListDataSource(repo_owner, repo_name, githubApiClient)
        liveData.postValue(usersListDataSource)
        return usersListDataSource
    }
}