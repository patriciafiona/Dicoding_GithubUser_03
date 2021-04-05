package com.path_studio.githubuser.widget

import android.content.Intent
import android.widget.RemoteViewsService

class StackWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        val listAvatar = intent.getStringArrayListExtra("AVATAR_LIST") as ArrayList<String>
        return StackRemoteViewsFactory(this.applicationContext, listAvatar)
    }

}