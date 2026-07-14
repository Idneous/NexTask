package com.example.nextask.data.model

import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.max

@Serializable
data class CreateTaskRequest(
    val user_id: Int,
    val title: String,
    val subject: String,
    val deadline: String, // Expects "YYYY-MM-DD HH:MM:SS"
    val estimated_hours: Double,
    val priority_weight: Int
)

@Serializable
data class CreateTaskResponse(
    val success: Boolean,
    val message: String,
    val task_id: Int? = null
)

@Serializable
data class UpdateProgressRequest(
    val task_id: Int,
    val progress: Float
)

@Serializable
data class DeleteTaskRequest(
    val task_id: Int
)

@Serializable
data class GenericResponse(
    val success: Boolean,
    val message: String? = null
)

@Serializable
data class ProductivityAnalyticsResponse(
    val success: Boolean,
    val completion_rate: Float,
    val average_hours_per_task: Double,
    val message: String? = null
)

@Serializable
data class DashboardStats(
    val remainingTasks: Int,
    val dueToday: Int,
    val completedTasks: Int
)

@Serializable
data class PriorityTask(
    val title: String,
    val subject: String,
    val deadline: String, // Expected format: "YYYY-MM-DD"
    val priority: String,
    val progress: Float,
    val loggedHours: Double,
    val totalHours: Double
) {
    //get days left by deadline minus date today
    val daysLeft: Double
        get() = try {
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

            val now = Date()
            val targetDate = formatter.parse(deadline) // Target deadline date

            if (targetDate != null) {
                // Difference in milliseconds
                val differenceInMs = targetDate.time - now.time

                // (1000 ms * 60s * 60m * 24h = 86,400,000 ms in a day)
                val decimalDays = differenceInMs.toDouble() / 86400000.0

                max(0.0, decimalDays)
            } else {
                0.0
            }
        } catch (e: Exception) {
            0.0
        }
}
