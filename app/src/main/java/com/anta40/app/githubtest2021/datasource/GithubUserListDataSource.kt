package com.anta40.app.githubtest2021.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.anta40.app.githubtest2021.data.GithubApiClient
import com.anta40.app.githubtest2021.data.Status
import com.anta40.app.githubtest2021.model.GithubUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class GithubUserListDataSource(private val repo_owner: String, private val repo_name:String,
                          private val githubApiClient: GithubApiClient):
    PageKeyedDataSource<Int, GithubUser>() {

    private val dataSourceJob = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Main + dataSourceJob)
    val loadStateLiveData: MutableLiveData<Status> = MutableLiveData()
    val totalCount: MutableLiveData<Long> = MutableLiveData()

    companion object{
        const val PAGE_SIZE = 15
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, GithubUser>) {
        scope.launch {
            loadStateLiveData.postValue(Status.LOADING)
            totalCount.postValue(0)

            val response = githubApiClient.getContributorsList(repo_owner, repo_name, 1, PAGE_SIZE)
            when(response.status) {
                Status.ERROR -> loadStateLiveData.postValue(Status.ERROR)
                Status.EMPTY -> loadStateLiveData.postValue(Status.EMPTY)
                else -> {
                    response.data?.let {
                        callback.onResult(it.items, null, 2)
                        loadStateLiveData.postValue(Status.SUCCESS)
                        totalCount.postValue(it.totalCount)
                    }
                }
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, GithubUser>) {
        scope.launch {
            val response = githubApiClient.getContributorsList(repo_owner, repo_name, params.key, PAGE_SIZE)
            response.data?.let {
                callback.onResult(it.items, params.key + 1)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, GithubUser>) {

    }
}