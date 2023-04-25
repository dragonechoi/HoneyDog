package com.cys.honeydog.adapters

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.cys.honeydog.R
import com.cys.honeydog.databinding.RecyclerItemSearchBinding
import com.cys.honeydog.fragments.SearchMainFragment
import com.cys.honeydog.network.AniMalHospital

class SearchFragmentAdapter(var context: Context, var items:MutableList<AniMalHospital>): Adapter<SearchFragmentAdapter.VH>() {

    inner class VH (var binding:RecyclerItemSearchBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(RecyclerItemSearchBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        var item:AniMalHospital= items[position]

        //제목글씨 중에 html태그문이 포함되어 있어서 지저분..이를 제거
        var title:String = HtmlCompat.fromHtml(item.title, HtmlCompat.FROM_HTML_MODE_COMPACT).toString()
        holder.binding.tvTitle.text= title


        holder.binding.address.text=item.address
        holder.binding.tvNum.text=item.telephone
        if (item.image.isNullOrEmpty()) {
            // 이미지가 없는 경우 기본 이미지 설정
            holder.binding.ivHospital.setImageResource(R.drawable.cogi_love)
        } else {
            Glide.with(context).load(item.image).into(holder.binding.ivHospital)
        }

        holder.binding.changePage.setOnClickListener{
            if (item.link != null) {
                //디바이스의 인터넷앱을 실행
                val intent= Intent(Intent.ACTION_VIEW)
                intent.data= Uri.parse(item.link)
                try {
                    context.startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(context, "링크가 삭제 되었거나 존재하지 않는 페이지 입니다.", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            } else {
                // 링크가 null 일 경우, 사용자에게 알림
                Toast.makeText(context, "링크가 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}