package com.luisdavidvarela.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.luisdavidvarela.todoapp.data.FakeTaskLocalDataSource
import com.luisdavidvarela.todoapp.domain.Task
import com.luisdavidvarela.todoapp.ui.theme.TodoAppTheme
import com.luisdavidvarela.todoapp.ui.theme.components.HelloWorld
import java.util.UUID

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoAppTheme() {
                var text by remember {
                    mutableStateOf("")
                }
                val fakeLocalDataSource = com.luisdavidvarela.todoapp.data.FakeTaskLocalDataSource
                LaunchedEffect(true) {
                    fakeLocalDataSource.taskFlow.collect {
                        text = it.toString()
                    }
                }
                LaunchedEffect(true) {
                    fakeLocalDataSource.addTask(
                        Task(
                            id = UUID.randomUUID().toString(),
                            title = "Task 1",
                            description = "Description 1"
                        )
                    )
                }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Text(
                        text = text,
                        modifier = Modifier
                            .padding(top = innerPadding.calculateTopPadding())
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}