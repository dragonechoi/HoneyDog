import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cys.honeydog.G
import com.cys.honeydog.R
import com.cys.honeydog.activities.PostActivity
import com.cys.honeydog.databinding.RecyclerCommunityListItemBinding
import com.cys.honeydog.model.DogCmmItem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DogCmmAdapter(var context: Context, var items: MutableList<DogCmmItem>) :
    RecyclerView.Adapter<DogCmmAdapter.VH>() {


    inner class VH(val binding: RecyclerCommunityListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding =
            RecyclerCommunityListItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return VH(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        var list: DogCmmItem = items[position]

        if (list.imageUri.isNullOrEmpty()) {
            // 이미지가 없을 경우 대체 이미지 사용
            Glide.with(context).load(R.drawable.bichon_like).into(holder.binding.communityListIv)
        } else {
            // 이미지가 있을 경우 원본 이미지 사용
            Glide.with(context).load(list.imageUri).into(holder.binding.communityListIv)
        }

        holder.binding.communityListTitle.text = list.title
        holder.binding.communityListNickname.text = list.nickname


        loadDogPost(
            holder.binding.communityListTitle,
            holder.binding.communityListIv,
            holder.binding.communityListNickname
        )

        holder.binding.communityList.setOnClickListener {
            val intent: Intent = Intent(context, PostActivity::class.java)
            intent.putExtra("imageUri", list.imageUri)
            intent.putExtra("title", list.title)
            intent.putExtra("nickname", list.nickname)
            intent.putExtra("postText", list.postText)
            intent.putExtra("profileUrl", list.profileUrl)
            intent.putExtra("id", list.userId)
            intent.putExtra("no", list.no)

            context.startActivity(intent)
        }
    }

    private fun loadDogPost(titleView: TextView, img: ImageView, nicknameView: TextView) {
        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("Post")
            .document(G.userAccount!!.id)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                if (snapshot != null && snapshot.exists()) {

                    val nickName = snapshot.getString("nickname")
                    val title = snapshot.getString("title")
                    val iv = snapshot.getString("imageUri")
                    titleView.text = title
                    Glide.with(context).load(iv).into(img)
                    nicknameView.text = nickName

                }
            }
    }
}