package com.path_studio.consumer_app.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.path_studio.consumer_app.databinding.ActivityDetailUserBinding

private lateinit var binding: ActivityDetailUserBinding

class DetailUserActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER = "extra_user"
        const val MY_USERNAME = "patriciafiona"

        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
    }
}