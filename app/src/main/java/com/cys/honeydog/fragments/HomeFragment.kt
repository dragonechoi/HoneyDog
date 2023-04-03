package com.cys.honeydog.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cys.honeydog.R
import com.cys.honeydog.databinding.FragmentHomeBinding


class HomeFragment: Fragment() {
    private lateinit var Binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Binding = FragmentHomeBinding.inflate(inflater, container, false)

        return Binding.root
    }
}


