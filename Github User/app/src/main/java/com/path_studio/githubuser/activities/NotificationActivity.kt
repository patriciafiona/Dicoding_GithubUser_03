package com.path_studio.githubuser.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.path_studio.githubuser.adapters.ListNotificationAdapter
import com.path_studio.githubuser.databinding.ActivityNotificationBinding
import com.path_studio.githubuser.entities.Notification
import com.path_studio.githubuser.models.MainViewModel

class NotificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationBinding

    private lateinit var mainViewModel: MainViewModel
    private lateinit var listNotification: ArrayList<Notification>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //show loading
        showLoading(true)

        //init Main view model
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        //get Notification data from API
        getNotificationFromAPI()
    }

    private fun getNotificationFromAPI(){
        mainViewModel.setNotification(this@NotificationActivity)

        mainViewModel.getNotifications().observe(this) { items ->
            if (items != null) {
                listNotification = items
                showNotifications()
                showLoading(false)
            }
        }
    }

    private fun showNotifications(){
        val rvListNotification: RecyclerView = binding.rvListNotification
        rvListNotification.setHasFixedSize(true)

        showRecyclerList(rvListNotification, listNotification)
    }

    private fun showRecyclerList(rv: RecyclerView, list: ArrayList<Notification>) {
        rv.layoutManager = LinearLayoutManager(this)
        val listAppAdapter = ListNotificationAdapter(list, this)
        rv.adapter = listAppAdapter
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}