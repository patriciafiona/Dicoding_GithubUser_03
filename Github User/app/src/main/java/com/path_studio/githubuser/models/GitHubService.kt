package com.path_studio.githubuser.models

import com.path_studio.githubuser.entities.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubService {
    //Get User Data and Details
    @GET("users/{username}")
    fun getUserDetail(
        @Path("username") username: String?,
        @Header("Authorization") accessToken: String
    ): Call<User>

    // Get Users public repositories
    @GET("users/{username}/repos?per_page=100")
    fun getUserPublicRepositories(
        @Path("username") username: String?,
        @Header("Authorization") accessToken: String
    ): Call<List<Repository>>

    //Get User Starred Repositories
    @GET("users/{username}/starred?per_page=100")
    fun getUserStarredRepositories(
        @Path("username") username: String?,
        @Header("Authorization") accessToken: String
    ): Call<List<Repository>>

    //Get User Organization
    @GET("users/{username}/orgs")
    fun getUserOrganizations(
        @Path("username") username: String?,
        @Header("Authorization") accessToken: String
    ): Call<List<Organization>>

    //Get Search User Result List
    @GET("search/users")
    fun getSearchByUsername(
            @Query("q") username: String?,
            @Header("Authorization") accessToken: String
    ): Call<Search>

    //getList followers
    @GET("users/{username}/followers?per_page=100")
    fun getUserFollowers(
        @Path("username") username: String?,
        @Header("Authorization") accessToken: String
    ): Call<List<User>>

    //getList following
    @GET("users/{username}/following?per_page=100")
    fun getUserFollowing(
        @Path("username") username: String?,
        @Header("Authorization") accessToken: String
    ): Call<List<User>>

    //get My Notifications
    @GET("notifications?per_page=20")
    fun getMyNotifications(
            @Header("Authorization") accessToken: String
    ): Call<List<Notification>>

    //get My Notifications
    @GET("search/repositories?sort=stars&order=desc&per_page=20")
    fun getTrendingRepo(
        @Query("q") created: String?,
        @Header("Authorization") accessToken: String
    ): Call<SearchRepo>
}