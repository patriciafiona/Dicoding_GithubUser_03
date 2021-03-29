package com.path_studio.githubuser.activities

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.faltenreich.skeletonlayout.Skeleton
import com.path_studio.githubuser.R
import com.path_studio.githubuser.Utils
import com.path_studio.githubuser.adapters.ListPopularRepoAdapter
import com.path_studio.githubuser.databinding.ActivityDetailUserBinding
import com.path_studio.githubuser.fragments.ProfileFragment
import com.path_studio.githubuser.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding

    private lateinit var mainViewModel: MainViewModel
    private lateinit var detailUser: User
    private lateinit var listStarred: ArrayList<Repository>

    private lateinit var skeleton: Skeleton

    companion object {
        const val EXTRA_USER = "extra_user"
        const val MY_USERNAME = "patriciafiona"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init Skeleton
        skeleton = binding.skeletonLayout

        //init
        showLoading(true)
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        //set background animated
        setBackgroundAnimated()

        //get User Details data from API & show all data into UI
        val data = intent.getParcelableExtra<User>(EXTRA_USER) as User
        getAndSetUserData(data.login.toString())
    }

    private fun setBackgroundAnimated(){
        //Setting Gradient Animated Background
        val constraintLayout: ConstraintLayout = binding.detailUserBackgroundAnimated
        val animationDrawable = constraintLayout.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }

    private fun setOnClick(){
        binding.followersContainer.setOnClickListener {
            //Go To Detail Follow Activity
            goToDetailFollowActivity()
        }

        binding.followingsContainer.setOnClickListener {
            //Go To Detail Follow Activity
            goToDetailFollowActivity()
        }
    }

    private fun getAndSetUserData(username: String){
        mainViewModel.setUserData(this, username)

        mainViewModel.getUserData().observe(this@DetailUserActivity, {items ->
            if (items != null) {
                detailUser = items
                getAndCheckMyFollowing()
                showData(detailUser)
                getMyStarredRepository(detailUser.login.toString())
                setOnClick()
                showLoading(false)
            }
        })
    }

    private fun getAndCheckMyFollowing(){
        CreateAPI.create().getUserFollowing(
            DetailFollowActivity.USERNAME,
            ProfileFragment.ACCESS_TOKEN
        ).enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val listMyFollowing = response.body() as ArrayList<User>

                    val check = listMyFollowing.filter { it.login == detailUser.login}
                    if (check.isNotEmpty()){
                        binding.btnFollow.text = resources.getString(R.string.unfollow)
                    }else{
                        binding.btnFollow.text = resources.getString(R.string.follow)
                    }
                }
            }

            override fun onFailure(call: Call<List<User>>, error: Throwable) {
                Log.e("tag", "The Error is: ${error.message}")
                Utils.showFailedGetDataFromAPI(this@DetailUserActivity)
            }
        })
    }

    private fun goToDetailFollowActivity(){
        val i = Intent(this, DetailFollowActivity::class.java)
        i.putExtra(DetailFollowActivity.EXTRA_DETAIL_USER, detailUser)
        startActivity(i)
    }

    private fun showData(user: User){
        binding.detailUserName.text = user.name.toString()
        binding.detailUserUsername.text = user.login.toString()
        binding.detailUserLocation.text = Utils.checkEmptyValue(user.location.toString())
        binding.detailUserRepositories.text = (user.public_repos + user.owned_private_repos).toString()
        binding.detailUserCompany.text = Utils.checkEmptyValue(user.company.toString())
        binding.detailUserEmail.text = Utils.checkEmptyValue(user.email.toString())
        binding.detailUserLink.text = Utils.checkEmptyValue(user.blog.toString())
        binding.detailUserFollowers.text = Utils.convertNumberFormat(user.followers)
        binding.detailUserFollowings.text = Utils.convertNumberFormat(user.following)

        Glide.with(this)
            .load(user.avatar_url)
            .apply(RequestOptions().override(800, 800))
            .into(binding.detailUserAvatar)


        if(MY_USERNAME.equals(user.login, true)){
            binding.btnFollow.visibility = View.GONE
        }else{
            binding.btnFollow.visibility = View.VISIBLE
        }

    }

    private fun getMyStarredRepository(username: String){
        mainViewModel.setStarredRepository(this, username)

        mainViewModel.getStarredRepository().observe(this) { items ->
            if (items != null) {
                listStarred = items
                binding.detailUserStarred.text = listStarred.size.toString()

                //show popular repo using horizontal recycle view
                if(listStarred.isNotEmpty()){
                    binding.rvUsersStarredRepo.visibility = View.VISIBLE
                    binding.noData.visibility = View.GONE
                    binding.noDataTxt.visibility = View.GONE

                    showStarredRepo()
                }else{
                    //show no data icon
                    binding.rvUsersStarredRepo.visibility = View.GONE
                    binding.noData.visibility = View.VISIBLE
                    binding.noDataTxt.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun showStarredRepo(){
        val rvPopularRepo: RecyclerView = binding.rvUsersStarredRepo
        rvPopularRepo.setHasFixedSize(true)

        showRecyclerList(rvPopularRepo, listStarred)
    }

    private fun showRecyclerList(rvApp: RecyclerView, list: ArrayList<Repository>) {
        rvApp.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val listAppAdapter = ListPopularRepoAdapter(list, this)
        rvApp.adapter = listAppAdapter
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            skeleton.showSkeleton()
        } else {
            skeleton.showOriginal()
        }
    }

}