package com.luisdavidvarela.todoapp.presentation.screens.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luisdavidvarela.todoapp.data.FakeTaskLocalDataSource
import com.luisdavidvarela.todoapp.domain.Task
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.UUID

class TaskViewModel: ViewModel() {
    private val fakeTaskLocalDataSource = FakeTaskLocalDataSource

    var state by mutableStateOf(TaskScreenState())
        private set

    private val eventChannel = Channel<TaskEvent>()
    val event = eventChannel.receiveAsFlow()

    fun onAction(actionTask: ActionTask) {
        viewModelScope.launch {
            when (actionTask) {
                is ActionTask.ChangeTaskCategory -> {
                    state = state.copy(
                        category = actionTask.category
                    )
                }
                is ActionTask.ChangeTaskDone -> {
                    state = state.copy(
                        isTaskDone = actionTask.isTaskDone
                    )
                }
                ActionTask.SaveTask -> {
                    val task = Task(
                        id = UUID.randomUUID().toString(),
                        title = state.taskName.text.toString(),
                        description = state.taskDescription.text.toString(),
                        isCompleted = state.isTaskDone,
                        category = state.category
                    )
                    fakeTaskLocalDataSource.addTask(task = task)
                    eventChannel.send(TaskEvent.TaskCreated)
                }
                else -> Unit
            }
        }
    }
}