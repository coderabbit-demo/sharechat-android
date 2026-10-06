package com.sharechat.android.feed

import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.Instant

class FeedParsingTest {

    private val json = javaClass.getResource("/feed_v1.json")!!.readText()

    @Test
    fun parsesFeedResponse() {
        val feed = Gson().fromJson(json, FeedResponse::class.java)
        val item = feed.items.single()

        assertEquals("p_1001", item.postId)
        assertEquals("priya_sings", item.author.handle)
        assertEquals(1_240_000L, item.likeCount)
        assertEquals("1", feed.nextCursor)
    }

    @Test
    fun formatsCardFields() {
        val item = Gson().fromJson(json, FeedResponse::class.java).items.single()

        assertEquals("12.4L", formatLikes(item.likeCount))
        assertEquals("13h", timeAgo(item.createdAt, now = Instant.parse("2026-10-06T08:00:00Z")))
    }
}
