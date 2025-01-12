import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adayup.zabbkago.CommentRecyclerViewFiles.CommentItem
import com.adayup.zabbkago.R
import com.adayup.zabbkago.apiFunctions.addCommentApiCall
import com.adayup.zabbkago.apiFunctions.addRemoveLikeApiCall
import com.adayup.zabbkago.apiFunctions.getCommentListApiCall
import com.adayup.zabbkago.apiFunctions.getShopLikes
import com.adayup.zabbkago.apiFunctions.getUserDetailsApiCall
import com.adayup.zabbkago.apiFunctions.getUserShopLikeApiCall
import com.adayup.zabbkago.interfaces.CommentActionListener
import com.adayup.zabbkago.responsesDataClasses.UserDetails
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class PageDetailsFragment : Fragment(), CommentActionListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var commentAdapter: CommentAdapter
    private lateinit var addCommentContent: TextInputEditText
    private lateinit var addCommentButton: ImageView
    private lateinit var commentCountTextView: TextView
    private lateinit var loadingImageView: ImageView
    private lateinit var likeBtn: ImageView
    private lateinit var likeCountTextView: TextView

    private fun getMainComments(commentList: List<CommentItem>): List<CommentItem> {
        return commentList.filter { it.id == it.parentID }
    }

    private fun loadComments() {
        val storeID = arguments?.getInt("storeID")
        val rotateAnimation = ObjectAnimator.ofFloat(loadingImageView, "rotation", 0f, 360f).apply {
            duration = 1000 // 1 second for a full rotation
            repeatCount = ObjectAnimator.INFINITE // Repeat indefinitely
            interpolator = LinearInterpolator() // Ensure smooth rotation
        }
        lifecycleScope.launch {
            rotateAnimation.start()
            storeID?.let {
                val allComments = getCommentListApiCall(it)

                Log.d("PageDetailsFragment", "Fetched ${allComments.size} comments.")
                commentCountTextView.text = allComments.size.toString()
                val userIDList = mutableListOf<Int>()
                val userDetailsList = mutableListOf<UserDetails>()
                val allCommentsItems = mutableListOf<CommentItem>()
                if(allComments.isEmpty()){
                    allCommentsItems.add(CommentItem(-1, "No comment yet", "System", it, -1))
                }
                allComments.forEach { elem ->
                    if (!userIDList.contains(elem.user_id)) {
                        val userData = view?.let { it1 ->
                            getUserDetailsApiCall(
                                context = it1.context,
                                userID = elem.user_id.toString()
                            )
                        }
                        if (userData != null) {
                            userDetailsList.add(userData)
                            userIDList.add(userData.id)
                        }
                    }

                    val userIndex = userIDList.indexOf(elem.user_id)
                    val temp = CommentItem(elem.id, elem.content, userDetailsList[userIndex].name, elem.place_id, elem.parent_id)
                    allCommentsItems.add(temp)
                }
                Log.d("PageDetailsFragment", "Processed ${allCommentsItems.size} comment items.")
                val mainComments = getMainComments(allCommentsItems)
                Log.d("PageDetailsFragment", "Found ${mainComments.size} main comments.")
                commentAdapter = CommentAdapter(mainComments, allCommentsItems, addCommentContent, ::onSendClick, this@PageDetailsFragment)
                recyclerView.adapter = commentAdapter
                rotateAnimation.cancel()
                loadingImageView.visibility = View.INVISIBLE
            }
        }
    }

    private fun onSendClick(text: String, parentID: Int?, shopID: Int) {
        Log.d("PageDetailsFragment", "onSendClick. text: $text, parentID: $parentID, shopID: $shopID")
        lifecycleScope.launch {
            if (parentID != null) {
                addCommentApiCall(requireContext(), text, shopID, parentID)
            } else {
                addCommentApiCall(requireContext(), text, shopID)
            }
            loadComments()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_page_details, container, false)
        val storeName = arguments?.getString("storeName")

        val textStoreName: TextView = view.findViewById(R.id.store_title)
        textStoreName.text = storeName

        addCommentContent = view.findViewById(R.id.addCommentInput)
        addCommentButton = view.findViewById(R.id.addCommentButton)
        commentCountTextView = view.findViewById(R.id.comment_count_text)
        loadingImageView = view.findViewById(R.id.loading_image)
        likeCountTextView = view.findViewById(R.id.likes_count_text)
        loadingImageView.setImageResource(R.drawable.loading)
        likeBtn = view.findViewById(R.id.like_button)

        val storeID = arguments?.getInt("storeID")
        if (storeID != null) {
            lifecycleScope.launch {
                val likesResult = getShopLikes(view.context, storeID)
                likeCountTextView.text = likesResult.likes.toString()
            }

            lifecycleScope.launch {
                val likeStatus = getUserShopLikeApiCall(view.context, storeID)
                if (likeStatus.status == "success") {
                    if (likeStatus.message == "liked") {
                        likeBtn.setImageResource(R.drawable.like_icon)
                    } else {
                        likeBtn.setImageResource(R.drawable.like_icon_unclicked)
                    }
                } else {
                    Log.d("LIKE", "Error")
                }
            }
            likeBtn.setOnClickListener {
                lifecycleScope.launch {
                    val response = addRemoveLikeApiCall(view.context, storeID)
                    if (response.status == "success") {
                        var currentCount: Int = likeCountTextView.text.toString().toInt()
                        if (response.message == "like_added") {
                            likeBtn.setImageResource(R.drawable.like_icon)
                            currentCount += 1
                            likeCountTextView.text = currentCount.toString()
                        } else {
                            likeBtn.setImageResource(R.drawable.like_icon_unclicked)
                            currentCount -= 1
                            likeCountTextView.text = currentCount.toString()
                        }
                    } else {
                        Log.d("LIKE", "Error")
                    }
                }
            }
        }

        recyclerView = view.findViewById(R.id.recycler_view2)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Initialize adapter with an empty list initially
        commentAdapter = CommentAdapter(emptyList(), emptyList(), addCommentContent, ::onSendClick, this)
        recyclerView.adapter = commentAdapter

        loadComments()

        addCommentButton.setOnClickListener {
            Log.d("PageDetailsFragment", "addCommentButton clicked. text: ${addCommentContent.text.toString()}")
            commentAdapter.sendComment(addCommentContent.text.toString())
        }

        return view
    }

    override fun onCommentPosted() {
        loadComments()
    }
}
