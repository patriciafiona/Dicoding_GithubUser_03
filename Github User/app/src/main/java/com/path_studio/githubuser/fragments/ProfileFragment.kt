package com.path_studio.githubuser.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.faltenreich.skeletonlayout.Skeleton
import com.path_studio.githubuser.BuildConfig
import com.path_studio.githubuser.Utils
import com.path_studio.githubuser.activities.DetailFollowActivity
import com.path_studio.githubuser.activities.MainActivity
import com.path_studio.githubuser.adapters.ListPopularRepoAdapter
import com.path_studio.githubuser.databinding.FragmentProfileBinding
import com.path_studio.githubuser.models.MainViewModel
import com.path_studio.githubuser.entities.Organization
import com.path_studio.githubuser.entities.Repository
import com.path_studio.githubuser.entities.User

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding as FragmentProfileBinding

    private lateinit var mainViewModel: MainViewModel
    private lateinit var listData: User
    private lateinit var listOrgs: ArrayList<Organization>
    private lateinit var listStarred: ArrayList<Repository>

    private lateinit var skeleton: Skeleton

    private var showDialog: Boolean = true //if one of the get method is already error, the other error won't show the Alert Dialog

    companion object {
        const val MY_USERNAME = "patriciafiona"
        const val ACCESS_TOKEN = "token " + BuildConfig.GITHUB_API_KEY
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        //init Skeleton
        skeleton = binding.skeletonLayout

        //show loading indicator
        showLoading(true)

        //init Main view model
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        //get Data From API
        getAndShowDataFromAPI()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //Hide Search Bar
        (activity as MainActivity).setSearchBarVisibility(0)
        (activity as MainActivity).clearSearchBar()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getAndShowDataFromAPI(){
        showDialog = mainViewModel.setStarredRepository(activity as MainActivity, MY_USERNAME, showDialog)
        showDialog = mainViewModel.setUserData(activity as MainActivity, MY_USERNAME, showDialog)
        showDialog = mainViewModel.setUserOrganization(activity as MainActivity, showDialog)

        mainViewModel.getStarredRepository().observe(activity as MainActivity) { items ->
            if (items != null) {
                listStarred = items
                binding.myStarred.text = listStarred.size.toString()
                showPopularRepo()
            }
        }

        mainViewModel.getUserData().observe(activity as MainActivity, {items ->
            if (items != null) {
                listData = items
                showData()
                setOnClick()
                showLoading(false)
            }
        })

        mainViewModel.getListOrganizations().observe(activity as MainActivity, {items ->
            if (items != null) {
                listOrgs = items
                binding.myOrganizations.text = listOrgs.size.toString()
            }
        })

    }

    private fun showData(){
        binding.myName.text = listData.name.toString()
        binding.myUsername.text = listData.login.toString()
        binding.myCompany.text = Utils.checkEmptyValue(listData.company.toString())
        binding.myLocation.text = Utils.checkEmptyValue(listData.location.toString())
        binding.myLink.text = Utils.checkEmptyValue(listData.blog.toString())
        binding.myFollowers.text = Utils.convertNumberFormat(listData.followers)
        binding.myFollowings.text = Utils.convertNumberFormat(listData.following)
        binding.myBio.text = Utils.checkEmptyValue(listData.bio.toString())

        //show total repo
        val totalRepo = listData.public_repos + listData.owned_private_repos
        binding.myRepository.text = totalRepo.toString()

        Glide.with((activity as MainActivity).applicationContext)
            .load(listData.avatar_url)
            .apply(RequestOptions().override(500, 500))
            .into(binding.myAvatar)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            skeleton.showSkeleton()
        } else {
            skeleton.showOriginal()
        }
    }

    private fun showPopularRepo(){
        val rvPopularRepo: RecyclerView = binding.rvPopularRepo
        rvPopularRepo.setHasFixedSize(true)

        showRecyclerList(rvPopularRepo, listStarred)
    }

    private fun showRecyclerList(rv: RecyclerView, list: ArrayList<Repository>) {
        rv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        val listAppAdapter = ListPopularRepoAdapter(list, activity as MainActivity)
        rv.adapter = listAppAdapter
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

    private fun goToDetailFollowActivity(){
        val i = Intent(activity, DetailFollowActivity::class.java)
        i.putExtra(DetailFollowActivity.EXTRA_DETAIL_USER, listData)
        startActivity(i)
    }

}