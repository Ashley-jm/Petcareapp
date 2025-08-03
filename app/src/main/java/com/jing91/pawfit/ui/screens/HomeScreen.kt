package com.jing91.pawfit.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.jing91.pawfit.R
import com.jing91.pawfit.ui.theme.PetcareAppTheme
import com.jing91.pawfit.viewmodel.ReminderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    userName: String = "User",
    petName: String = "Your Pet"
) {
    val reminderViewModel: ReminderViewModel = viewModel()
    val reminders by reminderViewModel.reminders.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Home", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        IconButton(onClick = {
                            navController.navigate("add_pet")
                        }) {
                            Icon(Icons.Default.Add, contentDescription = "Add Pet")
                        }
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = true,
                    onClick = { /* Stay on home */ }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Notifications, contentDescription = "Reminder") },
                    label = { Text("Reminder") },
                    selected = false,
                    onClick = { navController.navigate("reminder") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Favorite, contentDescription = "Pet") },
                    label = { Text("Pet") },
                    selected = false,
                    onClick = { navController.navigate("pet") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "User") },
                    label = { Text("User") },
                    selected = false,
                    onClick = { navController.navigate("user") }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            // 🐾 Pet Info Section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("$petName (Owner: $userName)", style = MaterialTheme.typography.headlineSmall)
                }
            }

            // 📝 Reminder Section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Reminder", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { navController.navigate("reminder") }) {
                        Text("View All Reminders")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(reminders) { reminder ->
                            ReminderCard(reminder.title, reminder.time)
                        }
                    }
                }
            }

            // ❤️ Health Info Section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Health Information", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        EditableHealthCard("Food", "2 meals/day")
                        EditableHealthCard("Weight", "6Kg")
                    }
                }
            }
        }
    }
}

@Composable
fun ReminderCard(title: String, time: String) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(12.dp)) {
            Image(painter = painterResource(id = R.drawable.dog), contentDescription = null)
            Text(title, fontSize = 14.sp)
            Text(time, fontSize = 12.sp)
        }
    }
}

@Composable
fun EditableHealthCard(title: String, value: String) {
    Card(
        modifier = Modifier
            .size(150.dp, 100.dp)
            .clickable {
                // TODO: 弹出对话框或跳转编辑页面
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(title, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Text(value, fontSize = 20.sp)
            Text("Tap to edit", fontSize = 12.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {

    PetcareAppTheme {
        HomeScreen(navController = rememberNavController())
    }
}