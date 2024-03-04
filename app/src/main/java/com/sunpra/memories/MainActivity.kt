package com.sunpra.memories

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sunpra.memories.ui.theme.MemoriesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MemoriesTheme {
                LoginScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen() {
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
                value = "sunpra12@gmail.com",
                onValueChange = {},
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                value = "password",
                label = { Text(text = "Password") },
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.eye),
                        contentDescription = "Password view icon."
                    )
                },
                onValueChange = {},
            )

            Button(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .heightIn(min = 52.dp),
                onClick = { }) {
                Text(text = "LOGIN")
            }

            Row(
                modifier = Modifier.padding(top = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checked = true, onCheckedChange = {})
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
    }
}

@Preview
@Composable
fun PreviewLoginScreen() {
    MemoriesTheme {
        LoginScreen()
    }
}