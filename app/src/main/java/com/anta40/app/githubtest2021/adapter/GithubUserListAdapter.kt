package com.anta40.app.githubtest2021.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.anta40.app.githubtest2021.R
import com.anta40.app.githubtest2021.model.GithubUser
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class GithubUserListAdapter(private val  listener: UsersListAdapterInteraction):
    PagedListAdapter<GithubUser, GithubUserListAdapter.UsersListViewHolder>(usersDiffCallback) {

    lateinit var context: Context

    interface UsersListAdapterInteraction {
        fun onUserItemClick(githubUserModel: GithubUser)
    }

    inner class UsersListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userItem: LinearLayout = itemView.findViewById(R.id.userItem)
        val imgAvatar: ImageView = itemView.findViewById(R.id.imgAvatar)
        val txtName: AppCompatTextView = itemView.findViewById(R.id.txtName)
        val txtHomeUrl: AppCompatTextView = itemView.findViewById(R.id.txtHomeUrl)
    }

    override fun onBindViewHolder(holder: UsersListViewHolder, position: Int) {
        val githubUserModel = getItem(position)
        githubUserModel?.let {
            Glide.with(context)
                .load(it.avatarUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.imgAvatar)

            holder.txtName.text = it.login
            holder.txtHomeUrl.text = it.htmlUrl

            holder.userItem.setOnClickListener {
                listener.onUserItemClick(githubUserModel)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersListViewHolder {
        context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.item_github_user, parent, false)
        return UsersListViewHolder(view)
    }

    companion object {
        val usersDiffCallback = object : DiffUtil.ItemCallback<GithubUser>() {
            override fun areItemsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean {
                return oldItem == newItem
            }
        }
    }
}