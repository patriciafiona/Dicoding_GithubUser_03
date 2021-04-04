package com.path_studio.consumer_app.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Notification(
        var id: Int,
        var unread: Boolean,
        var reason: String,
        var updated_at: String,
        var last_read_at: String,
        var subject: Subject,
        var repository: Repository,
        var url: String
): Parcelable
