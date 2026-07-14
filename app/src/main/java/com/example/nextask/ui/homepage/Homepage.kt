package com.example.nextask.ui.homepage

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.nextask.ui.NavItem
import com.example.nextask.data.model.PriorityTask
import com.example.nextask.data.model.DashboardStats
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.max

@Composable
fun HomepageScreen(
    userName: String,
    stats: DashboardStats,
    tasks: List<PriorityTask>,
    navController: NavController,
    onViewAnalyticsClick: () -> Unit,
    onViewTasksClick: () -> Unit,
    onAddTaskClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color.White,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddTaskClick,
                containerColor = Color(0xFF0F4C81),
                contentColor = Color.White,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .padding(bottom = 16.dp, end = 8.dp)
                    .size(64.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Task",
                    modifier = Modifier.size(32.dp)
                )
            }
        },
        bottomBar = {
            BottomNavigation(navController = navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // greeting
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "Welcome, $userName!",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // summary
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatCard(title = "Remaining Tasks", value = stats.remainingTasks.toString(), modifier = Modifier.weight(1f))
                StatCard(title = "Due Today", value = stats.dueToday.toString(), modifier = Modifier.weight(1f))
                StatCard(title = "Completed", value = stats.completedTasks.toString(), modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(28.dp))

            // priority queue
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Your Priority Queue",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = "View Tasks",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline,
                    color = Color.Black,
                    modifier = Modifier.clickable { onViewTasksClick() }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(tasks) { task ->
                    PriorityTaskItem(task = task)
                }
            }
        }
    }
}

@Composable
fun StatCard(title: String, value: String, modifier: Modifier = Modifier) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF16558F)),
        shape = RoundedCornerShape(24.dp),
        modifier = modifier.height(96.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
fun PriorityTaskItem(task: PriorityTask) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
            .padding(12.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = "${task.title} (${task.subject})",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )

                val priorityColor = when(task.priority.lowercase()) {
                    "high" -> Color(0xFFC94A4A)
                    "medium" -> Color(0xFFDDA83A)
                    else -> Color(0xFF4CAF50)
                }
                Text(
                    text = buildAnnotatedString {
                        append("Priority: ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = priorityColor)) {
                            append(task.priority)
                        }
                    },
                    fontSize = 11.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(4.dp))


            val formattedDaysLeft = String.format("%.1f", task.daysLeft)
            Text(
                text = "Deadline: ${task.deadline} ($formattedDaysLeft days left)",
                fontSize = 11.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Progress: ${(task.progress * 100).toInt()}%",
                    fontSize = 11.sp,
                    color = Color.Black
                )
                Text(
                    text = String.format("%.2f / %.2f hrs logged", task.loggedHours, task.totalHours),
                    fontSize = 11.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            LinearProgressIndicator(
                progress = { task.progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(CircleShape),
                color = Color(0xFF5B9FFA),
                trackColor = Color(0xFFCEE3FF)
            )
        }
    }
}

@Composable
fun BottomNavigation(navController: NavController) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    val items = listOf(NavItem.Home, NavItem.Tasks, NavItem.Analytics, NavItem.Profile)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .height(64.dp)
            .background(Color(0xFFC3DDFF), RoundedCornerShape(32.dp)),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                BottomNavItem(
                    item = item,
                    isSelected = currentRoute == item.route,
                    onItemClick = {
                        if (currentRoute != item.route) {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun RowScope.BottomNavItem(
    item: NavItem,
    isSelected: Boolean,
    onItemClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .weight(1f)
            .clickable { onItemClick() }
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.title,
            tint = Color.Black,
            modifier = Modifier
                .size(28.dp)
                .border(if (isSelected) 1.5.dp else 0.dp, Color.Black, CircleShape)
                .padding(2.dp)
        )
        Text(
            text = item.title,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

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

    HomepageScreen(
        userName = "Juan",
        stats = sampleStats,
        tasks = sampleTasks,
        navController = rememberNavController(),
        onViewAnalyticsClick = {},
        onViewTasksClick = {},
        onAddTaskClick = {}
    )
}