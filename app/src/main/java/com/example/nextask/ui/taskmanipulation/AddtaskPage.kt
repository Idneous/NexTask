package com.example.nextask.ui.taskmanipulation

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar
import java.util.Locale

@Composable
fun AddTaskPage(
    onBackClick: () -> Unit,
    onCreateTaskClick: (title: String, subject: String, date: String, time: String, hours: String, priority: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val calendar = remember { Calendar.getInstance() }

    var title by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }

    var selectedDate by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf("") }

    var estimatedHours by remember { mutableStateOf("") }
    var priorityWeight by remember { mutableFloatStateOf(3f) }

    val primaryBlue = Color(0xFF0F4C81)

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.Black, CircleShape)
                    .clickable { onBackClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Add New Task",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Title
            FormLabel(text = "Title")
            CustomOutlinedTextField(
                value = title,
                onValueChange = { title = it },
                placeholder = "Enter Title"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Subject
            FormLabel(text = "Subject")
            CustomOutlinedTextField(
                value = subject,
                onValueChange = { subject = it },
                placeholder = "Enter Subject"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Deadline row
            FormLabel(text = "Deadline")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Date Picker
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .border(1.dp, Color.Black, RoundedCornerShape(12.dp))
                        .clickable {
                            val datePickerDialog = DatePickerDialog(
                                context,
                                { _, year, month, dayOfMonth ->

                                    selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth)
                                },
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)
                            )
                            datePickerDialog.show()
                        }
                        .padding(horizontal = 12.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = selectedDate.ifEmpty { "Select Date" },
                            color = if (selectedDate.isEmpty()) Color.Gray else Color.Black,
                            fontSize = 14.sp
                        )
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Calendar",
                            tint = Color.Black,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                // Time Picker
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .border(1.dp, Color.Black, RoundedCornerShape(12.dp))
                        .clickable {
                            val timePickerDialog = TimePickerDialog(
                                context,
                                { _, hour, minute ->
                                    selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hour, minute)
                                },
                                calendar.get(Calendar.HOUR_OF_DAY),
                                calendar.get(Calendar.MINUTE),
                                true
                            )
                            timePickerDialog.show()
                        }
                        .padding(horizontal = 12.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = selectedTime.ifEmpty { "Select Time" },
                            color = if (selectedTime.isEmpty()) Color.Gray else Color.Black,
                            fontSize = 14.sp
                        )
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Clock",
                            tint = Color.Black,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Estimated Hours
            FormLabel(text = "Estimated Hours")
            CustomOutlinedTextField(
                value = estimatedHours,
                onValueChange = { estimatedHours = it },
                placeholder = "Enter Hours",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.width(160.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Priority Slider
            FormLabel(text = "Priority Weight")

            Column(modifier = Modifier.fillMaxWidth()) {
                Slider(
                    value = priorityWeight,
                    onValueChange = { priorityWeight = it },
                    valueRange = 1f..5f,
                    steps = 3,
                    colors = SliderDefaults.colors(
                        thumbColor = primaryBlue,
                        activeTrackColor = primaryBlue,
                        inactiveTrackColor = Color.LightGray,
                        activeTickColor = Color.Transparent,
                        inactiveTickColor = Color.Transparent
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    listOf("1", "2", "3", "4", "5").forEach { num ->
                        Text(
                            text = num,
                            fontSize = 12.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Confirm button
            Button(
                onClick = {
                    onCreateTaskClick(
                        title, subject, selectedDate, selectedTime, estimatedHours, priorityWeight.toInt()
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(bottom = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = primaryBlue),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text(
                    text = "CREATE TASK",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun FormLabel(text: String) {
    Text(
        text = text,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
        modifier = Modifier.padding(bottom = 6.dp)
    )
}

@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(text = placeholder, color = Color.Gray, fontSize = 14.sp) },
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black,
            focusedContainerColor = Color(0xFFF9F9F9),
            unfocusedContainerColor = Color(0xFFF9F9F9)
        ),
        shape = RoundedCornerShape(12.dp),
        keyboardOptions = keyboardOptions,
        modifier = modifier.height(52.dp)
    )
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddTaskPagePreview() {
    AddTaskPage(onBackClick = {}, onCreateTaskClick = { _, _, _, _, _, _ -> })
}