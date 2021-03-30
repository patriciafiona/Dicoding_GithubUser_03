package com.path_studio.githubuser.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.path_studio.githubuser.MappingHelper
import com.path_studio.githubuser.activities.MainActivity
import com.path_studio.githubuser.adapters.UserFavAdapter
import com.path_studio.githubuser.database.UserHelper
import com.path_studio.githubuser.databinding.FragmentFavoriteUserBinding
import com.path_studio.githubuser.entities.UserFav
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteUserFragment : Fragment() {

    private var _binding: FragmentFavoriteUserBinding? = null
    private val binding get() = _binding as FragmentFavoriteUserBinding
    private lateinit var adapter: UserFavAdapter

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteUserBinding.inflate(inflater, container, false)
        val view = binding.root

        showLoading(true)

        binding.rvFavUser.layoutManager = LinearLayoutManager(activity)
        binding.rvFavUser.setHasFixedSize(true)
        adapter = UserFavAdapter(activity as MainActivity)

        if (savedInstanceState == null) {
            readDatabase()
        } else {
            val list = savedInstanceState.getParcelableArrayList<UserFav>(EXTRA_STATE)
            if (list != null) {
                adapter.listUser = list
                showLoading(false)
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //Show Search Bar
        (activity as MainActivity).setSearchBarVisibility(1)
        (activity as MainActivity).clearSearchBar()
    }

    private fun readDatabase(){
        GlobalScope.launch(Dispatchers.Main) {
            val userHelper = UserHelper.getInstance((activity as MainActivity).applicationContext)
            userHelper.open()
            val deferredUsers = async(Dispatchers.IO) {
                val cursor = userHelper.queryAll()
                MappingHelper.mapCursorToArrayList(cursor)
            }

            val users = deferredUsers.await()
            if (users.size > 0) {
                adapter.listUser = users
                binding.rvFavUser.adapter = adapter

                binding.noData.visibility = View.GONE
                binding.noDataTxt.visibility = View.GONE
            } else {
                adapter.listUser = ArrayList()
                binding.rvFavUser.adapter = adapter

                binding.noData.visibility = View.VISIBLE
                binding.noDataTxt.visibility = View.VISIBLE
            }
            showLoading(false)
            userHelper.close()
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}