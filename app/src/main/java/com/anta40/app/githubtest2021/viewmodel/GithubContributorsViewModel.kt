package com.anta40.app.githubtest2021.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.anta40.app.githubtest2021.datasource.GithubUserListFactory
import com.anta40.app.githubtest2021.datasource.GithubUserListDataSource
import com.anta40.app.githubtest2021.model.GithubUser
import java.util.concurrent.Executors

class GithubContributorsViewModel(private val usersListDataSourceFactory:
                                  GithubUserListFactory): ViewModel() {

    var dataSource: MutableLiveData<GithubUserListDataSource>
    lateinit var usersLiveData: LiveData<PagedList<GithubUser>>
    val isWaiting: ObservableField<Boolean> = ObservableField()
    val errorMessage: ObservableField<String> = ObservableField()
    val totalCount: ObservableField<Long> = ObservableField()

    init {
        isWaiting.set(true)
        errorMessage.set(null)
        dataSource = usersListDataSourceFactory.liveData
        initUsersListFactory()
    }

    private fun initUsersListFactory() {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(GithubUserListDataSource.PAGE_SIZE)
            .setPageSize(GithubUserListDataSource.PAGE_SIZE)
            .setPrefetchDistance(3)
            .build()

        val executor = Executors.newFixedThreadPool(5)

        usersLiveData = LivePagedListBuilder<Int, GithubUser>(usersListDataSourceFactory, config)
            .setFetchExecutor(executor)
            .build()
    }
}