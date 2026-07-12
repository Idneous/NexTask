package com.example.nextask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.nextask.ui.homepage.DashboardStats
import com.example.nextask.ui.homepage.HomepageScreen
import com.example.nextask.ui.homepage.PriorityTask
import com.example.nextask.ui.login.LoginScreen
import com.example.nextask.ui.registration.RegisterScreen
import com.example.nextask.ui.theme.NexTaskTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NexTaskTheme() {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen(
                        onLoginClick = { username, password ->

                            println("Logging in with: $username and $password")
                        },
                        onSignUpClick = {}
                    )
                }
            }
        }
    }
}



/*@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    //preview
    NexTaskTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            RegisterScreen(
                onSignUpClick = { username, email, password ->
                    // function
                },
                onSigninClick = {},
            )
        }
    }
}*/
/*
@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    //preview
    NexTaskTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            LoginScreen(
                onLoginClick = { username, password ->
                    // function
                },
                onSignUpClick = {},
            )
        }
    }
}*/


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DashboardScreenPreview() {
    val sampleStats = DashboardStats(remainingTasks = 2, dueToday = 1, completedTasks = 15)
    val sampleTasks = listOf(
        PriorityTask(
            title = "Final Project Presentation", subject = "IT140P",
            deadline = "2026-07-15", priority = "High",
            progress = 0.30f, loggedHours = 1.30, totalHours = 4.30
        ),
        PriorityTask(
            title = "Data Science Quiz", subject = "CS191-4P",
            deadline = "2026-07-22", priority = "Low",
            progress = 0.00f, loggedHours = 0.00, totalHours = 1.00
        )
    )
    //sample data
    HomepageScreen(
        userName = "Juan",
        stats = sampleStats,
        tasks = sampleTasks,
        onViewAnalyticsClick = {},
        onViewTasksClick = {},
        onAddTaskClick = {},
        navController = rememberNavController(),
        modifier = Modifier
    )
}

