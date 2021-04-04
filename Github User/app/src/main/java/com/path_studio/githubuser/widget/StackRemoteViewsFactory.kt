package com.path_studio.githubuser.widget

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.path_studio.githubuser.R
import com.path_studio.githubuser.Utils

internal class StackRemoteViewsFactory(private val mContext: Context, private val listAvatar: ArrayList<String>) : RemoteViewsService.RemoteViewsFactory {

    private val mWidgetItems = ArrayList<Bitmap>()
    private var mListAvatar = ArrayList<String>()

    fun setListAvatar(listAvatar: ArrayList<String>){
        Log.e("Manual Add", listAvatar.toString())
        this.mListAvatar = listAvatar
    }

    override fun onCreate() {
        setListAvatar(listAvatar)
    }

    override fun onDataSetChanged() {
        mWidgetItems.clear()
        for (avatarURL in mListAvatar){
            Log.e("avatar URL", avatarURL)
            mWidgetItems.add(Utils.getBitmapFromURL(mContext, avatarURL))
        }
    }

    override fun onDestroy() {}

    override fun getCount(): Int = mWidgetItems.size

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.widget_item)
        rv.setImageViewBitmap(R.id.imageView, mWidgetItems[position])

        val extras = bundleOf(
            FavoriteUserWidget.EXTRA_ITEM to position
        )
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent)
        return rv
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(i: Int): Long = 0

    override fun hasStableIds(): Boolean = false

}