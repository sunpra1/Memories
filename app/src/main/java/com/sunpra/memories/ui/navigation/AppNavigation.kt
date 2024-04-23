package com.sunpra.memories.ui.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sunpra.memories.ui.screen.login_screen.LoginScreen
import com.sunpra.memories.ui.screen.memories_screen.MemoriesScreen
import com.sunpra.memories.utility.AppStorage

@Composable
fun AppNavigation(
    context: Context = LocalContext.current,
    appStorage: AppStorage = AppStorage(context)
) {
    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = if (appStorage.token == null) "/loginScreen" else "/memoriesScreen",
    ) {
        composable(route = "/loginScreen") {
            LoginScreen(navController)
        }
        composable(route = "/memoriesScreen") {
            MemoriesScreen(navController)
        }
    }
}