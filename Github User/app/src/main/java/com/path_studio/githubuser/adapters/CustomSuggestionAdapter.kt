package com.path_studio.githubuser.adapters

import android.content.Intent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter
import com.path_studio.githubuser.R
import com.path_studio.githubuser.activities.DetailUserActivity
import com.path_studio.githubuser.activities.MainActivity
import com.path_studio.githubuser.models.User

class CustomSuggestionsAdapter(inflater: LayoutInflater?, val activity: MainActivity) : SuggestionsAdapter<User, CustomSuggestionsAdapter.SuggestionHolder>(inflater)
{

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomSuggestionsAdapter.SuggestionHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_row_suggestion, parent, false)
        return SuggestionHolder(view)
    }

    override fun onBindSuggestionHolder(
        suggestion: User,
        holder: CustomSuggestionsAdapter.SuggestionHolder,
        position: Int
    ) {

        holder.name.text = suggestion.login

        Glide.with(holder.itemView)
            .load(suggestion.avatar_url)
            .apply(RequestOptions().override(500, 500))
            .into(holder.avatar)

        holder.itemView.setOnClickListener {
            //show detail page
            val i = Intent(activity, DetailUserActivity::class.java)
            i.putExtra(DetailUserActivity.EXTRA_USER, suggestion)
            activity.startActivity(i)
        }
    }

    override fun getSingleViewHeight(): Int {
        return 60
    }

    inner class SuggestionHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.suggestion_appName)
        var avatar: ImageView = itemView.findViewById(R.id.suggestion_appLogo)
    }

}