package com.path_studio.consumer_app.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Repository (
    var id: Int,
    var name: String,
    var full_name: String?,
    var isPrivate: Boolean,
    var owner: User,
    var html_url: String,
    var description: String,
    var language: String?,
    var stargazers_count: Int
): Parcelable