package com.path_studio.githubuser.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserFav (
    var id: Int = 0,
    var login: String? = null,
    var date: String? = null
): Parcelable
