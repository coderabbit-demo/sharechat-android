package com.sharechat.android.feed

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class FeedViewModel(
    private val api: FeedApi = FeedApi.create(),
    private val userId: String = "u_1",
    private val lang: String? = "hi",
) : ViewModel() {

    var items by mutableStateOf<List<FeedItem>>(emptyList())
        private set
    var error by mutableStateOf<String?>(null)
        private set

    private var nextCursor: String? = null
    private val liked = mutableSetOf<String>()

    init {
        loadMore()
    }

    fun loadMore() {
        viewModelScope.launch {
            try {
                val page = api.getFeed(userId, lang, nextCursor)
                items = items + page.items
                nextCursor = page.nextCursor
            } catch (e: Exception) {
                error = "Couldn't load your feed. Pull to retry."
            }
        }
    }

    /** Optimistic like: update the count immediately, then sync with the server. */
    fun toggleLike(postId: String) {
        val nowLiked = liked.add(postId) || !liked.remove(postId)
        items = items.map {
            if (it.postId == postId) it.copy(likeCount = it.likeCount + if (nowLiked) 1 else -1) else it
        }
    }

    fun isLiked(postId: String) = postId in liked
}
