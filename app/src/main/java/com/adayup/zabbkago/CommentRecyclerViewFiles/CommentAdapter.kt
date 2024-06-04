import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adayup.zabbkago.CommentRecyclerViewFiles.CommentItem
import com.adayup.zabbkago.R
import com.adayup.zabbkago.SubCommentRecyclerView.SubCommentAdapter
import com.adayup.zabbkago.interfaces.CommentActionListener
import com.google.android.material.textfield.TextInputEditText

class CommentAdapter(
    private val commentItem: List<CommentItem>,
    private val allComments: List<CommentItem>,
    private val textInput: TextInputEditText,
    private val onSendClickListener: (String, Int?, Int) -> Unit,
    private val commentActionListener: CommentActionListener
) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val content: TextView = itemView.findViewById(R.id.comment)
        val username: TextView = itemView.findViewById(R.id.username)
        val subCommentRecyclerView: RecyclerView = itemView.findViewById(R.id.recycler_view_subcomment)
        val replyButton: TextView = itemView.findViewById(R.id.reply_button)
        val commentBody: LinearLayout = itemView.findViewById(R.id.comment_body)

        // Stores the original background to highlight currently pressed comment
        val originalBackground = commentBody.background
    }

    private var isReply = false
    private var replyParentID: Int? = null
    private var shopID: Int? = null
    private var lastClickedHolder: CommentViewHolder? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
        return CommentViewHolder(v)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val currentItem = commentItem[position]
        shopID = currentItem.shopID
        holder.content.text = currentItem.content
        holder.username.text = currentItem.nickname

        Log.d("CommentAdapter", "onBindViewHolder position: $position, content: ${currentItem.content}, shopID: ${currentItem.shopID}")

        if (holder.subCommentRecyclerView.layoutManager == null) {
            holder.subCommentRecyclerView.layoutManager = LinearLayoutManager(holder.itemView.context)
        }

        val subComments = allComments.filter { it.id != it.parentID && it.parentID == currentItem.id }
        Log.d("CommentAdapter", "Found ${subComments.size} sub-comments for comment ID: ${currentItem.id}")

        holder.subCommentRecyclerView.adapter = SubCommentAdapter(subComments)
        holder.subCommentRecyclerView.isNestedScrollingEnabled = false

        holder.replyButton.setOnClickListener {
            isReply = true
            replyParentID = currentItem.id
            textInput.hint = "Replying..."
            textInput.requestFocus()

            Log.d("CommentAdapter", "Reply button clicked. replyParentID: $replyParentID, isReply: $isReply")

            if (lastClickedHolder != holder) {
                lastClickedHolder?.commentBody?.background = lastClickedHolder?.originalBackground
                holder.commentBody.setBackgroundColor(0xFFA020F0.toInt())
                lastClickedHolder = holder
            }
        }
    }

    fun sendComment(text: String) {
        shopID?.let {
            Log.d("CommentAdapter", "Sending comment. text: $text, replyParentID: $replyParentID, shopID: $it")
            onSendClickListener(text, replyParentID, it)
            isReply = false
            replyParentID = null
            textInput.hint = "Write your comment here"
            textInput.text = null
            lastClickedHolder?.commentBody?.background = lastClickedHolder?.originalBackground
        }
    }

    override fun getItemCount(): Int {
        return commentItem.size
    }
}
