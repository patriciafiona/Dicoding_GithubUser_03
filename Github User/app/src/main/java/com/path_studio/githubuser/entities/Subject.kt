package com.path_studio.githubuser.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Subject(
        var title: String,
        var url: String,
        var latest_comment_url: String,
        var type: String
): Parcelable
