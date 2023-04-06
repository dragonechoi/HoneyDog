package com.cys.honeydog.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.cys.honeydog.R
import com.cys.honeydog.adapters.ProfilFragmentAdapter
import com.cys.honeydog.databinding.FragmentProfilMainBinding
import com.cys.honeydog.model.ProfilRecyclerItem

class ProfilMainFragment : Fragment() {
    private lateinit var Binding: FragmentProfilMainBinding
    var item: MutableList<ProfilRecyclerItem> = mutableListOf()
    var imgUri: Uri ?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Binding = FragmentProfilMainBinding.inflate(inflater, container, false)
        return Binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        item.add(ProfilRecyclerItem("게시글 작성 리사이클러뷰 테스트", R.drawable.cogi_love))
        item.add(ProfilRecyclerItem("게시글 작성 리사이클러뷰 테스트", R.drawable.cogi_love))
        item.add(ProfilRecyclerItem("게시글 작성 리사이클러뷰 테스트", R.drawable.cogi_love))
        item.add(ProfilRecyclerItem("게시글 작성 리사이클러뷰 테스트", R.drawable.cogi_love))
        item.add(ProfilRecyclerItem("게시글 작성 리사이클러뷰 테스트", R.drawable.cogi_love))
        item.add(ProfilRecyclerItem("게시글 작성 리사이클러뷰 테스트", R.drawable.cogi_love))
        item.add(ProfilRecyclerItem("게시글 작성 리사이클러뷰 테스트", R.drawable.cogi_love))
        item.add(ProfilRecyclerItem("게시글 작성 리사이클러뷰 테스트", R.drawable.cogi_love))
        item.add(ProfilRecyclerItem("게시글 작성 리사이클러뷰 테스트", R.drawable.cogi_love))
        item.add(ProfilRecyclerItem("게시글 작성 리사이클러뷰 테스트", R.drawable.cogi_love))
        item.add(ProfilRecyclerItem("게시글 작성 리사이클러뷰 테스트", R.drawable.cogi_love))
        item.add(ProfilRecyclerItem("게시글 작성 리사이클러뷰 테스트", R.drawable.cogi_love))
        item.add(ProfilRecyclerItem("게시글 작성 리사이클러뷰 테스트", R.drawable.cogi_love))
        item.add(ProfilRecyclerItem("게시글 작성 리사이클러뷰 테스트", R.drawable.cogi_love))
        item.add(ProfilRecyclerItem("게시글 작성 리사이클러뷰 테스트", R.drawable.cogi_love))
        item.add(ProfilRecyclerItem("게시글 작성 리사이클러뷰 테스트", R.drawable.cogi_love))
        item.add(ProfilRecyclerItem("게시글 작성 리사이클러뷰 테스트", R.drawable.cogi_love))
        item.add(ProfilRecyclerItem("게시글 작성 리사이클러뷰 테스트", R.drawable.cogi_love))
        item.add(ProfilRecyclerItem("게시글 작성 리사이클러뷰 테스트", R.drawable.cogi_love))

        Binding.profilRecycler.adapter = ProfilFragmentAdapter(requireContext(), item)

        Binding.profilImage.setOnClickListener { clickChangeProfile()}

    }
private fun clickChangeProfile(){
    val intent = Intent(MediaStore.ACTION_PICK_IMAGES)
    resultLauncher.launch(intent)

}

    var resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode != AppCompatActivity.RESULT_CANCELED) {

            imgUri = it.getData()?.getData()
            Glide.with(this).load(imgUri).into(Binding.profilImage)

        }
    }
}
