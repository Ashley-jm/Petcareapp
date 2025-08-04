package com.jing91.pawfit.ui.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.jing91.pawfit.database.Reminder
import com.jing91.pawfit.ui.components.PetTopAppBar
import com.jing91.pawfit.viewmodel.ReminderViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddReminderScreen(navController: NavController) {
    val reminderViewModel: ReminderViewModel = viewModel()
    val context = LocalContext.current

    var title by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }

    val calendar = Calendar.getInstance()

    fun showDateTimePicker(context: Context) {
        DatePickerDialog(context, { _, year, month, day ->
            TimePickerDialog(context, { _, hour, minute ->
                calendar.set(year, month, day, hour, minute)
                val format = SimpleDateFormat("dd MMM - h a", Locale.getDefault())
                time = format.format(calendar.time)
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show()
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    Scaffold(
        topBar = { PetTopAppBar(title = "Add reminder") }

    )  { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Reminder Title") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = time,
                onValueChange = {},
                label = { Text("Time") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showDateTimePicker(context) },
                readOnly = true
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (title.isNotBlank() && time.isNotBlank()) {
                        val reminder = Reminder(title = title, time = time)
                        reminderViewModel.insert(reminder) {
                            navController.navigate("reminder")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Reminder")
            }
        }
    }
}
