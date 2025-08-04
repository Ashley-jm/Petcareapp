package com.jing91.pawfit.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.jing91.pawfit.database.AppDatabase
import com.jing91.pawfit.database.Pet
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

open class PetViewModel(application: Application) : AndroidViewModel(application) {
    private val petDao = AppDatabase.getDatabase(application).petDao()
    open val allPets: StateFlow<List<Pet>> = petDao.getAllPets()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    open fun addPet(pet: Pet, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                petDao.insert(pet)
                onSuccess()
            } catch (e: Exception) {
                Log.e("PetViewModel", "Failed to insert pet", e)
            }
        }
    }
    fun deletePet(pet: Pet) {
        viewModelScope.launch {
            try {
                petDao.delete(pet)
            } catch (e: Exception) {
                Log.e("PetViewModel", "Failed to delete pet", e)
            }
        }
    }
}
