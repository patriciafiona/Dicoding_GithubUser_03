package com.path_studio.githubuser.models

import android.app.Notification
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.path_studio.consumer_app.Utils
import com.path_studio.consumer_app.entities.Organization
import com.path_studio.consumer_app.entities.Repository
import com.path_studio.consumer_app.entities.SearchRepo
import com.path_studio.consumer_app.entities.User
import com.path_studio.consumer_app.fragments.FavoriteUserFragment.Companion.ACCESS_TOKEN
import com.path_studio.consumer_app.fragments.FavoriteUserFragment.Companion.MY_USERNAME
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    private val trendingRepository = MutableLiveData<SearchRepo>()
    private val listStarredRepo = MutableLiveData<ArrayList<Repository>>()
    private val listOrgs = MutableLiveData<ArrayList<Organization>>()
    private val userData = MutableLiveData<User>()
    private val listNotification = MutableLiveData<ArrayList<Notification>>()
    private val listFollowing = MutableLiveData<ArrayList<User>>()
    private val listFollowers = MutableLiveData<ArrayList<User>>()

    //SETTER
    fun setTrendingRepository(context: Context){
        //get last week date
        val lastWeek:String = Utils.getDaysAgo(7)
        val query = "created:>$lastWeek"

        CreateAPI.create().getTrendingRepo(query, ACCESS_TOKEN).enqueue(object :
            Callback<SearchRepo> {
            override fun onResponse(
                call: Call<SearchRepo>,
                response: Response<SearchRepo>
            ) {
                if (response.isSuccessful) {
                    trendingRepository.postValue(response.body() as SearchRepo)
                }
            }

            override fun onFailure(call: Call<SearchRepo>, t: Throwable) {
                Log.e("tag", "The Error is: ${t.message}")
                Utils.showFailedGetDataFromAPI(context)
            }

        })
    }

    fun setStarredRepository(context: Context, username: String, showDialog: Boolean): Boolean{
        var status = false
        CreateAPI.create().getUserStarredRepositories(
            username,
           ACCESS_TOKEN
        ).enqueue(object : Callback<List<Repository>> {
            override fun onResponse(
                call: Call<List<Repository>>,
                response: Response<List<Repository>>
            ) {
                if (response.isSuccessful) {
                    listStarredRepo.postValue(response.body() as ArrayList<Repository>)
                    status = true
                }
            }

            override fun onFailure(call: Call<List<Repository>>, t: Throwable) {
                Log.e("tag", "The Error is: ${t.message}")

                if(showDialog){
                    Utils.showFailedGetDataFromAPI(context)
                    status = false
                }
            }

        })
        return status
    }

    fun setStarredRepository(context: Context, username: String){
        CreateAPI.create().getUserStarredRepositories(
            username,
            ACCESS_TOKEN
        ).enqueue(object : Callback<List<Repository>> {
            override fun onResponse(
                call: Call<List<Repository>>,
                response: Response<List<Repository>>
            ) {
                if (response.isSuccessful) {
                    listStarredRepo.postValue(response.body() as ArrayList<Repository>)
                }
            }

            override fun onFailure(call: Call<List<Repository>>, t: Throwable) {
                Log.e("tag", "The Error is: ${t.message}")
                Utils.showFailedGetDataFromAPI(context)
            }

        })
    }

    fun setUserData(context: Context, username: String, showDialog: Boolean): Boolean{
        var status = false
        CreateAPI.create().getUserDetail(
            username,
            ACCESS_TOKEN
        ).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    userData.postValue(response.body() as User)
                    status = true
                }
            }

            override fun onFailure(call: Call<User>, error: Throwable) {
                Log.e("tag", "The Error is: ${error.message}")

                if(showDialog){
                    Utils.showFailedGetDataFromAPI(context)
                    status = false
                }
            }
        })
        return status
    }

    fun setUserData(context: Context, username: String){
        CreateAPI.create().getUserDetail(
            username,
            ACCESS_TOKEN
        ).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    userData.postValue(response.body() as User)
                }
            }

            override fun onFailure(call: Call<User>, error: Throwable) {
                Log.e("tag", "The Error is: ${error.message}")
                Utils.showFailedGetDataFromAPI(context)
            }
        })
    }

    fun setUserOrganization(context: Context, showDialog: Boolean): Boolean{
        var status = false
        CreateAPI.create().getUserOrganizations(
            MY_USERNAME,
            ACCESS_TOKEN
        ).enqueue(object : Callback<List<Organization>> {
            override fun onResponse(
                call: Call<List<Organization>>,
                response: Response<List<Organization>>
            ) {
                if (response.isSuccessful) {
                    listOrgs.postValue(response.body() as ArrayList<Organization>)
                    status = true
                }
            }

            override fun onFailure(call: Call<List<Organization>>, t: Throwable) {
                Log.e("tag", "The Error is: ${t.message}")

                if(showDialog){
                    Utils.showFailedGetDataFromAPI(context)
                    status = false
                }
            }

        })
        return status
    }

    fun setNotification(context: Context){
        CreateAPI.create().getMyNotifications(ACCESS_TOKEN).enqueue(object :
            Callback<List<Notification>> {
            override fun onResponse(
                call: Call<List<Notification>>,
                response: Response<List<Notification>>
            ) {
                if (response.isSuccessful) {
                    listNotification.postValue(response.body() as ArrayList<Notification>)
                }
            }

            override fun onFailure(call: Call<List<Notification>>, t: Throwable) {
                Log.e("tag", "The Error is: ${t.message}")
                Utils.showFailedGetDataFromAPI(context)
            }

        })
    }

    fun setFollowing(context: Context, username: String){
        CreateAPI.create().getUserFollowing(
            username,
            ACCESS_TOKEN
        ).enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    listFollowing.postValue(response.body() as ArrayList<User>)
                }
            }

            override fun onFailure(call: Call<List<User>>, error: Throwable) {
                Log.e("tag", "The Error is: ${error.message}")
                Utils.showFailedGetDataFromAPI(context)
            }
        })
    }

    fun setFollowers(context: Context, username: String){
        CreateAPI.create().getUserFollowers(
            username,
            ACCESS_TOKEN
        ).enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    listFollowers.postValue(response.body() as ArrayList<User>)
                }
            }

            override fun onFailure(call: Call<List<User>>, error: Throwable) {
                Log.e("tag", "The Error is: ${error.message}")
                Utils.showFailedGetDataFromAPI(context)
            }
        })
    }
    //--------------------------------------------------------------------------------------------
    //GETTER
    fun getTrendingFromAPI(): LiveData<SearchRepo> {
        return trendingRepository
    }

    fun getStarredRepository(): LiveData<ArrayList<Repository>>{
        return listStarredRepo
    }

    fun getUserData(): LiveData<User>{
        return userData
    }

    fun getListOrganizations(): LiveData<ArrayList<Organization>>{
        return listOrgs
    }

    fun getNotifications(): LiveData<ArrayList<Notification>>{
        return listNotification
    }

    fun getFollowing(): LiveData<ArrayList<User>>{
        return listFollowing
    }

    fun getFollowers(): LiveData<ArrayList<User>>{
        return listFollowers
    }

}