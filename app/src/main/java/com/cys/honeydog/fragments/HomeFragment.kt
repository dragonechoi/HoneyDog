package com.cys.honeydog.fragments

import android.content.ClipData.Item
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cys.honeydog.activities.CatCmmActivity
import com.cys.honeydog.activities.DogCmmActivity
import com.cys.honeydog.adapters.MiniCmtItemAdapter
import com.cys.honeydog.databinding.FragmentHomeBinding
import com.cys.honeydog.model.MiniCmtItem


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

     var  item:MutableList<MiniCmtItem> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root




    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        item.add(MiniCmtItem("오늘 강아지와 함꺠 수원 나들이ddddddddddddddddddddddddddd"))
        item.add(MiniCmtItem("오늘 강아지와 함꺠 부산 나들이"))
        item.add(MiniCmtItem("오늘 강아지와 함꺠 광주 나들이"))
        item.add(MiniCmtItem("오늘 강아지와 함꺠 부산 나들이"))
        item.add(MiniCmtItem("오늘 강아지와 함꺠 광주 나들이"))
        item.add(MiniCmtItem("오늘 강아지와 함꺠 광주 나들이"))
        item.add(MiniCmtItem("오늘 강아지와 함꺠 광주 나들이"))
        item.add(MiniCmtItem("오늘 강아지와 함꺠 광주 나들이"))
        item.add(MiniCmtItem("오늘 강아지와 함꺠 광주 나들이"))

        binding.recyclerTitle.adapter=MiniCmtItemAdapter(requireContext(),item)

        binding.tvGoCmm.setOnClickListener {
            val intent = Intent(requireContext(), CatCmmActivity::class.java)
            startActivity(intent)

            binding.mainDogCommunity.setOnClickListener {
             val  intent =Intent(requireContext(),DogCmmActivity::class.java)
                startActivity(intent)
            }
            binding.mainCatCommunity.setOnClickListener {
                val intent2 = Intent(requireContext(),CatCmmActivity::class.java)
                startActivity(intent2)
            }


        }
    }
}