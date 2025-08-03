package com.jing91.pawfit.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.jing91.pawfit.database.AppDatabase
import com.jing91.pawfit.database.Reminder
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ReminderViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = AppDatabase.getDatabase(application).reminderDao()

    val reminders = dao.getAllReminders()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun insertReminder(title: String, time: String) {
        viewModelScope.launch {
            dao.insert(Reminder(title = title, time = time))
        }
    }

    fun deleteReminder(reminder: Reminder) {
        viewModelScope.launch {
            dao.delete(reminder)
        }
    }
}
