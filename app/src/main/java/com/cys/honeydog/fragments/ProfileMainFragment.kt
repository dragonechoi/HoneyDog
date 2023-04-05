package com.cys.honeydog.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cys.honeydog.R
import com.cys.honeydog.activities.CatCmmActivity
import com.cys.honeydog.activities.ProfileSettingActivity
import com.cys.honeydog.adapters.ProfilFragmentAdapter
import com.cys.honeydog.databinding.FragmentProfileMainBinding
import com.cys.honeydog.model.ProfilRecyclerItem

class ProfileMainFragment: Fragment() {
    private lateinit var Binding: FragmentProfileMainBinding
    var item:MutableList<ProfilRecyclerItem> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? { Binding = FragmentProfileMainBinding.inflate(inflater, container, false)
        return Binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        item.add(ProfilRecyclerItem("게시글 작성 리사이클러뷰 테스트",R.drawable.cogi_love))
        item.add(ProfilRecyclerItem("게시글 작성 리사이클러뷰 테스트",R.drawable.cogi_love))
        item.add(ProfilRecyclerItem("게시글 작성 리사이클러뷰 테스트",R.drawable.cogi_love))
        item.add(ProfilRecyclerItem("게시글 작성 리사이클러뷰 테스트",R.drawable.cogi_love))
        item.add(ProfilRecyclerItem("게시글 작성 리사이클러뷰 테스트",R.drawable.cogi_love))
        item.add(ProfilRecyclerItem("게시글 작성 리사이클러뷰 테스트",R.drawable.cogi_love))
        item.add(ProfilRecyclerItem("게시글 작성 리사이클러뷰 테스트",R.drawable.cogi_love))
        item.add(ProfilRecyclerItem("게시글 작성 리사이클러뷰 테스트",R.drawable.cogi_love))
        item.add(ProfilRecyclerItem("게시글 작성 리사이클러뷰 테스트",R.drawable.cogi_love))
        item.add(ProfilRecyclerItem("게시글 작성 리사이클러뷰 테스트",R.drawable.cogi_love))
        item.add(ProfilRecyclerItem("게시글 작성 리사이클러뷰 테스트",R.drawable.cogi_love))
        item.add(ProfilRecyclerItem("게시글 작성 리사이클러뷰 테스트",R.drawable.cogi_love))
        item.add(ProfilRecyclerItem("게시글 작성 리사이클러뷰 테스트",R.drawable.cogi_love))
        item.add(ProfilRecyclerItem("게시글 작성 리사이클러뷰 테스트",R.drawable.cogi_love))
        item.add(ProfilRecyclerItem("게시글 작성 리사이클러뷰 테스트",R.drawable.cogi_love))
        item.add(ProfilRecyclerItem("게시글 작성 리사이클러뷰 테스트",R.drawable.cogi_love))
        item.add(ProfilRecyclerItem("게시글 작성 리사이클러뷰 테스트",R.drawable.cogi_love))
        item.add(ProfilRecyclerItem("게시글 작성 리사이클러뷰 테스트",R.drawable.cogi_love))
        item.add(ProfilRecyclerItem("게시글 작성 리사이클러뷰 테스트",R.drawable.cogi_love))

        Binding.profileChange.setOnClickListener {
            val intent = Intent(requireContext(), ProfileSettingActivity::class.java)
            startActivity(intent)
        }

        Binding.profilRecycler.adapter=ProfilFragmentAdapter(requireContext(),item)
    }

}