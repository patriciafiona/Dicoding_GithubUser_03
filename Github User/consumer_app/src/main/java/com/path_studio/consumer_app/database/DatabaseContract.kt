package com.path_studio.githubuser.database

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {
    const val AUTHORITY = "com.path_studio.githubuser"
    const val SCHEME = "content"

    class UserColumns : BaseColumns {

        companion object {
            const val TABLE_NAME = "user"
            const val _ID = "_id"
            const val LOGIN = "login"
            const val DATE = "date"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}