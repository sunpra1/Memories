package com.sunpra.memories.ui.screen.login_screen

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.sunpra.memories.R
import com.sunpra.memories.ui.theme.MemoriesTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sunpra.memories.utility.AppStorage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginScreenViewModel = viewModel(),
    context: Context = LocalContext.current
) {

    val uiState by viewModel.uiState.collectAsState()
    val dialogMessage by viewModel.dialogMessage.collectAsState(null)
    val onLoginSuccess by viewModel.loginSuccess.collectAsState(null)

    LaunchedEffect(onLoginSuccess) {
        if(onLoginSuccess != null){
            navController.navigate("/memoriesScreen")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Login",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(text = "WELCOME", style = MaterialTheme.typography.headlineMedium)
            Text(
                text = "Please provide your valid credentials.",
                style = MaterialTheme.typography.titleLarge
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                label = { Text(text = "Email") },
                value = uiState.email,
                onValueChange = viewModel::onEmailChanged,
            )
            AnimatedVisibility(visible = uiState.emailError != null) {
                Text(
                    modifier = Modifier.padding(start = 4.dp, top = 2.dp),
                    text = uiState.emailError ?: "",
                    style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.error)
                )
            }
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                visualTransformation = if (uiState.isPasswordVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
                value = uiState.password,
                label = { Text(text = "Password") },
                trailingIcon = {
                    if (uiState.isPasswordVisible) {
                        Icon(
                            modifier = Modifier.clickable(
                                enabled = true,
                                onClick = viewModel::onPasswordToggled
                            ),
                            painter = painterResource(id = R.drawable.eye),
                            contentDescription = "Password view icon."
                        )
                    } else {
                        Icon(
                            modifier = Modifier.clickable(
                                enabled = true,
                                onClick = viewModel::onPasswordToggled
                            ),
                            painter = painterResource(id = R.drawable.eye_off),
                            contentDescription = "Password view icon."
                        )
                    }
                },
                onValueChange = viewModel::onPasswordChanged,
            )

            Button(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .heightIn(min = 52.dp),
                onClick = viewModel::onLoginClicked
            ) {
                Text(text = "LOGIN")
            }

            Row(
                modifier = Modifier.padding(top = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = uiState.rememberMe,
                    onCheckedChange = viewModel::rememberMeToggled
                )
                Text(text = "Remember me.")
            }

            Text(
                modifier = Modifier.align(Alignment.End),
                text = buildAnnotatedString {
                    append("If new user")
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            textDecoration = TextDecoration.Underline
                        )
                    ) {
                        append(" register")
                    }
                    append(" here.")
                },
            )
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
fun PreviewLoginScreen() {
    MemoriesTheme {
        LoginScreen(rememberNavController())
    }
}