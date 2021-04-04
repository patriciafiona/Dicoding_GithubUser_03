package com.path_studio.githubuser.helper

import android.database.Cursor
import com.path_studio.consumer_app.entity.UserFav
import com.path_studio.githubuser.database.DatabaseContract

object MappingHelper {
    fun mapCursorToArrayList(notesCursor: Cursor?): ArrayList<UserFav> {
        val userList = ArrayList<UserFav>()
        notesCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns._ID))
                val login = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.LOGIN))
                val date = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.DATE))
                userList.add(UserFav(id, login, date))
            }
        }
        return userList
    }
}