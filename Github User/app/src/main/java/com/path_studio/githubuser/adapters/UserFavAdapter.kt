package com.path_studio.githubuser.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.path_studio.githubuser.CustomOnItemClickListener
import com.path_studio.githubuser.R
import com.path_studio.githubuser.activities.DetailUserActivity
import com.path_studio.githubuser.databinding.ItemRowFavUserBinding
import com.path_studio.githubuser.entities.User
import com.path_studio.githubuser.entities.UserFav

class UserFavAdapter(private val activity: Activity) : RecyclerView.Adapter<UserFavAdapter.UserFavViewHolder>() {
    var listUser = ArrayList<UserFav>()
        set(listUser) {
            if (listUser.size > 0) {
                this.listUser.clear()
            }
            this.listUser.addAll(listUser)
            notifyDataSetChanged()
        }

    var listDetailUser = ArrayList<User>()
        set(listDetailUser) {
            if (listDetailUser.size > 0) {
                this.listDetailUser.clear()
            }
            this.listDetailUser.addAll(listDetailUser)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserFavViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_fav_user, parent, false)
        return UserFavViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserFavViewHolder, position: Int) {
        holder.bind(listUser[position], listDetailUser[position])
    }

    override fun getItemCount(): Int = this.listUser.size

    inner class UserFavViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRowFavUserBinding.bind(itemView)
        fun bind(user: UserFav, userDetail: User) {
            binding.itemRowUsername.text = user.login
            binding.itemRowName.text = userDetail.name
            binding.itemRowDate.text = user.date

            Glide.with(binding.clItemUser)
                .load(userDetail.avatar_url)
                .apply(RequestOptions().override(500, 500))
                .into(binding.itemRowAvatar)

            binding.clItemUser.setOnClickListener(CustomOnItemClickListener(adapterPosition, object : CustomOnItemClickListener.OnItemClickCallback {
                override fun onItemClicked(view: View, position: Int) {
                    //show detail page
                    val i = Intent(activity, DetailUserActivity::class.java)
                    i.putExtra(DetailUserActivity.EXTRA_USER, userDetail)
                    activity.startActivity(i)
                }
            }))
        }
    }
}