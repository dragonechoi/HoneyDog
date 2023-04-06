package com.cys.honeydog.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cys.honeydog.R
import com.cys.honeydog.adapters.DogCmmAdapter
import com.cys.honeydog.databinding.ActivityDogCmmBinding
import com.cys.honeydog.model.DogCmmItem

class DogCmmActivity : AppCompatActivity() {
    val binding: ActivityDogCmmBinding by lazy { ActivityDogCmmBinding.inflate(layoutInflater) }
    var item: MutableList<DogCmmItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


//        item.add(DogCmmItem(R.drawable.dog_community_header, "오늘 강아지랑 // 다녀왔어요", "또리맘"))
//        item.add(DogCmmItem(R.drawable.dog_community_header, "오늘 강아지랑 // 다녀왔어요", "콩이맘"))
//        item.add(DogCmmItem(R.drawable.dog_community_header, "오늘 강아지랑 // 다녀왔어요", "희망"))
//        item.add(DogCmmItem(R.drawable.dog_community_header, "오늘 강아지랑 // 다녀왔어요", "태산"))
//        item.add(DogCmmItem(R.drawable.dog_community_header, "오늘 강아지랑 ㅇㅇ 다녀왔어요", "사랑"))
//        item.add(DogCmmItem(R.drawable.dog_community_header, "오늘 강아지랑 빠지 다녀왔어요", "믿음"))
//        item.add(DogCmmItem(R.drawable.dog_community_header, "오늘 강아지랑 애견카페 다녀왔어요", "소망"))
//        item.add(DogCmmItem(R.drawable.dog_community_header, "오늘 강아지랑 시골 다녀왔어요", "22"))
//        item.add(DogCmmItem(R.drawable.dog_community_header, "오늘 강아지랑 해외 여행 다녀왔어요", "11"))
//        item.add(DogCmmItem(R.drawable.dog_community_header, "오늘 강아지랑 기차여행 다녀왔어요", "11"))
//        item.add(DogCmmItem(R.drawable.dog_community_header, "오늘 강아지랑 식당 다녀왔어요", "11"))
//        item.add(DogCmmItem(R.drawable.dog_community_header, "오늘 강아지랑 공원 다녀왔어요", "11"))
//        item.add(DogCmmItem(R.drawable.dog_community_header, "오늘 강아지랑 여행 다녀왔어요", "11"))
//        item.add(DogCmmItem(R.drawable.dog_community_header, "오늘 강아지랑 병원 다녀왔어요", "11"))

        binding.recyclerDogCmm.adapter = DogCmmAdapter(this, item)

        binding.editPost.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    NewPostActivity::class.java
                )
            )
        }
        binding.communityIntentBtn.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    CatCmmActivity::class.java
                )
            )
        }
        binding.goHome.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
    }

    override fun onResume() {
        super.onResume()
    }

   private fun loadData(){

   }
}