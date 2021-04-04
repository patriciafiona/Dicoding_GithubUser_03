package com.path_studio.consumer_app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.path_studio.consumer_app.BuildConfig
import com.path_studio.consumer_app.adapters.UserFavAdapter
import com.path_studio.consumer_app.databinding.FragmentFavoriteUserBinding


class FavoriteUserFragment : Fragment() {

    private var _binding: FragmentFavoriteUserBinding? = null
    private val binding get() = _binding as FragmentFavoriteUserBinding
    private lateinit var adapter: UserFavAdapter

    companion object {
        private const val EXTRA_USER_DETAIL = "EXTRA_USER_DETAIL"
        private const val EXTRA_USER_FAV = "EXTRA_USER_FAV"
        const val ACCESS_TOKEN = "token " + BuildConfig.GITHUB_API_KEY
        const val MY_USERNAME = "patriciafiona"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteUserBinding.inflate(inflater, container, false)
        val view = binding.root

        /*binding.rvFavUser.layoutManager = LinearLayoutManager(activity)
        binding.rvFavUser.setHasFixedSize(true)
        adapter = UserFavAdapter(activity as MainActivity)*/
        return view
    }

    /*override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState == null) {
            showLoading(true)
            readDatabase()
        } else {
            val listUserFav = savedInstanceState.getParcelableArrayList<UserFav>(EXTRA_USER_FAV)
            val listDetail = savedInstanceState.getParcelableArrayList<User>(EXTRA_USER_DETAIL)
            checkCurrentData(listUserFav, listDetail)
        }
    }

    override fun onStop() {
        super.onStop()
        (activity as MainActivity).intent.putExtra("EXTRA_FF_USER_FAV", adapter.listUserDb)
        (activity as MainActivity).intent.putExtra("EXTRA_FF_USER_DETAIL", adapter.listDetailUser)
    }

    override fun onResume() {
        super.onResume()
        val listUserFav = (activity as MainActivity).intent.getParcelableArrayListExtra<UserFav>("EXTRA_FF_USER_FAV")
        val listDetail = (activity as MainActivity).intent.getParcelableArrayListExtra<User>("EXTRA_FF_USER_DETAIL")
        checkCurrentData(listUserFav, listDetail)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_USER_DETAIL, adapter.listDetailUser)
        outState.putParcelableArrayList(EXTRA_USER_FAV, adapter.listUserDb)
    }

    private  fun checkCurrentData(listUserFav: ArrayList<UserFav>?, listDetail: ArrayList<User>?) {
        runBlocking {
            val currentData = getCurrentUserFav()
            if (listUserFav != null) {
                //check if current data in db change or not
                if(currentData.containsAll(listUserFav) && listUserFav.containsAll(currentData) ){
                    if (listDetail != null) {
                        adapter = UserFavAdapter(activity as MainActivity)
                        Collections.sort(listDetail, CategoriesComparator())
                        adapter.listDetailUser =listDetail
                        binding.rvFavUser.adapter = adapter

                        binding.noData.visibility = View.GONE
                        binding.noDataTxt.visibility = View.GONE
                    }
                }else{
                    showLoading(true)
                    readDatabase()
                }
            }
        }
    }

    private fun readDatabase(){
        runBlocking {
            val users = getCurrentUserFav()
            adapter.listUserDb = users

            if (users.size > 0) {
                //get data from API and set to adapter
                val listUserDetail: ArrayList<User> = ArrayList()
                for(u in users){
                    CreateAPI.create().getUserDetail(
                        u.login,
                        ACCESS_TOKEN
                    ).enqueue(object : Callback<User> {
                        override fun onResponse(call: Call<User>, response: Response<User>) {
                            if (response.isSuccessful) {
                                listUserDetail.add(response.body() as User)

                                if (listUserDetail.size == users.size) {
                                    Collections.sort(listUserDetail, CategoriesComparator())
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
                adapter.listDetailUser = ArrayList()
                binding.rvFavUser.adapter = adapter

                binding.noData.visibility = View.VISIBLE
                binding.noDataTxt.visibility = View.VISIBLE
                showLoading(false)
            }

        }

    }

    private suspend fun getCurrentUserFav(): ArrayList<UserFav> = coroutineScope{
        val userHelper = UserHelper.getInstance((activity as MainActivity).applicationContext)
        userHelper.open()

        val deferredUsers = async(Dispatchers.IO) {
            val cursor = userHelper.queryAll()
            MappingHelper.mapCursorToArrayList(cursor)
        }

        val users = deferredUsers.await()
        userHelper.close()

        users
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }*/

}