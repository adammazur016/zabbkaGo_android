package com.adayup.zabbkago.fragments

import CommentAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adayup.zabbkago.R
import com.adayup.zabbkago.CommentRecyclerViewFiles.CommentItem
import com.adayup.zabbkago.apiFunctions.addCommentApiCall
import com.adayup.zabbkago.apiFunctions.getCommentListApiCall
import com.adayup.zabbkago.apiFunctions.getUserDetailsApiCall
import com.adayup.zabbkago.responsesDataClasses.Comment
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PageDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PageDetailsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var commentAdapter: CommentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private fun getMainComments(commentList: List<CommentItem>): List<CommentItem>{
        var mainCommentList = listOf<CommentItem>()
        for (elem in commentList){
            if (elem.id == elem.parentID){
                mainCommentList = mainCommentList + elem
            }
        }
        return mainCommentList
    }
    private fun loadComments() {
        val storeID = arguments?.getInt("storeID")
        lifecycleScope.launch {
            storeID?.let {
                val allComments = getCommentListApiCall(it)
                val allCommentsItems = mutableListOf<CommentItem>()
                allComments.forEach { elem ->
                    val userData = view?.let { it1 ->
                        getUserDetailsApiCall(
                            context = it1.context,
                            userID = elem.user_id.toString()
                        )
                    }
                    val temp = userData?.let { it1 -> CommentItem(elem.id,elem.content, it1.name, elem.place_id, elem.parent_id) }
                    if (temp != null) {
                        allCommentsItems.add(temp)
                    }
                }
                val mainComments = getMainComments(allCommentsItems)
                commentAdapter = CommentAdapter(mainComments, allCommentsItems)
                recyclerView.adapter = commentAdapter
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_page_details, container, false)
        val storeName = arguments?.getString("storeName")
        val storeID = arguments?.getInt("storeID")

        val textStoreName: TextView = view.findViewById(R.id.store_title)
        textStoreName.text = storeName

        recyclerView = view.findViewById(R.id.recycler_view2)
        recyclerView.layoutManager = LinearLayoutManager(context)


        loadComments()

        val addCommentContent: TextInputEditText = view.findViewById(R.id.addCommentInput)
        val addCommentButton: ImageView = view.findViewById(R.id.addCommentButton)

        addCommentButton.setOnClickListener{
            val content = addCommentContent.text.toString()
            lifecycleScope.launch {
                val response = addCommentApiCall(view.context, content, storeID.toString())
                if (response.status == "success"){
                    addCommentContent.text = null
                    loadComments()
                }
            }
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PageDetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PageDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}