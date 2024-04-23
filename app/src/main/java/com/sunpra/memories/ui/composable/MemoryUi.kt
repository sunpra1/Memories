package com.sunpra.memories.ui.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sunpra.memories.data.json.Memory

@Composable
fun MemoryUi(modifier: Modifier = Modifier, memory: Memory) {
    Column(modifier = modifier) {
        Row {
            Box(modifier = Modifier.size(100.dp).padding(end = 12.dp)) {
                AsyncImage(
                    model = "https://sunilprasai.com.np/storage/images/memories/${memory.image}",
                    contentDescription = null,
                )
            }
            Column {
                Text(text = memory.createdAt, style = MaterialTheme.typography.labelMedium)
                Text(text = memory.title, style = MaterialTheme.typography.titleMedium)
                Text(text = memory.description, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}