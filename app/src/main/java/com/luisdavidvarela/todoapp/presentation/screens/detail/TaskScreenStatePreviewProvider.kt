package com.luisdavidvarela.todoapp.presentation.screens.detail

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.luisdavidvarela.todoapp.domain.Category

class TaskScreenStatePreviewProvider : PreviewParameterProvider<TaskScreenState> {
    override val values: Sequence<TaskScreenState>
        get() = sequenceOf(
            TaskScreenState(
                taskName = TextFieldState("Task 1"),
                taskDescription = TextFieldState("Description 1"),
                isTaskDone = false,
                category = Category.WORK
            ),
            TaskScreenState(
                taskName = TextFieldState("Task 2"),
                taskDescription = TextFieldState("Description 2"),
                isTaskDone = true,
                category = Category.WORK
            ),
            TaskScreenState(
                taskName = TextFieldState("Task 3"),
                taskDescription = TextFieldState("Description 3"),
                isTaskDone = false,
                category = Category.OTHER
            )
        )
}