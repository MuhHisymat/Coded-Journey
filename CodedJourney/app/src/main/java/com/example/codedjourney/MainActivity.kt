package com.example.codedjourney

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.codedjourney.adapter.ViewPagerAdapter
import com.example.codedjourney.databinding.ActivityMainBinding
import com.example.codedjourney.fragment.HomeFragment
import com.example.codedjourney.fragment.PodcastFragment
import com.example.codedjourney.fragment.UserFragment


class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupTab()
    }

    private fun setupTab() {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(HomeFragment(),"Home")
        adapter.addFragment(PodcastFragment(),"Podcast")
        adapter.addFragment(UserFragment(),"Profile")

        binding.viewPager.adapter = adapter
        binding.tabs.setupWithViewPager(binding.viewPager)

        binding.tabs.getTabAt(0)!!.setIcon(R.drawable.ic_home)
        binding.tabs.getTabAt(1)!!.setIcon(R.drawable.ic_song)
        binding.tabs.getTabAt(2)!!.setIcon(R.drawable.ic_user)
    }
}