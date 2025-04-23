package com.luisdavidvarela.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.luisdavidvarela.todoapp.presentation.screens.detail.TaskScreen
import com.luisdavidvarela.todoapp.presentation.screens.detail.TaskScreenRoot
import com.luisdavidvarela.todoapp.presentation.screens.detail.TaskScreenState
import com.luisdavidvarela.todoapp.presentation.screens.home.HomeScreenRoot
import com.luisdavidvarela.todoapp.ui.theme.TodoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoAppTheme() {
                TaskScreenRoot()
            }
        }
    }
}