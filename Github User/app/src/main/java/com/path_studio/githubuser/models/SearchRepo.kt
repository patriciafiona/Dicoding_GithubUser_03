package com.path_studio.githubuser.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchRepo (
    var total_count: Int,
    var items: ArrayList<Repository>
): Parcelable