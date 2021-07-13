package com.anta40.app.githubtest2021.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anta40.app.githubtest2021.R
import com.anta40.app.githubtest2021.adapter.GithubUserListAdapter
import com.anta40.app.githubtest2021.data.Status
import com.anta40.app.githubtest2021.databinding.GithubContributorsListBinding
import com.anta40.app.githubtest2021.model.GithubUser
import com.anta40.app.githubtest2021.viewmodel.GithubContributorsViewModel
import org.koin.androidx.viewmodel.compat.ScopeCompat.viewModel

class GithubContributorsFragment : Fragment(), GithubUserListAdapter.UsersListAdapterInteraction {

    private val usersListViewModel: GithubContributorsViewModel by viewModel()
    private lateinit var itemViewer: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: GithubContributorsListBinding = DataBindingUtil.inflate(inflater, R.layout.github_contributors_list, container, false)

        binding.usersListViewModel = usersListViewModel

        itemViewer = binding.root.findViewById(R.id.recview_users)
        initAdapterAndObserve()

        return binding.root
    }

    private fun initAdapterAndObserve() {
        val usersListAdapter = GithubUserListAdapter(this)

        itemViewer.layoutManager = LinearLayoutManager(context)
        itemViewer.adapter = usersListAdapter

        Transformations.switchMap(usersListViewModel.dataSource) { dataSource -> dataSource.loadStateLiveData }
            .observe(this, Observer {
                when(it) {
                    Status.LOADING -> {
                        usersListViewModel.isWaiting.set(true)
                        usersListViewModel.errorMessage.set(null)
                    }
                    Status.SUCCESS -> {
                        usersListViewModel.isWaiting.set(false)
                        usersListViewModel.errorMessage.set(null)
                    }
                    Status.EMPTY -> {
                        usersListViewModel.isWaiting.set(false)
                        usersListViewModel.errorMessage.set(getString(R.string.msg_users_list_is_empty))
                    }
                    else -> {
                        usersListViewModel.isWaiting.set(false)
                        usersListViewModel.errorMessage.set(getString(R.string.msg_fetch_users_list_has_error))
                    }
                }
            })

        Transformations.switchMap(usersListViewModel.dataSource) { dataSource -> dataSource.totalCount }
            .observe(this, Observer {totalCount ->
                totalCount?.let { usersListViewModel.totalCount.set(it)}
            })

        usersListViewModel.usersLiveData.observe(this, Observer {
            usersListAdapter.submitList(it)
        })
    }

    override fun onUserItemClick(githubUserModel: GithubUser) {

    }
}