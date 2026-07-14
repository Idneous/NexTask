package com.example.nextask.ui.taskmanipulation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nextask.data.model.CreateTaskRequest
import com.example.nextask.data.network.ApiService
import kotlinx.coroutines.launch

sealed interface TaskUiState {
    object Idle : TaskUiState
    object Loading : TaskUiState
    object Success : TaskUiState
    data class Error(val message: String) : TaskUiState
}

class TaskViewModel : ViewModel() {
    private val apiService = ApiService()
    
    var uiState by mutableStateOf<TaskUiState>(TaskUiState.Idle)
        private set

    fun createTask(userId: Int, title: String, subject: String, deadlineDate: String, deadlineTime: String, estimatedHours: Double, priorityWeight: Int) {
        uiState = TaskUiState.Loading
        viewModelScope.launch {
            try {
                // Formatting deadline as "YYYY-MM-DD HH:MM:SS"
                val deadline = "$deadlineDate $deadlineTime:00"
                val request = CreateTaskRequest(
                    user_id = userId,
                    title = title,
                    subject = subject,
                    deadline = deadline,
                    estimated_hours = estimatedHours,
                    priority_weight = priorityWeight
                )
                val response = apiService.createTask(request)
                if (response.success) {
                    uiState = TaskUiState.Success
                } else {
                    uiState = TaskUiState.Error(response.message)
                }
            } catch (e: Exception) {
                uiState = TaskUiState.Error("Network error: ${e.localizedMessage}")
            }
        }
    }

    fun resetState() {
        uiState = TaskUiState.Idle
    }
}
