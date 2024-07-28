package com.learning.littlelemon.utils

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.learning.littlelemon.screens.Home
import com.learning.littlelemon.screens.Onboarding
import com.learning.littlelemon.screens.Profile

@Composable
fun NavigationComposable(navController: NavHostController) {
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }
    val sharedEmail = remember { mutableStateOf(preferencesManager.getData("email", "")) }
    val startDestination = if (sharedEmail.value == "") Onboarding.route else Home.route

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Onboarding.route) {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                Onboarding(innerPadding, navController)
            }
        }
        composable(Home.route) {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                Home(innerPadding, navController)
            }
        }
        composable(Profile.route) {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                Profile(innerPadding, navController)
            }
        }
    }
}
