package com.example.nextask.ui.login
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nextask.data.model.LoginRequest
import com.example.nextask.data.network.ApiService
import kotlinx.coroutines.launch

sealed interface LoginUiState {
    object Idle : LoginUiState
    object Loading : LoginUiState
    data class Success(val name: String, val userId: Int) : LoginUiState
    data class Error(val message: String) : LoginUiState
}

class LoginViewModel : ViewModel() {
    private val apiService = ApiService()
    var uiState by mutableStateOf<LoginUiState>(LoginUiState.Idle)
        private set
    fun loginUser(emailOrUsername: String, password: String) {
        uiState = LoginUiState.Loading

        viewModelScope.launch {
            try {
                val request = LoginRequest(email = emailOrUsername, password = password)

                val response = apiService.login(request)

                if (response.success && response.userId != null && response.name != null) {
                    uiState = LoginUiState.Success(name = response.name, userId = response.userId)
                } else {
                    uiState = LoginUiState.Error(response.message ?: "Authentication failed")
                }
            } catch (e: Exception) {
                uiState = LoginUiState.Error("Network error: ${e.localizedMessage}")
            }
        }
    }

    fun resetState() {
        uiState = LoginUiState.Idle
    }
}