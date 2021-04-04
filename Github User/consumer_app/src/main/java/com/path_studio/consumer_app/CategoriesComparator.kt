package com.path_studio.consumer_app

import com.path_studio.consumer_app.entity.User

class CategoriesComparator: Comparator<User> {
    override fun compare(category1: User, category2: User): Int {
        return category1.name.toString().compareTo(category2.name.toString())
    }
}