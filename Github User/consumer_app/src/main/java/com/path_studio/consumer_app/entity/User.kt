package com.path_studio.consumer_app.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
    var login: String?, //username
    var id: Int,
    var avatar_url: String?,
    var name: String?,
    var company: String?,
    var blog: String?, //link
    var location: String?,
    var email: String?,
    var bio: String?,
    var public_repos: Int, //only get total public repositories
    var owned_private_repos: Int,
    var followers: Int,
    var following: Int,
    var created_at: String?,
    var updated_at: String?,
    var type: String?,
): Parcelable