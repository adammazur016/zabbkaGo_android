package com.adayup.zabbkago.SubCommentRecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.adayup.zabbkago.R
import com.adayup.zabbkago.CommentRecyclerViewFiles.CommentItem

class SubCommentAdapter(private val subCommentData: List<CommentItem>) : RecyclerView.Adapter<SubCommentAdapter.SubCommentViewHolder>() {

    class SubCommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val subUsername: TextView = itemView.findViewById<TextView>(R.id.subUsername)
        val subContent: TextView = itemView.findViewById<TextView>(R.id.subComment)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubCommentViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_subcomment, parent, false)
        return SubCommentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SubCommentViewHolder, position: Int) {
        val subCommentItem = subCommentData[position]
        holder.subUsername.text = subCommentItem.nickname
        holder.subContent.text = subCommentItem.content
    }

    override fun getItemCount(): Int {
        return subCommentData.size
    }


}