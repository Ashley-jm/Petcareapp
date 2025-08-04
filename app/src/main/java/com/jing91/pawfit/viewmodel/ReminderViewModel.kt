package com.jing91.pawfit.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.jing91.pawfit.database.AppDatabase
import com.jing91.pawfit.database.Reminder
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ReminderViewModel(application: Application) : AndroidViewModel(application) {

    private val reminderDao = AppDatabase.getDatabase(application).reminderDao()

    // Flow: 实时获取所有提醒
    val reminders: StateFlow<List<Reminder>> = reminderDao.getAllReminders()
        .map { list ->
            list.sortedBy { parseDate(it.time) }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // 插入 reminder
    fun insert(reminder: Reminder, onComplete: () -> Unit = {}) {
        viewModelScope.launch {
            reminderDao.insert(reminder)
            onComplete()
        }
    }

    // 删除 reminder
    fun deleteReminder(reminder: Reminder) {
        viewModelScope.launch {
            reminderDao.delete(reminder)
        }
    }

    // 时间字符串转 Date 对象（用于排序）
    private fun parseDate(time: String): java.util.Date? {
        return try {
            val format = java.text.SimpleDateFormat("dd MMM - h a", java.util.Locale.getDefault())
            format.parse(time)
        } catch (e: Exception) {
            null
        }
    }
}
