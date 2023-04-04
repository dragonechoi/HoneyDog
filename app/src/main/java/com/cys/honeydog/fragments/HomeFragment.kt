package com.cys.honeydog.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cys.honeydog.activities.DogCmmActivity
import com.cys.honeydog.databinding.FragmentHomeBinding



class HomeFragment : Fragment() {
     private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root





    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvGoCmm.setOnClickListener { startActivity(Intent(requireContext(),DogCmmActivity::class.java)) }

     }





}