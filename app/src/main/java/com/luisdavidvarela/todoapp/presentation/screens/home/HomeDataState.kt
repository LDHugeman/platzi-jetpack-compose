package com.luisdavidvarela.todoapp.presentation.screens.home

import com.luisdavidvarela.todoapp.domain.Task

data class HomeDataState(
    val date: String = "",
    val summary: String = "",
    val completedTask: List<Task> = emptyList(),
    val pendingTask: List<Task> = emptyList(),
)
