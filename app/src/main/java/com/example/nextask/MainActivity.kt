package com.example.nextask

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nextask.data.model.DashboardStats
import com.example.nextask.data.model.PriorityTask
import com.example.nextask.ui.NavItem
import com.example.nextask.ui.homepage.HomepageScreen
import com.example.nextask.ui.login.LoginScreen
import com.example.nextask.ui.login.LoginUiState
import com.example.nextask.ui.login.LoginViewModel
import com.example.nextask.ui.registration.RegisterScreen
import com.example.nextask.ui.registration.RegisterUiState
import com.example.nextask.ui.registration.RegisterViewModel
import com.example.nextask.ui.tasklist.TasklistPage
import com.example.nextask.ui.taskmanipulation.AddTaskPage
import com.example.nextask.ui.taskmanipulation.TaskUiState
import com.example.nextask.ui.taskmanipulation.TaskViewModel
import com.example.nextask.ui.theme.NexTaskTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NexTaskTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    var currentUserId by remember { mutableStateOf(-1) }
    var currentUserName by remember { mutableStateOf("User") }

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            val loginViewModel: LoginViewModel = viewModel()
            val uiState = loginViewModel.uiState

            LaunchedEffect(uiState) {
                when (uiState) {
                    is LoginUiState.Success -> {
                        currentUserId = uiState.userId
                        currentUserName = uiState.name
                        Toast.makeText(context, "Welcome ${uiState.name}!", Toast.LENGTH_SHORT).show()
                        navController.navigate(NavItem.Home.route) {
                            popUpTo("login") { inclusive = true }
                        }
                        loginViewModel.resetState()
                    }
                    is LoginUiState.Error -> {
                        Toast.makeText(context, uiState.message, Toast.LENGTH_SHORT).show()
                        loginViewModel.resetState()
                    }
                    else -> {}
                }
            }

            LoginScreen(
                onLoginClick = { email, password ->
                    loginViewModel.loginUser(email, password)
                },
                onSignUpClick = {
                    navController.navigate("register")
                }
            )
        }
        composable("register") {
            val registerViewModel: RegisterViewModel = viewModel()
            val uiState = registerViewModel.uiState

            LaunchedEffect(uiState) {
                when (uiState) {
                    is RegisterUiState.Success -> {
                        Toast.makeText(context, "Registration Successful!", Toast.LENGTH_SHORT).show()
                        navController.navigate("login") {
                            popUpTo("register") { inclusive = true }
                        }
                        registerViewModel.resetState()
                    }
                    is RegisterUiState.Error -> {
                        Toast.makeText(context, uiState.message, Toast.LENGTH_SHORT).show()
                        registerViewModel.resetState()
                    }
                    else -> {}
                }
            }

            RegisterScreen(
                onSignUpClick = { username, email, password ->
                    registerViewModel.registerUser(username, email, password)
                },
                onSigninClick = {
                    navController.navigate("login")
                }
            )
        }
        composable(NavItem.Home.route) {
            val sampleStats = DashboardStats(remainingTasks = 2, dueToday = 1, completedTasks = 15)
            val sampleTasks = listOf(
                PriorityTask(
                    title = "Final Project Presentation", subject = "IT140P",
                    deadline = "2026-07-15", priority = "High",
                    progress = 0.30f, loggedHours = 1.30, totalHours = 4.30
                )
            )
            HomepageScreen(
                userName = currentUserName,
                stats = sampleStats,
                tasks = sampleTasks,
                onViewAnalyticsClick = {
                    navController.navigate(NavItem.Analytics.route)
                },
                onViewTasksClick = {
                    navController.navigate(NavItem.Tasks.route)
                },
                onAddTaskClick = {
                    navController.navigate("add_task")
                },
                navController = navController
            )
        }
        composable(NavItem.Tasks.route) {
            // Sample data for now
            val sampleTasks = listOf(
                PriorityTask(
                    title = "Final Project Presentation", subject = "IT140P",
                    deadline = "2026-07-15", priority = "High",
                    progress = 0.30f, loggedHours = 1.30, totalHours = 4.30
                )
            )
            TasklistPage(
                userName = currentUserName,
                tasks = sampleTasks,
                navController = navController,
                onViewAnalyticsClick = {},
                onViewTasksClick = {},
                onAddTaskClick = {
                    navController.navigate("add_task")
                },
                onEditTaskClick = {
                    // Navigate to edit task
                }
            )
        }
        composable("add_task") {
            val taskViewModel: TaskViewModel = viewModel()
            val uiState = taskViewModel.uiState

            LaunchedEffect(uiState) {
                when (uiState) {
                    is TaskUiState.Success -> {
                        Toast.makeText(context, "Task Created Successfully!", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                        taskViewModel.resetState()
                    }
                    is TaskUiState.Error -> {
                        Toast.makeText(context, uiState.message, Toast.LENGTH_SHORT).show()
                        taskViewModel.resetState()
                    }
                    else -> {}
                }
            }

            AddTaskPage(
                onBackClick = {
                    navController.popBackStack()
                },
                onCreateTaskClick = { title, subject, date, time, hours, priority ->
                    val estimatedHours = hours.toDoubleOrNull() ?: 0.0
                    taskViewModel.createTask(
                        userId = currentUserId,
                        title = title,
                        subject = subject,
                        deadlineDate = date,
                        deadlineTime = time,
                        estimatedHours = estimatedHours,
                        priorityWeight = priority
                    )
                }
            )
        }
        composable(NavItem.Analytics.route) {
            Surface(modifier = Modifier.fillMaxSize()) {
                Text(text = "Analytics Page (Coming Soon)", modifier = Modifier.padding(16.dp))
            }
        }
        composable(NavItem.Profile.route) {
            Surface(modifier = Modifier.fillMaxSize()) {
                Text(text = "Profile Page (Coming Soon)", modifier = Modifier.padding(16.dp))
            }
        }
    }
}
