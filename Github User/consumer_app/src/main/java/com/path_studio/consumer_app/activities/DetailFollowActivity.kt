package com.path_studio.consumer_app.activities

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.path_studio.consumer_app.R
import com.path_studio.consumer_app.adapters.FollowTabsAdapter
import com.path_studio.consumer_app.databinding.ActivityDetailFollowBinding
import com.path_studio.consumer_app.entities.User

class DetailFollowActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailFollowBinding

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.following,
            R.string.follower
        )

        const val EXTRA_DETAIL_USER = "EXTRA_DETAIL_USERNAME"
        var USERNAME = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailFollowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //get Extra data & Show Data
        showData()

        //set Tab
        setTab()
    }

    private  fun showData(){
        //Get data from Extra
        val data = intent.getParcelableExtra<User>(EXTRA_DETAIL_USER) as User
        val getName = data.name.toString()
        USERNAME = data.login.toString()
        val getAvatar = data.avatar_url.toString()
        val getFollowing = data.following
        val getFollowers = data.followers

        //show data
        binding.userName.text = getName
        binding.userUsername.text = USERNAME
        binding.detailUserFollowers.text = getFollowers.toString()
        binding.detailUserFollowings.text = getFollowing.toString()

        Glide.with(this)
            .load(getAvatar)
            .apply(RequestOptions().override(800, 800))
            .into(binding.userAvatar)
    }

    private fun setTab(){
        val sectionsPagerAdapter = FollowTabsAdapter(this)
        val viewPager: ViewPager2 = binding.followViewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.followTabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

}