package com.sunpra.memories.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sunpra.memories.ui.screen.login_screen.LoginScreen
import com.sunpra.memories.ui.screen.memories_screen.MemoriesScreen

@Composable
fun AppNavigation(){
    val navController: NavHostController = rememberNavController()

    NavHost(navController = navController, startDestination = "/loginScreen"){
        composable(route = "/loginScreen"){
            LoginScreen(navController)
        }
        composable(route = "/memoriesScreen"){
            MemoriesScreen(navController)
        }
    }
}