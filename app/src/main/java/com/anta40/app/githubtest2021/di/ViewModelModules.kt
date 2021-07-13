package com.anta40.app.githubtest2021.di

import com.anta40.app.githubtest2021.viewmodel.GithubContributorsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val usersListViewModel = module {
    viewModel { GithubContributorsViewModel(get()) }
}