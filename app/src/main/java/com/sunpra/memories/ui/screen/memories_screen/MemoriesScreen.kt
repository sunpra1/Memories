package com.sunpra.memories.ui.screen.memories_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sunpra.memories.ui.composable.MemoryUi

@Composable
fun MemoriesScreen(
    navHostController: NavHostController,
    navigateToAddMemoryScreen: () -> Unit = { navHostController.navigate("/addMemory") },
    lifecycleOwner : LifecycleOwner = LocalLifecycleOwner.current,
    viewModel: MemoriesScreenViewModel = viewModel()
) {
    val dialogMessage by viewModel.dialogMessage.collectAsState(null)

    val uiState by viewModel.uiState.collectAsState()

    DisposableEffect(Unit) {

        val observer = LifecycleEventObserver{ owner, event ->
            if(event == Lifecycle.Event.ON_RESUME){
                viewModel.getMemories()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Scaffold(
        floatingActionButton = {
            IconButton(onClick = navigateToAddMemoryScreen) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            LazyColumn {
                items(uiState.memories.size) {
                    MemoryUi(modifier = Modifier.padding(12.dp), memory = uiState.memories[it])
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