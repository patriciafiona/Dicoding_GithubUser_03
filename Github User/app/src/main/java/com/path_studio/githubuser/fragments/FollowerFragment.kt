package com.path_studio.githubuser.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.path_studio.githubuser.activities.DetailFollowActivity
import com.path_studio.githubuser.adapters.ListFollowAdapter
import com.path_studio.githubuser.databinding.FragmentFollowerBinding
import com.path_studio.githubuser.models.MainViewModel
import com.path_studio.githubuser.entities.User

class FollowerFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var listFollower:ArrayList<User>

    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding as FragmentFollowerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)
        val view = binding.root

        //show Loading
        showLoading(true)

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        //get list following
        getFollowerFromAPIAndShow()

        return view
    }

    private fun getFollowerFromAPIAndShow(){
        mainViewModel.setFollowers(activity as DetailFollowActivity, DetailFollowActivity.USERNAME)

        mainViewModel.getFollowers().observe(activity as DetailFollowActivity, {items ->
            if (items != null) {
                listFollower = items
                showRV()
                showLoading(false)
            }
        })

    }

    private fun showRV(){
        if(listFollower.size > 0){
            binding.noData.visibility = View.GONE
            binding.noRvData.visibility = View.GONE

            val rvListFollowing: RecyclerView = binding.rvListFollower
            rvListFollowing.setHasFixedSize(true)

            showRecyclerList(rvListFollowing, listFollower)
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