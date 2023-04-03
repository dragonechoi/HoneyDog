package com.cys.honeydog.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cys.honeydog.R
import com.cys.honeydog.databinding.FragmentProfilMainBinding
import com.cys.honeydog.databinding.FragmentSearchMainBinding

class SearchMainFragment: Fragment() {
    private lateinit var Binding: FragmentSearchMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Binding = FragmentSearchMainBinding.inflate(inflater, container, false)

        return Binding.root
    }
}