package com.path_studio.consumer_app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.path_studio.consumer_app.activities.DetailFollowActivity
import com.path_studio.consumer_app.adapters.ListFollowAdapter
import com.path_studio.consumer_app.databinding.FragmentFollowingBinding
import com.path_studio.consumer_app.entities.User
import com.path_studio.githubuser.models.MainViewModel

class FollowingFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var listFollowing:ArrayList<User>

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding as FragmentFollowingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        val view = binding.root

        //show Loading
        showLoading(true)

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        //get list following
        getFollowingFromAPIAndShow()

        return view
    }

    private fun getFollowingFromAPIAndShow(){
        mainViewModel.setFollowing(activity as DetailFollowActivity, DetailFollowActivity.USERNAME)

        mainViewModel.getFollowing().observe(activity as DetailFollowActivity, {items ->
            if (items != null) {
                listFollowing = items
                showRV()
                showLoading(false)
            }
        })
    }

    private fun showRV(){
        if(listFollowing.size > 0){
            binding.noData.visibility = View.GONE
            binding.noRvData.visibility = View.GONE

            val rvListFollowing: RecyclerView = binding.rvListFollowing
            rvListFollowing.setHasFixedSize(true)

            showRecyclerList(rvListFollowing, listFollowing)
        }else{
            binding.noData.visibility = View.VISIBLE
            binding.noRvData.visibility = View.VISIBLE
        }
    }

    private fun showRecyclerList(rv: RecyclerView, list: ArrayList<User>) {
        rv.layoutManager = LinearLayoutManager(activity)
        val listAppAdapter = ListFollowAdapter(list, activity as DetailFollowActivity)
        rv.adapter = listAppAdapter
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}