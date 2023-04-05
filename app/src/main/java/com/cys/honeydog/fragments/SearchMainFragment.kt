package com.cys.honeydog.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cys.honeydog.R
import com.cys.honeydog.adapters.SearchFragmentAdapter
import com.cys.honeydog.databinding.FragmentProfileMainBinding
import com.cys.honeydog.databinding.FragmentSearchMainBinding
import com.cys.honeydog.model.MiniCmtItem
import com.cys.honeydog.model.SearchItem

class SearchMainFragment: Fragment() {
    private lateinit var Binding: FragmentSearchMainBinding
    var  item:MutableList<SearchItem> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? { Binding = FragmentSearchMainBinding.inflate(inflater, container, false)

        return Binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        item.add(SearchItem(R.drawable.cogi_love,"24시 반려동물 응급 수술 전문","031-000-000","경기도 수원시 권선구 탑동"))
        item.add(SearchItem(R.drawable.cogi_love,"24시 반려동물 응급 수술 전문","031-000-000","경기도 수원시 권선구 구운동"))
        item.add(SearchItem(R.drawable.cogi_love,"24시 반려동물 응급 수술 전문","031-000-000","경기도 수원시 권선구 서둔동"))
        item.add(SearchItem(R.drawable.cogi_love,"24시 반려동물 응급 수술 전문","031-000-000","경기도 수원시 권선구 칠보"))
        item.add(SearchItem(R.drawable.cogi_love,"24시 반려동물 응급 수술 전문","031-000-000","경기도 수원시 권선구 화서동"))
        item.add(SearchItem(R.drawable.cogi_love,"24시 반려동물 응급 수술 전문","031-000-000","경기도 수원시 권선구 당수동"))
        item.add(SearchItem(R.drawable.cogi_love,"24시 반려동물 응급 수술 전문","031-000-000","경기도 수원시 권선구 호메실동"))
        item.add(SearchItem(R.drawable.cogi_love,"24시 반려동물 응급 수술 전문","031-000-000","경기도 수원시 권선구 매탄동"))
        item.add(SearchItem(R.drawable.cogi_love,"24시 반려동물 응급 수술 전문","031-000-000","경기도 수원시 권선구 권선동"))
        item.add(SearchItem(R.drawable.cogi_love,"24시 반려동물 응급 수술 전문","031-000-000","경기도 수원시 권선구 세류동"))
        item.add(SearchItem(R.drawable.cogi_love,"24시 반려동물 응급 수술 전문","031-000-000","경기도 수원시 권선구 세류동"))
        item.add(SearchItem(R.drawable.cogi_love,"24시 반려동물 응급 수술 전문","031-000-000","경기도 수원시 권선구 세류동"))
        item.add(SearchItem(R.drawable.cogi_love,"24시 반려동물 응급 수술 전문","031-000-000","경기도 수원시 권선구 세류동"))
        item.add(SearchItem(R.drawable.cogi_love,"24시 반려동물 응급 수술 전문","031-000-000","경기도 수원시 권선구 세류동"))
        item.add(SearchItem(R.drawable.cogi_love,"24시 반려동물 응급 수술 전문","031-000-000","경기도 수원시 권선구 세류동"))
        item.add(SearchItem(R.drawable.cogi_love,"24시 반려동물 응급 수술 전문","031-000-000","경기도 수원시 권선구 세류동"))
        item.add(SearchItem(R.drawable.cogi_love,"24시 반려동물 응급 수술 전문","031-000-000","경기도 수원시 권선구 세류동"))

        Binding.recyclerSearch.adapter=SearchFragmentAdapter(requireContext(),item)
    }
}