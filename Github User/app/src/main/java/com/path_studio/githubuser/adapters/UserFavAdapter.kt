package com.path_studio.githubuser.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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

    fun addItem(user: UserFav) {
        this.listUser.add(user)
        notifyItemInserted(this.listUser.size - 1)
    }
    fun updateItem(position: Int, user: UserFav) {
        this.listUser[position] = user
        notifyItemChanged(position, user)
    }
    fun removeItem(position: Int) {
        this.listUser.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listUser.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserFavViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_fav_user, parent, false)
        return UserFavViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserFavViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = this.listUser.size

    inner class UserFavViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRowFavUserBinding.bind(itemView)
        fun bind(user: UserFav) {
            binding.itemRowUsername.text = user.login
            //binding.itemRowName.text = user.name
            binding.itemRowDate.text = user.date

            /*Glide.with(binding.clItemUser)
                .load(user.avatar)
                .apply(RequestOptions().override(500, 500))
                .into(binding.itemRowAvatar)*/

            binding.clItemUser.setOnClickListener(CustomOnItemClickListener(adapterPosition, object : CustomOnItemClickListener.OnItemClickCallback {
                override fun onItemClicked(view: View, position: Int) {
                    //show detail page
                    val i = Intent(activity, DetailUserActivity::class.java)
                    val userData: User = User(user.login,0,"","","","","",
                        "","",0,0,0,0,"","","")
                    i.putExtra(DetailUserActivity.EXTRA_USER, userData)
                    activity.startActivity(i)
                }
            }))
        }
    }
}