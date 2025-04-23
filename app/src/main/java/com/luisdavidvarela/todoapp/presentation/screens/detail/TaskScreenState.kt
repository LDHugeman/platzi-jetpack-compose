package com.luisdavidvarela.todoapp.presentation.screens.detail

import androidx.compose.foundation.text.input.TextFieldState
import com.luisdavidvarela.todoapp.domain.Category

data class TaskScreenState(
    val taskName: TextFieldState = TextFieldState(),
    val taskDescription: TextFieldState = TextFieldState(),
    val category: Category? = null,
    val isTaskDone: Boolean = false,
)