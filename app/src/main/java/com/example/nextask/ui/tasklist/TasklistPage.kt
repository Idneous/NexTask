package com.example.nextask.ui.tasklist

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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Calendar
import java.util.Locale
import kotlin.math.max

data class Tasks(
    val title: String,
    val subject: String,
    val deadline: String, // Expected format: "YYYY-MM-DD"
    val priority: String,
    val progress: Float,
    val loggedHours: Double,
    val totalHours: Double
) {
    val daysLeft: Double
        get() = try {
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val now = Date()
            val targetDate = formatter.parse(deadline)

            if (targetDate != null) {
                val differenceInMs = targetDate.time - now.time
                val decimalDays = differenceInMs.toDouble() / 86400000.0
                max(0.0, decimalDays)
            } else {
                0.0
            }
        } catch (e: Exception) {
            0.0
        }
}

@Composable
fun TasklistPage(
    userName: String,
    tasks: List<Tasks>,
    navController: NavController,
    onViewAnalyticsClick: () -> Unit,
    onViewTasksClick: () -> Unit,
    onAddTaskClick: () -> Unit,
    onEditTaskClick: (Tasks) -> Unit,
    modifier: Modifier = Modifier
) {
    val datetoday = Date()
    val calendar = Calendar.getInstance().apply { time = datetoday }
    val monthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
    val day = calendar.get(Calendar.DAY_OF_MONTH)


    // dropdown state
    var expanded by remember { mutableStateOf(false) }
    val filterOptions = listOf("All", "High", "Medium", "Low")
    var selectedPriorityFilter by remember { mutableStateOf("All") }

    // filter based on prio picked
    val displayTasks = remember(tasks, selectedPriorityFilter) {
        if (selectedPriorityFilter == "All") {
            tasks
        } else {
            tasks.filter { it.priority.equals(selectedPriorityFilter, ignoreCase = true) }
        }
    }

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

            // Greeting
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "Today",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ){
                Text(
                    text = "$monthName $day",
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // dropdown picker
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Filter by Priority:",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )

                Box {
                    Row(
                        modifier = Modifier
                            .width(140.dp)
                            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { expanded = true }
                            .padding(horizontal = 12.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = selectedPriorityFilter,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Dropdown Indicator",
                            tint = Color.Black
                        )
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .width(140.dp)
                            .background(Color.White)
                    ) {
                        filterOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(text = option, color = Color.Black) },
                                onClick = {
                                    selectedPriorityFilter = option
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Heading row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (selectedPriorityFilter == "All") "Your Tasks" else "$selectedPriorityFilter Priority Tasks",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                if (displayTasks.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No tasks found for this priority level.",
                                color = Color.Gray,
                                fontSize = 14.sp
                            )
                        }
                    }
                } else {
                    items(displayTasks) { task ->
                        TaskItem(task = task, onEditClick = { onEditTaskClick(task) })
                    }
                }
            }
        }
    }
}

@Composable
fun TaskItem(
    task: Tasks,
    onEditClick: () -> Unit
) {
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
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "${task.title} (${task.subject})",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(2.dp))

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

                IconButton(
                    onClick = onEditClick,
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Task",
                        tint = Color(0xFF0F4C81),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

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
    val sampleTasks = listOf(
        Tasks(
            title = "Final Project Presentation", subject = "IT140P",
            deadline = "2026-07-15", priority = "High",
            progress = 0.30f, loggedHours = 1.30, totalHours = 4.30
        ),
        Tasks(
            title = "Data Science Quiz", subject = "CS191-4P",
            deadline = "2026-07-22", priority = "Low",
            progress = 0.00f, loggedHours = 0.00, totalHours = 1.00
        )
    )

    TasklistPage(
        userName = "Juan",
        tasks = sampleTasks,
        navController = rememberNavController(),
        onViewAnalyticsClick = {},
        onViewTasksClick = {},
        onAddTaskClick = {},
        onEditTaskClick = {}
    )
}