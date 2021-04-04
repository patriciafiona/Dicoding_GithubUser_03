package com.path_studio.consumer_app.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
    var login: String? = null, //username
    var id: Int = 0,
    var avatar_url: String? = null,
    var name: String? = null,
    var company: String? = null,
    var blog: String? = null, //link
    var location: String? = null,
    var email: String? = null,
    var bio: String? = null,
    var public_repos: Int = 0, //only get total public repositories
    var owned_private_repos: Int = 0,
    var followers: Int = 0,
    var following: Int = 0,
    var created_at: String? = null,
    var updated_at: String? = null,
    var type: String? = null,
): Parcelable