package com.example.codedjourney.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.viewpager2.widget.ViewPager2
import com.example.codedjourney.LoginActivity
import com.example.codedjourney.Play
import com.example.codedjourney.R
import com.example.codedjourney.adapter.VideoAdapter
import com.example.codedjourney.databinding.FragmentHomeBinding
import com.example.codedjourney.databinding.FragmentUserBinding
import com.example.codedjourney.model.VideoModel
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.*

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnTT.setOnClickListener {
            btntt()
        }
    }

    private fun btntt() {
        val intent = Intent(context, Play::class.java)
        startActivity(intent)
        activity?.finish()
    }
}


