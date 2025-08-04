package com.jing91.pawfit.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.jing91.pawfit.database.AppDatabase
import com.jing91.pawfit.database.Pet
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PetViewModel(application: Application) : AndroidViewModel(application) {
    private val petDao = AppDatabase.getDatabase(application).petDao()
    val allPets: StateFlow<List<Pet>> = petDao.getAllPets()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    fun addPet(pet: Pet, onSuccess: () -> Unit) {
        viewModelScope.launch {
            petDao.insert(pet)
            onSuccess()
        }
    }
}
