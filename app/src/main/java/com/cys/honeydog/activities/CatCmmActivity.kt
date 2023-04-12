package com.cys.honeydog.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cys.honeydog.R
import com.cys.honeydog.adapters.CatCmmAdapter
import com.cys.honeydog.databinding.ActivityCatCmmBinding
import com.cys.honeydog.model.CatCmmItem

class CatCmmActivity : AppCompatActivity() {
    val binding:ActivityCatCmmBinding by lazy { ActivityCatCmmBinding.inflate(layoutInflater) }
    var item: MutableList<CatCmmItem> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        item.add(CatCmmItem(R.drawable.cat_community_header,"오늘 우리고양이 폼 미쳤다","냥호가"))
        item.add(CatCmmItem(R.drawable.cat_community_header,"고양이는 왜 귀여운 것 일까?","냥이조아"))
        item.add(CatCmmItem(R.drawable.cat_community_header,"오늘 우리집에 귀한 아이가 왔어요","제임스맘"))
        item.add(CatCmmItem(R.drawable.cat_community_header,"너무너무 슬퍼요 ㅜㅠㅠ","밍키"))
        item.add(CatCmmItem(R.drawable.cat_community_header,"고양이는 나를 왜 자꾸 물어대는걸까요?? 궁금해서 질문글 작성해봅니다","방울이"))
        item.add(CatCmmItem(R.drawable.cat_community_header,"나도 고양이 ㅠㅠㅠ","네로"))
        item.add(CatCmmItem(R.drawable.cat_community_header,"나만없어 고양이","깜이"))

        binding.recyclerCatCmm.adapter=CatCmmAdapter(this,item)

        binding.communityIntentBtn.setOnClickListener { startActivity(Intent(this,DogCmmActivity::class.java)) }
        binding.goHome.setOnClickListener { startActivity(Intent(this ,MainActivity::class.java)) }
        binding.editPost.setOnClickListener { startActivity(Intent(this,NewPostActivity::class.java)) }

    }


}