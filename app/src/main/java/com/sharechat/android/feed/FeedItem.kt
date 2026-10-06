package com.sharechat.android.feed

import com.google.gson.annotations.SerializedName

// Mirrors GET /v1/feed from sharechat-feed-service (api/openapi.yaml).

data class FeedResponse(
    @SerializedName("items") val items: List<FeedItem>,
    @SerializedName("next_cursor") val nextCursor: String?,
)

data class FeedItem(
    @SerializedName("post_id") val postId: String,
    @SerializedName("author") val author: Author,
    @SerializedName("media") val media: Media,
    @SerializedName("caption") val caption: String?,
    @SerializedName("language") val language: String,
    @SerializedName("like_count") val likeCount: Long,
    @SerializedName("created_at") val createdAt: String, // ISO-8601, parsed in FeedCard
)

data class Author(
    @SerializedName("id") val id: String,
    @SerializedName("handle") val handle: String,
    @SerializedName("avatar_url") val avatarUrl: String?,
)

data class Media(
    @SerializedName("type") val type: String,
    @SerializedName("url") val url: String,
    @SerializedName("duration_ms") val durationMs: Long?,
)
