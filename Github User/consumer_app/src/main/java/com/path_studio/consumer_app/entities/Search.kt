package com.path_studio.consumer_app.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Search(
        var total_count: Int,
        var items:ArrayList<User>
): Parcelable
