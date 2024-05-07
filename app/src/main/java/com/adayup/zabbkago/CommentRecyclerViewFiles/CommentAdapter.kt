import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adayup.zabbkago.R
import com.adayup.zabbkago.CommentRecyclerViewFiles.CommentItem
import com.adayup.zabbkago.SubCommentRecyclerView.SubCommentAdapter
import com.adayup.zabbkago.apiFunctions.getCommentListApiCall
import com.adayup.zabbkago.responsesDataClasses.Comment

class CommentAdapter(private val commentItem: List<CommentItem>, private val allComments: List<CommentItem>) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val content: TextView = itemView.findViewById(R.id.comment)
        val username: TextView = itemView.findViewById(R.id.username)
        val subCommentRecyclerView: RecyclerView = itemView.findViewById(R.id.recycler_view_subcomment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
        return CommentViewHolder(v)
    }


    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val currentItem = commentItem[position]
        holder.content.text = currentItem.content
        holder.username.text = currentItem.nickname

        if (holder.subCommentRecyclerView.layoutManager == null) {
            holder.subCommentRecyclerView.layoutManager = LinearLayoutManager(holder.itemView.context)
        }

        var subComments = listOf<CommentItem>()
        for(elem in allComments){
            if (elem.id != elem.parentID && elem.parentID == currentItem.id){
                subComments = subComments + elem
            }
        }


        holder.subCommentRecyclerView.adapter = SubCommentAdapter(subComments)
        holder.subCommentRecyclerView.isNestedScrollingEnabled = false

    }

    override fun getItemCount(): Int {
        return commentItem.size
    }
}
