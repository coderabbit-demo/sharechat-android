# sharechat-android

ShareChat Android app: home feed.

## Feed

The home feed calls `GET /v1/feed` on `sharechat-feed-service` through Retrofit and Gson.
The response models in [`feed/FeedItem.kt`](app/src/main/java/com/sharechat/android/feed/FeedItem.kt)
mirror the service's `api/openapi.yaml`: snake_case keys, `created_at` as ISO-8601.

Each card shows the author, media, caption, time since posting and like count. Liking a post updates
the count immediately, before the server confirms.

Installed versions stay in use for months, so the feed API must stay backward-compatible with them.
See the `mobile-api-compatibility` skill in `sharechat-ai-context`.
