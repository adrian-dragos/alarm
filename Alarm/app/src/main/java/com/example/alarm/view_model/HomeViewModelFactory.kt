package com.example.alarm.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.alarm.Domain.AlarmRepository

class HomeViewModelFactory(private val alarmRepository: AlarmRepository)
    : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(alarmRepository) as T
    }
}