package com.path_studio.githubuser.fragments

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.path_studio.githubuser.activities.AboutActivity
import com.path_studio.githubuser.activities.MainActivity
import com.path_studio.githubuser.activities.NotificationActivity
import com.path_studio.githubuser.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding as FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val view = binding.root

        //set Onclick on Settings Menu
        setSettingsMenuListener()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //Hide Search Bar
        (activity as MainActivity).setSearchBarVisibility(0)
        (activity as MainActivity).clearSearchBar()
    }

    private fun setSettingsMenuListener(){
        //For Account Menu
        binding.textAccount.setOnClickListener {
            val i = Intent(activity, NotificationActivity::class.java)
            startActivity(i)
        }

        //For About Menu
        binding.textAbout.setOnClickListener {
            val i = Intent(activity, AboutActivity::class.java)
            startActivity(i)
        }

        //For Language Menu
        binding.textLanguage.setOnClickListener {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}