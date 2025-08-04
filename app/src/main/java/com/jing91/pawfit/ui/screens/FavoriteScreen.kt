package com.jing91.pawfit.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.jing91.pawfit.ui.components.PetTopAppBar
import com.jing91.pawfit.viewmodel.PetViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(navController: NavController) {
    val viewModel: PetViewModel = viewModel()
    val pets by viewModel.allPets.collectAsState()

    Scaffold(
        topBar = { PetTopAppBar(title = "Your pets") },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = false,
                    onClick = { navController.navigate("home") }
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
                    selected = true,
                    onClick = { /* Already on this screen */ }
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
        Column(modifier = Modifier.padding(innerPadding)) {
            if (pets.isEmpty()) {
                Text("No pets added yet.", modifier = Modifier.padding(16.dp))
            } else {
                LazyColumn {
                    items(pets) { pet ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(text = "Name: ${pet.name}")
                                Text(text = "Breed: ${pet.breed}")
                                Text(text = "Birthday: ${pet.birthday}")
                            }
                        }
                    }
                }
            }
        }
    }
}
