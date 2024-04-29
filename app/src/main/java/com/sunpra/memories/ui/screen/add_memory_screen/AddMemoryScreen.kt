package com.sunpra.memories.ui.screen.add_memory_screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.sunpra.memories.ui.theme.MemoriesTheme

@Composable
fun AddMemoryScreen(
    navHostController: NavHostController,
    viewModel: AddMemoryScreenViewModel = viewModel(),
    ) {

    val uiState by viewModel.uiState.collectAsState()

    val dialogMessage by viewModel.dialogMessage.collectAsState(null)
    val onAddMemorySuccess by viewModel.onAddMemorySuccess.collectAsState(false)

    LaunchedEffect(onAddMemorySuccess) {
        if (onAddMemorySuccess) {
            navHostController.popBackStack()
        }
    }

    val imageChooseLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = viewModel::onImageSelected,
    )

    val onSelectImageClicked: () -> Unit = {
        imageChooseLauncher.launch("image/*")
    }

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
                    onValueChange = viewModel::onTitleChanged,
                )
                AnimatedVisibility(visible = uiState.titleError != null) {
                    Text(
                        modifier = Modifier.padding(start = 4.dp, top = 2.dp),
                        text = uiState.titleError ?: "",
                        style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.error)
                    )
                }

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp),
                    value = uiState.description,
                    label = { Text(text = "Description") },
                    onValueChange = viewModel::onDescriptionChanged,
                    minLines = 4
                )
                AnimatedVisibility(visible = uiState.descriptionError != null) {
                    Text(
                        modifier = Modifier.padding(start = 4.dp, top = 2.dp),
                        text = uiState.descriptionError ?: "",
                        style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.error)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp), verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .border(2.dp, color = Color.Gray)
                    ) {
                        Image(
                            modifier = Modifier.fillMaxSize(),
                            painter = rememberAsyncImagePainter(model = uiState.image),
                            contentDescription = null
                        )
                    }
                    ElevatedButton(
                        modifier = Modifier.padding(start = 16.dp),
                        onClick = onSelectImageClicked
                    ) {
                        Text(text = "SELECT IMAGE")
                    }
                }
                AnimatedVisibility(visible = uiState.imageError != null) {
                    Text(
                        modifier = Modifier.padding(start = 4.dp, top = 2.dp),
                        text = uiState.imageError ?: "",
                        style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.error)
                    )
                }
                ElevatedButton(
                    modifier = Modifier
                        .padding(top = 32.dp)
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    onClick = viewModel::onAddMemoryClicked
                ) {
                    Text(text = "ADD MEMORY")
                }
            }
        }
        dialogMessage?.let { message ->
            AlertDialog(
                properties = DialogProperties(
                    dismissOnBackPress = false,
                    dismissOnClickOutside = false
                ),
                title = { Text(text = "MESSAGE") },
                text = { Text(text = message) },
                onDismissRequest = viewModel::onDismissClicked,
                confirmButton = {
                    TextButton(onClick = viewModel::onDismissClicked) {
                        Text(text = "OK")
                    }
                },
            )
        }
    }
}

@Preview
@Composable
fun PreviewAddMemoryScreen() {
    MemoriesTheme {
        AddMemoryScreen(rememberNavController())
    }
}