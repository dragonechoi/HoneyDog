package com.cys.honeydog.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cys.honeydog.activities.CatCmmActivity
import com.cys.honeydog.activities.DogCmmActivity
import com.cys.honeydog.adapters.MiniCmtItemAdapter
import com.cys.honeydog.databinding.FragmentHomeBinding
import com.cys.honeydog.model.MiniCmtItem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    var item: MutableList<MiniCmtItem> = mutableListOf()
    lateinit var adapter: MiniCmtItemAdapter

    companion object {
        var commentNum = 0
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 어댑터 초기화
        adapter = MiniCmtItemAdapter(requireContext(), item)
        binding.recyclerTitle.adapter = adapter

        loadData()
        clickCmmBtn()

        binding.tvGoCmm.setOnClickListener {
            val intent = Intent(requireContext(), DogCmmActivity::class.java)
            startActivity(intent)
        }
    }

    fun clickCmmBtn() {
        binding.mainDogCommunity.setOnClickListener {
            val intent = Intent(requireActivity(), DogCmmActivity::class.java)
            startActivity(intent)
        }
        binding.mainCatCommunity.setOnClickListener {
            val intent2 = Intent(requireContext(), CatCmmActivity::class.java)
            startActivity(intent2)

        }
    }

    private fun loadData() {
        val fireStore = FirebaseFirestore.getInstance()
        val postRef = fireStore.collection("Post")
            .orderBy("no", Query.Direction.DESCENDING)

        //Post 컬렉션데이터 호출
        postRef.get().addOnSuccessListener { documents ->
            for (document in documents) {
                val post = document.toObject(MiniCmtItem::class.java)
                item.add(post)
            }
            // 데이터를 모두 가져온 후 어댑터 설정
            adapter.notifyDataSetChanged()
        }.addOnFailureListener {
            // 호출 실패 시 처리할 내용
        }
    }
}