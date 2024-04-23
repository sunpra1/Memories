package com.sunpra.memories.ui.screen.add_memory_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sunpra.memories.ui.theme.MemoriesTheme

@Composable
fun AddMemoryScreen(viewModel: AddMemoryScreenViewModel = viewModel()) {

    val uiState by viewModel.uiState.collectAsState()

    Scaffold { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(text = "ADD MEMORY", style = MaterialTheme.typography.headlineMedium)
                Text(
                    text = "Please enter your memory detail.",
                    style = MaterialTheme.typography.titleLarge
                )

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp),
                    value = uiState.title,
                    label = { Text(text = "Title") },
                    onValueChange = {},
                )
                AnimatedVisibility(visible = uiState.emailError != null) {
                    Text(
                        modifier = Modifier.padding(start = 4.dp, top = 2.dp),
                        text = uiState.emailError ?: "",
                        style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.error)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewAddMemoryScreen() {
    MemoriesTheme {
        AddMemoryScreen()
    }
}