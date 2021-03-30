package com.path_studio.githubuser.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.path_studio.githubuser.R
import com.path_studio.githubuser.Utils
import com.path_studio.githubuser.activities.MainActivity
import com.path_studio.githubuser.entities.SearchRepo
import de.hdodenhof.circleimageview.CircleImageView

class ListTrendingRepoAdapter(val searchRepo: SearchRepo, val activity: MainActivity) : RecyclerView.Adapter<ListTrendingRepoAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_col_trending_repo, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = searchRepo.items[position]

        Glide.with(holder.itemView.context)
                .load(data.owner.avatar_url)
                .apply(RequestOptions().override(800, 800))
                .into(holder.dispBackground)

        Glide.with(holder.itemView.context)
            .load(data.owner.avatar_url)
            .apply(RequestOptions().override(500, 500))
            .into(holder.dispAvatar)

        holder.dispUsername.text = data.owner.login
        holder.dispRepoName.text = data.name
        holder.dispRepoDetail.text = data.description
        holder.dispStarred.text = Utils.convertNumberFormat(data.stargazers_count)

        holder.dispLanguage.text = Utils.checkEmptyValue(data.language)

        when(data.language){
            "Java" -> holder.dispLanguageIndicator.setImageResource(R.color.red)
            "Python" -> holder.dispLanguageIndicator.setImageResource(R.color.blue_900)
            "PHP" -> holder.dispLanguageIndicator.setImageResource(R.color.purple)
            "JavaScript" -> holder.dispLanguageIndicator.setImageResource(R.color.amber)
            "C" -> holder.dispLanguageIndicator.setImageResource(R.color.blue_500)
            "C++" -> holder.dispLanguageIndicator.setImageResource(R.color.yellow_100)
            "Dart" -> holder.dispLanguageIndicator.setImageResource(R.color.purple_900)
            "Kotlin" -> holder.dispLanguageIndicator.setImageResource(R.color.cyan)
            "Shell" -> holder.dispLanguageIndicator.setImageResource(R.color.yellow)
            "HTML" -> holder.dispLanguageIndicator.setImageResource(R.color.amber_200)
            "CSS" -> holder.dispLanguageIndicator.setImageResource(R.color.blue_grey_600)
            else -> holder.dispLanguageIndicator.setImageResource(R.color.grey_600)
        }
    }

    override fun getItemCount(): Int {
        return searchRepo.items.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var dispBackground: ImageView = itemView.findViewById(R.id.discover_background)
        var dispAvatar: CircleImageView = itemView.findViewById(R.id.discover_avatar)
        var dispUsername: TextView = itemView.findViewById(R.id.discover_username)
        var dispRepoName: TextView = itemView.findViewById(R.id.discover_repo_name)
        var dispRepoDetail: TextView = itemView.findViewById(R.id.discover_details)
        var dispStarred: TextView = itemView.findViewById(R.id.discover_starred)
        var dispLanguage: TextView = itemView.findViewById(R.id.discover_language)
        var dispLanguageIndicator: CircleImageView = itemView.findViewById(R.id.discover_langauge_indicator)
    }

}