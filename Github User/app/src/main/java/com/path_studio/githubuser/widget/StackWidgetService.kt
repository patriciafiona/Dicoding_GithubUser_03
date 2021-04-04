package com.path_studio.githubuser.widget

import android.content.Intent
import android.util.Log
import android.widget.RemoteViewsService

class StackWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        Log.e("StackWidgetService", "Masuk Sini")
        val listAvatar = intent.getStringArrayListExtra("AVATAR_LIST") as ArrayList<String>
        return StackRemoteViewsFactory(this.applicationContext, listAvatar)
    }

}