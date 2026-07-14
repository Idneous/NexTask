package com.example.nextask.ui.registration

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nextask.data.model.RegisterRequest
import com.example.nextask.data.network.ApiService
import kotlinx.coroutines.launch

sealed interface RegisterUiState {
    object Idle : RegisterUiState
    object Loading : RegisterUiState
    object Success : RegisterUiState
    data class Error(val message: String) : RegisterUiState
}

class RegisterViewModel : ViewModel() {
    private val apiService = ApiService()
    
    var uiState by mutableStateOf<RegisterUiState>(RegisterUiState.Idle)
        private set

    fun registerUser(username: String, email: String, password: String) {
        uiState = RegisterUiState.Loading
        viewModelScope.launch {
            try {
                // PHP expects "name", so we map username to name
                val response = apiService.register(RegisterRequest(name = username, email = email, password = password))
                if (response.success) {
                    uiState = RegisterUiState.Success
                } else {
                    uiState = RegisterUiState.Error(response.message ?: "Registration failed")
                }
            } catch (e: Exception) {
                uiState = RegisterUiState.Error("Network error: ${e.localizedMessage}")
            }
        }
    }

    fun resetState() {
        uiState = RegisterUiState.Idle
    }
}
