package com.path_studio.githubuser.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.path_studio.githubuser.helper.MappingHelper
import com.path_studio.githubuser.Utils
import com.path_studio.githubuser.activities.MainActivity
import com.path_studio.githubuser.adapters.UserFavAdapter
import com.path_studio.githubuser.database.UserHelper
import com.path_studio.githubuser.databinding.FragmentFavoriteUserBinding
import com.path_studio.githubuser.entities.User
import com.path_studio.githubuser.entities.UserFav
import com.path_studio.githubuser.models.CreateAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoriteUserFragment : Fragment() {

    private var _binding: FragmentFavoriteUserBinding? = null
    private val binding get() = _binding as FragmentFavoriteUserBinding
    private lateinit var adapter: UserFavAdapter

    companion object {
        private const val EXTRA_USER = "EXTRA_USER"
        private const val EXTRA_USER_DETAIL = "EXTRA_USER_DETAIL"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteUserBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.rvFavUser.layoutManager = LinearLayoutManager(activity)
        binding.rvFavUser.setHasFixedSize(true)
        adapter = UserFavAdapter(activity as MainActivity)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //Show Search Bar
        (activity as MainActivity).setSearchBarVisibility(1)
        (activity as MainActivity).clearSearchBar()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState == null) {
            showLoading(true)
            readDatabase()
        } else {
            val list = savedInstanceState.getParcelableArrayList<UserFav>(EXTRA_USER)
            val list_detail = savedInstanceState.getParcelableArrayList<User>(EXTRA_USER_DETAIL)
            if (list != null && list_detail != null) {
                adapter.listUser = list
                adapter.listDetailUser = list_detail
                binding.rvFavUser.adapter = adapter
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_USER, adapter.listUser)
        outState.putParcelableArrayList(EXTRA_USER_DETAIL, adapter.listDetailUser)
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

                //get data from API and set to adapter
                val listUserDetail: ArrayList<User> = ArrayList()
                for(u in users){
                    CreateAPI.create().getUserDetail(
                        u.login,
                        ProfileFragment.ACCESS_TOKEN
                    ).enqueue(object : Callback<User> {
                        override fun onResponse(call: Call<User>, response: Response<User>) {
                            if (response.isSuccessful) {
                                listUserDetail.add(response.body() as User)

                                if(listUserDetail.size == users.size) {
                                    adapter.listDetailUser = listUserDetail

                                    binding.rvFavUser.adapter = adapter

                                    binding.noData.visibility = View.GONE
                                    binding.noDataTxt.visibility = View.GONE

                                    showLoading(false)
                                }
                            }
                        }

                        override fun onFailure(call: Call<User>, error: Throwable) {
                            Log.e("tag", "The Error is: ${error.message}")
                            Utils.showFailedGetDataFromAPI(activity as MainActivity)
                        }
                    })
                }
            } else {
                adapter.listUser = ArrayList()
                binding.rvFavUser.adapter = adapter

                binding.noData.visibility = View.VISIBLE
                binding.noDataTxt.visibility = View.VISIBLE
                showLoading(false)
            }

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