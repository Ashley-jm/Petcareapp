package com.jing91.pawfit.ui.screens

import android.app.Application
import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.*
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.jing91.pawfit.R
import com.jing91.pawfit.database.Pet
import com.jing91.pawfit.ui.components.PetTopAppBar
import com.jing91.pawfit.viewmodel.PetViewModel
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutPetScreen(navController: NavController) {
    val context = LocalContext.current
    val petViewModel: PetViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory(context.applicationContext as Application)
    )
    var petName by remember { mutableStateOf("") }
    var birthday by remember { mutableStateOf("") }
    var breed by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf("Dog") }



    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, day: Int ->
            birthday = String.format("%02d/%02d/%04d", day, month + 1, year)
        },
        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
    )

    Scaffold(
        topBar = { PetTopAppBar(title = "About Your Pet") }

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {

            Box(
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.CenterHorizontally)
                    .clickable { /* 上传图片逻辑 */ }
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Pet Image",
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = petName,
                onValueChange = { petName = it },
                label = { Text("Name of Pet") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = birthday,
                onValueChange = {},
                label = { Text("Birthday") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { datePickerDialog.show() },
                readOnly = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = breed,
                onValueChange = { breed = it },
                label = { Text("Breed") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                PetTypeButton(type = "Dog", selected = selectedType == "Dog") {
                    selectedType = "Dog"
                }
                PetTypeButton(type = "Cat", selected = selectedType == "Cat") {
                    selectedType = "Cat"
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (petName.isNotBlank() && breed.isNotBlank() && birthday.isNotBlank()) {
                        val pet = Pet(
                            name = petName,
                            breed = breed,
                            birthday = birthday,
                            type = selectedType
                        )
                        petViewModel.addPet(pet) {
                            navController.navigate("favorites")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Pet")
            }

        }
    }
}

@Composable
fun PetTypeButton(type: String, selected: Boolean, onClick: () -> Unit) {
    val icon = if (type == "Dog") R.drawable.ic_dog else R.drawable.ic_cat

    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .padding(4.dp)
            .border(
                width = if (selected) 2.dp else 1.dp,
                color = if (selected) Color(0xFFFF9800) else Color.LightGray,
                shape = MaterialTheme.shapes.small
            ),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = if (selected) Color(0xFFFFF3E0) else Color.White,
            contentColor = Color.Black
        )
    ) {
        Icon(painter = painterResource(id = icon), contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(type)
    }
}

