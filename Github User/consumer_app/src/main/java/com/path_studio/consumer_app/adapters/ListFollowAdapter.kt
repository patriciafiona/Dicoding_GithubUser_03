package com.path_studio.consumer_app.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.path_studio.consumer_app.R
import com.path_studio.consumer_app.Utils
import com.path_studio.consumer_app.activities.DetailUserActivity
import com.path_studio.consumer_app.entities.User
import de.hdodenhof.circleimageview.CircleImageView

class ListFollowAdapter(val list: ArrayList<User>, val context: Context) : RecyclerView.Adapter<ListFollowAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_user, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = list[position]

        holder.dispUsername.text = user.login
        holder.dispType.text = Utils.checkEmptyValue(user.type.toString())

        Glide.with(holder.itemView)
            .load(user.avatar_url.toString())
            .apply(RequestOptions().override(500, 500))
            .into(holder.dispAvatar)

        holder.itemView.setOnClickListener {
            //show detail page
            val i = Intent(context, DetailUserActivity::class.java)
            i.putExtra(DetailUserActivity.EXTRA_USER, user)
            context.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var dispUsername: TextView = itemView.findViewById(R.id.item_row_username)
        var dispType: TextView = itemView.findViewById(R.id.item_row_type)
        var dispAvatar: CircleImageView = itemView.findViewById(R.id.item_row_avatar)
    }
}