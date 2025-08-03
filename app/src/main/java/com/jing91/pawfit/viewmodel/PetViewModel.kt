package com.jing91.pawfit.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.jing91.pawfit.database.AppDatabase
import com.jing91.pawfit.database.Pet
import kotlinx.coroutines.launch

class PetViewModel(application: Application) : AndroidViewModel(application) {
    private val petDao = AppDatabase.getDatabase(application).petDao()

    fun addPet(pet: Pet, onSuccess: () -> Unit) {
        viewModelScope.launch {
            petDao.insert(pet)
            onSuccess()
        }
    }
}
