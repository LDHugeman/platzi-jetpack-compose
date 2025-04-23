package com.luisdavidvarela.todoapp.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.luisdavidvarela.todoapp.presentation.screens.detail.TaskScreenRoot
import com.luisdavidvarela.todoapp.presentation.screens.home.HomeScreenRoot
import kotlinx.serialization.Serializable

@Composable
fun NavigationRoot (
    navController:NavHostController
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        NavHost(
            navController = navController,
            startDestination = HomeScreenDestination,
        ) {
            composable<HomeScreenDestination> {
                HomeScreenRoot(
                    navigateToTaskScreen = {
                        navController.navigate(TaskScreenDestination)
                    },
                )
            }
            composable<TaskScreenDestination> {
                TaskScreenRoot(
                    navigateBack = {
                        navController.navigateUp()
                    },
                )
            }
        }
    }
}

@Serializable
object HomeScreenDestination

@Serializable
object TaskScreenDestination