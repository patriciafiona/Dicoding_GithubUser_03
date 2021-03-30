package com.path_studio.githubuser.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserFav (
    var id: Int,
    var login: String?,
    var date: String?
): Parcelable
