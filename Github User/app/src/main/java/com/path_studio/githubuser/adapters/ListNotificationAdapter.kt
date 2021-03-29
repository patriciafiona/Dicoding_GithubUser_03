package com.path_studio.githubuser.adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.path_studio.githubuser.R
import com.path_studio.githubuser.Utils
import com.path_studio.githubuser.models.Notification
import de.hdodenhof.circleimageview.CircleImageView

class ListNotificationAdapter(val list: ArrayList<Notification>, val context: Context) : RecyclerView.Adapter<ListNotificationAdapter.ListViewHolder>(){
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_notification, viewGroup, false)
        return ListViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = list[position]

        when(data.reason){
            "security_alert" ->
                holder.reasonIcon.setImageResource(R.drawable.ic_baseline_warning_orange_24)
            else ->
                holder.reasonIcon.setImageResource(R.drawable.ic_baseline_help_outline_red_24)
        }

        if(data.unread){
            holder.statusIcon.visibility = View.VISIBLE
        }else if(!data.unread){
            holder.statusIcon.visibility = View.GONE
        }

        holder.title.text = data.subject.title
        holder.fullName.text = data.repository.full_name

        //compare time after this notification created
        val detailCompare = Utils.checkDayswithToday(data.updated_at)
        holder.createdAt.text = detailCompare[0]
        holder.detailTime.text = detailCompare[1]

    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var reasonIcon: ImageView = itemView.findViewById(R.id.notification_reason)
        var statusIcon: CircleImageView = itemView.findViewById(R.id.notification_status)
        var fullName: TextView = itemView.findViewById(R.id.notification_fullname)
        var title: TextView = itemView.findViewById(R.id.notification_title)
        var createdAt: TextView = itemView.findViewById(R.id.notification_days)
        var detailTime: TextView = itemView.findViewById(R.id.notification_detail_time)
    }
}