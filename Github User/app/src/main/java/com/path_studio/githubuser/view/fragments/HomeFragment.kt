package com.path_studio.githubuser.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.faltenreich.skeletonlayout.Skeleton
import com.path_studio.githubuser.Utils.showFailedGetDataFromAPI
import com.path_studio.githubuser.databinding.FragmentHomeBinding
import com.path_studio.githubuser.view.activities.MainActivity


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding as FragmentHomeBinding

    private lateinit var skeleton: Skeleton

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        //init Skeleton
        skeleton = binding.skeletonLayout

        //show loading indicator
        showLoading(true)

        setGithubContribution()

        return view
    }

    private fun setGithubContribution(){
        binding.contributorImg.visibility = View.GONE

        val myWebView: WebView = binding.contributorImg
        myWebView.loadUrl("https://ghchart.rshah.org/patriciafiona")

        myWebView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                showLoading(false)
                binding.contributorImg.visibility = View.VISIBLE
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                showFailedGetDataFromAPI(activity as MainActivity)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //Show Search Bar
        (activity as MainActivity).setSearchBarVisibility(1)
        (activity as MainActivity).clearSearchBar()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            skeleton.showSkeleton()
            binding.progressBar.visibility = View.VISIBLE
        } else {
            skeleton.showOriginal()
            binding.progressBar.visibility = View.GONE
        }
    }

}