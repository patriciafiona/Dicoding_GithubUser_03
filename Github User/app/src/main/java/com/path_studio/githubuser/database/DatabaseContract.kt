package com.path_studio.githubuser.database

import android.provider.BaseColumns

internal class DatabaseContract {

    internal class UserColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "user"
            const val _ID = "_id"
            const val LOGIN = "login"
            const val DATE = "date"
        }
    }
}