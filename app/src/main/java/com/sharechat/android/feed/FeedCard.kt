package com.sharechat.android.feed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import java.time.Duration
import java.time.Instant

@Composable
fun FeedScreen(vm: FeedViewModel = viewModel()) {
    vm.error?.let { Text(it, Modifier.padding(16.dp)) }
    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        items(vm.items, key = { it.postId }) { item ->
            FeedCard(item, liked = vm.isLiked(item.postId), onLike = { vm.toggleLike(item.postId) })
        }
    }
}

@Composable
fun FeedCard(item: FeedItem, liked: Boolean, onLike: () -> Unit) {
    Card(Modifier.fillMaxWidth().padding(horizontal = 12.dp)) {
        Column(Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = item.author.avatarUrl,
                    contentDescription = null,
                    modifier = Modifier.size(36.dp).clip(CircleShape),
                )
                Spacer(Modifier.width(8.dp))
                Text("@${item.author.handle}", style = MaterialTheme.typography.titleSmall)
                Spacer(Modifier.weight(1f))
                Text(timeAgo(item.createdAt), style = MaterialTheme.typography.labelSmall)
            }

            if (item.media.type == "image" || item.media.type == "video") {
                AsyncImage(
                    model = item.media.url,
                    contentDescription = item.caption,
                    modifier = Modifier.fillMaxWidth().aspectRatio(1f).padding(top = 8.dp),
                )
            }

            item.caption?.let { Text(it, Modifier.padding(top = 8.dp)) }

            TextButton(onClick = onLike) {
                Text("${if (liked) "♥" else "♡"} ${formatLikes(item.likeCount)}")
            }
        }
    }
}

/** Indian compact format: 1,240,000 → "12.4L", 48,300 → "48.3K". */
fun formatLikes(count: Long): String = when {
    count >= 10_000_000 -> "%.1fCr".format(count / 10_000_000.0)
    count >= 100_000 -> "%.1fL".format(count / 100_000.0)
    count >= 1_000 -> "%.1fK".format(count / 1_000.0)
    else -> count.toString()
}

fun timeAgo(createdAt: String, now: Instant = Instant.now()): String {
    val age = Duration.between(Instant.parse(createdAt), now)
    return when {
        age.toDays() > 0 -> "${age.toDays()}d"
        age.toHours() > 0 -> "${age.toHours()}h"
        else -> "${age.toMinutes()}m"
    }
}
