package com.path_studio.githubuser.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.path_studio.githubuser.R
import com.path_studio.githubuser.activities.MainActivity
import com.path_studio.githubuser.adapters.ListTrendingRepoAdapter
import com.path_studio.githubuser.databinding.FragmentExploreBinding
import com.path_studio.githubuser.models.MainViewModel
import com.path_studio.githubuser.models.SearchRepo

class ExploreFragment : Fragment() {

    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding as FragmentExploreBinding

    private lateinit var mainViewModel: MainViewModel
    private lateinit var trendingRepo: SearchRepo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        val view = binding.root

        showLoading(true)

        //init Main view model
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        //Set Animation
        settingAnimation()

        //Get data & set trending recycle view
        getTrendingFromAPI()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //Show Search Bar
        (activity as MainActivity).setSearchBarVisibility(1)
        (activity as MainActivity).clearSearchBar()
    }

    private fun settingAnimation(){
        //Setting trending banner image
        val image: ImageView = binding.trendingBannerImg
        val fadeImgAnim: Animation = AnimationUtils.loadAnimation((activity as MainActivity).applicationContext, R.anim.fade_in)
        image.startAnimation(fadeImgAnim)

        //Setting trending banner Text
        val text: TextView = binding.trendingBannerText
        val fadeTxtAnim: Animation = AnimationUtils.loadAnimation((activity as MainActivity).applicationContext, R.anim.fade_in_and_slide)
        fadeTxtAnim.startOffset = 500
        text.startAnimation(fadeTxtAnim)
    }

    private fun getTrendingFromAPI(){
        mainViewModel.setTrendingRepository(activity as MainActivity)

        mainViewModel.getTrendingFromAPI().observe(activity as MainActivity, { items ->
            if (items != null) {
                trendingRepo = items
                showRV()
                showLoading(false)
            }
        })
    }

    private fun showRV(){
        val rvTrending: RecyclerView = binding.rvDiscover
        rvTrending.setHasFixedSize(true)

        showRecyclerList(rvTrending, trendingRepo)
    }

    private fun showRecyclerList(rv: RecyclerView, list: SearchRepo) {
        rv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        val listAppAdapter = ListTrendingRepoAdapter(list, activity as MainActivity)
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