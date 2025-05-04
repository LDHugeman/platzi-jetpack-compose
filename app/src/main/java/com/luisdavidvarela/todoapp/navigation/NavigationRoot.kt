package com.luisdavidvarela.todoapp.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.luisdavidvarela.todoapp.presentation.screens.detail.TaskScreenRoot
import com.luisdavidvarela.todoapp.presentation.screens.detail.TaskViewModel
import com.luisdavidvarela.todoapp.presentation.screens.home.HomeScreenRoot
import com.luisdavidvarela.todoapp.presentation.screens.home.HomeScreenViewModel
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
                val homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
                HomeScreenRoot(
                    navigateToTaskScreen = {
                        navController.navigate(TaskScreenDestination(it))
                    },
                    viewModel = homeScreenViewModel
                )
            }
            composable<TaskScreenDestination> {
                val taskViewModel: TaskViewModel = hiltViewModel()
                TaskScreenRoot(
                    viewModel = taskViewModel,
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}

@Serializable
object HomeScreenDestination

@Serializable
data class TaskScreenDestination (
    val taskId: String? = null,
)