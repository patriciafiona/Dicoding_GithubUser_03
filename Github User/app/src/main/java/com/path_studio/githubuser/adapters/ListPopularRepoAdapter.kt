package com.path_studio.githubuser.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.path_studio.githubuser.R
import com.path_studio.githubuser.Utils
import com.path_studio.githubuser.models.Repository
import de.hdodenhof.circleimageview.CircleImageView

class ListPopularRepoAdapter (val list: ArrayList<Repository>, val context: Context) : RecyclerView.Adapter<ListPopularRepoAdapter.ListViewHolder>(){

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_col_popular_repository, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val repo = list[position]

        holder.dispRepoUsername.text = repo.owner.login
        holder.dispRepoName.text = repo.name
        holder.dispRepoDetail.text = Utils.checkEmptyValue(repo.description)
        holder.dispRepoCategoryTxt.text = Utils.checkEmptyValue(repo.language.toString())
        holder.dispFav.text = Utils.convertNumberFormat(repo.stargazers_count)

        when(repo.language){
            "Java" -> holder.dispRepoCategoryIndicator.setImageResource(R.color.red)
            "PHP" -> holder.dispRepoCategoryIndicator.setImageResource(R.color.purple)
            "JavaScript" -> holder.dispRepoCategoryIndicator.setImageResource(R.color.amber)
            "C" -> holder.dispRepoCategoryIndicator.setImageResource(R.color.blue_500)
            "C++" -> holder.dispRepoCategoryIndicator.setImageResource(R.color.blue_900)
            "Dart" -> holder.dispRepoCategoryIndicator.setImageResource(R.color.purple_900)
            "Kotlin" -> holder.dispRepoCategoryIndicator.setImageResource(R.color.cyan)
            "Shell" -> holder.dispRepoCategoryIndicator.setImageResource(R.color.yellow)
            "HTML" -> holder.dispRepoCategoryIndicator.setImageResource(R.color.amber_200)
            "CSS" -> holder.dispRepoCategoryIndicator.setImageResource(R.color.blue_grey_600)
            else -> holder.dispRepoCategoryIndicator.setImageResource(R.color.grey_600)
        }

        Glide.with(holder.itemView)
                .load(repo.owner.avatar_url.toString())
                .apply(RequestOptions().override(500, 500))
                .into(holder.dispRepoAvatar)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var dispRepoUsername: TextView = itemView.findViewById(R.id.popular_repo_username)
        var dispRepoName: TextView = itemView.findViewById(R.id.popular_repo_name)
        var dispRepoDetail: TextView = itemView.findViewById(R.id.popular_repo_detail)
        var dispRepoCategoryTxt: TextView = itemView.findViewById(R.id.popular_repo_category_name)
        var dispRepoCategoryIndicator: CircleImageView = itemView.findViewById(R.id.popular_repo_category)
        var dispFav: TextView = itemView.findViewById(R.id.popular_repo_favorite)
        var dispRepoAvatar: CircleImageView = itemView.findViewById(R.id.popular_repo_avatar)
    }

}