package com.path_studio.githubuser.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Search(
        var total_count: Int,
        var items:ArrayList<User>
): Parcelable
