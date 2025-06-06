package com.luisdavidvarela.todoapp.presentation.screens.detail

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.luisdavidvarela.todoapp.domain.Task
import com.luisdavidvarela.todoapp.domain.TaskLocalDataSource
import com.luisdavidvarela.todoapp.navigation.TaskScreenDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskLocalDataSource: TaskLocalDataSource,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    val taskData = savedStateHandle.toRoute<TaskScreenDestination>()

    var state by mutableStateOf(TaskScreenState())
        private set

    private val eventChannel = Channel<TaskEvent>()
    val event = eventChannel.receiveAsFlow()
    private val canSaveTask = snapshotFlow { state.taskName.text.toString() }

    private var editedTask: Task? = null
    init {
        taskData.taskId?.let {
            viewModelScope.launch {
                val task = taskLocalDataSource.getTaskById(it)
                editedTask = task

                state = state.copy(
                    taskName = TextFieldState(task?.title?: ""),
                    taskDescription = TextFieldState(task?.description?: ""),
                    isTaskDone = task?.isCompleted ?: false,
                    category = task?.category
                )
            }
        }

        canSaveTask.onEach {
            state = state.copy(canSaveTask = it.isNotEmpty())
        }.launchIn(viewModelScope)
    }

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
                is ActionTask.SaveTask -> {
                    editedTask?.let {
                        taskLocalDataSource.updateTask(
                             updatedTask = it.copy(
                                id = it.id,
                                title = state.taskName.text.toString(),
                                description = state.taskDescription.text.toString(),
                                isCompleted = state.isTaskDone,
                                category = state.category
                            )
                        )
                    }?:run {
                        val task = Task(
                            id = UUID.randomUUID().toString(),
                            title = state.taskName.text.toString(),
                            description = state.taskDescription.text.toString(),
                            isCompleted = state.isTaskDone,
                            category = state.category
                        )
                        taskLocalDataSource.addTask(task = task)
                    }
                    eventChannel.send(TaskEvent.TaskCreated)
                }
                else -> Unit
            }
        }
    }
}