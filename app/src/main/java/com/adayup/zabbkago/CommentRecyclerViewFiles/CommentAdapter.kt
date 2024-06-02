// In this file the list of comments and subcomments is displayed as recycler view.

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adayup.zabbkago.R
import com.adayup.zabbkago.CommentRecyclerViewFiles.CommentItem
import com.adayup.zabbkago.SubCommentRecyclerView.SubCommentAdapter
import com.adayup.zabbkago.apiFunctions.addCommentApiCall
import com.adayup.zabbkago.interfaces.CommentActionListener
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


class CommentAdapter(private val commentItem: List<CommentItem>, private val allComments: List<CommentItem>, private val textInput: TextInputEditText,  private val scope: CoroutineScope, private val sendButton: ImageView, private val commentActionListener: CommentActionListener) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val content: TextView = itemView.findViewById(R.id.comment)
        val username: TextView = itemView.findViewById(R.id.username)
        val subCommentRecyclerView: RecyclerView = itemView.findViewById(R.id.recycler_view_subcomment)
        val replyButton: TextView = itemView.findViewById(R.id.reply_button)
        val commentBody: LinearLayout = itemView.findViewById(R.id.comment_body)

        //Stores the original background to highlight currently pressed comment
        val originalBackground = commentBody.background
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
        return CommentViewHolder(v)
    }


    private var isReply = false
    private var replyParentID: Int? = null

    private var lastClickedHolder: CommentViewHolder? = null

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

        holder.replyButton.setOnClickListener {
            isReply = true
            replyParentID = currentItem.id
            textInput.hint = "Replying..."
            textInput.requestFocus()
            Log.d("CLICKED", "Parent id: " + replyParentID.toString())
            //IF USER CLICKED ON DIFFERENT COMMENT THAN BEFORE
            if(lastClickedHolder != holder){
                lastClickedHolder?.commentBody?.background = holder.originalBackground

                holder.commentBody.setBackgroundColor(0XFFA020F0.toInt())

                lastClickedHolder = holder
            }

        }

        sendButton.setOnClickListener {
            val text: String = textInput.text.toString()
            if (isReply) {
                Log.d("CLICKED", "This is reply")
                Log.d("CLICKED", "Parent id: " + replyParentID.toString())
                scope.launch {
                    replyParentID?.let { it1 ->
                        addCommentApiCall(holder.itemView.context, text, currentItem.shopID,
                            it1
                        )
                    }
                    isReply = false
                    replyParentID = null
                    textInput.hint = "Write your comment here"
                    textInput.text = null
                    commentActionListener.onCommentPosted()
                }
            } else {
                Log.d("CLICKED", "It is not a reply")
                scope.launch {
                    addCommentApiCall(holder.itemView.context, text, currentItem.shopID.toString())
                    textInput.text = null
                    commentActionListener.onCommentPosted()
                }
            }
        }
    }


    override fun getItemCount(): Int {
        return commentItem.size
    }
}
