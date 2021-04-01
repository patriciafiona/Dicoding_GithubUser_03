package com.path_studio.githubuser.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.net.toUri
import com.path_studio.githubuser.R
import com.path_studio.githubuser.activities.DetailUserActivity
import com.path_studio.githubuser.database.UserHelper
import com.path_studio.githubuser.entities.User
import com.path_studio.githubuser.fragments.ProfileFragment
import com.path_studio.githubuser.helper.MappingHelper
import com.path_studio.githubuser.models.CreateAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoriteUserWidget : AppWidgetProvider() {
    companion object {

        private const val TOAST_ACTION = "com.path_studio.githubuser.TOAST_ACTION"
        const val EXTRA_ITEM = "com.path_studio.githubuser.EXTRA_ITEM"
        const val EXTRA_LOGIN = "USER_LOGIN"

        fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
            val intent = Intent(context, StackWidgetService::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)

            //get data from database
            GlobalScope.launch(Dispatchers.Main) {
                val userHelper = UserHelper.getInstance(context)
                userHelper.open()
                val deferredUsers = async(Dispatchers.IO) {
                    val cursor = userHelper.queryAll()
                    MappingHelper.mapCursorToArrayList(cursor)
                }

                val users = deferredUsers.await()
                if (users.size > 0) {
                    //get data from API and set to adapter
                    val listUserAvatar: ArrayList<String> = ArrayList()
                    val listUserLogin: ArrayList<String> = ArrayList()
                    for(u in users){
                        CreateAPI.create().getUserDetail(
                            u.login,
                            ProfileFragment.ACCESS_TOKEN
                        ).enqueue(object : Callback<User> {
                            override fun onResponse(call: Call<User>, response: Response<User>) {
                                if (response.isSuccessful) {
                                    val user = response.body() as User
                                    listUserAvatar.add(user.avatar_url.toString())
                                    listUserLogin.add(user.login.toString())

                                    if(listUserAvatar.size == users.size && listUserLogin.size == users.size) {
                                        intent.putExtra("AVATAR_LIST", listUserAvatar)
                                        intent.data = intent.toUri(Intent.URI_INTENT_SCHEME).toUri()

                                        val views = RemoteViews(context.packageName, R.layout.image_user_fav_widget)
                                        views.setRemoteAdapter(R.id.stack_view, intent)
                                        views.setEmptyView(R.id.stack_view, R.id.empty_view)

                                        val toastIntent = Intent(context, FavoriteUserWidget::class.java)
                                        toastIntent.action = TOAST_ACTION
                                        toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
                                        toastIntent.putExtra(EXTRA_LOGIN, listUserLogin)
                                        intent.data = intent.toUri(Intent.URI_INTENT_SCHEME).toUri()
                                        val toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent, PendingIntent.FLAG_UPDATE_CURRENT)
                                        views.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent)

                                        appWidgetManager.updateAppWidget(appWidgetId, views)
                                    }
                                }
                            }

                            override fun onFailure(call: Call<User>, error: Throwable) {
                                Log.e("tag", "The Error is: ${error.message}")
                            }
                        })
                    }
                }
                userHelper.close()
            }
            //end of read database
        }
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action != null) {
            if (intent.action == TOAST_ACTION) {
                val login = intent.getStringArrayListExtra(EXTRA_LOGIN) as ArrayList<String>
                val viewIndex = intent.getIntExtra(EXTRA_ITEM, 0)
                Toast.makeText(context, login[viewIndex], Toast.LENGTH_SHORT).show()
            }
        }
    }
}