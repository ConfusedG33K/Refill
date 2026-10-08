package com.example.refill.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.refill.data.RefillEntry
import com.example.refill.data.RefillRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar

/**
 * ViewModel managing UI state for today's refill counter and list.
 */
class RefillViewModel(private val repository: RefillRepository) : ViewModel() {

    /**
     * Calculates the timestamp in milliseconds corresponding to 00:00:00 (midnight) today.
     */
    private fun getStartOfTodayTimestamp(): Long {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return calendar.timeInMillis
    }

    /**
     * StateFlow exposing today's refill entries to the UI.
     */
    val todayRefills: StateFlow<List<RefillEntry>> = repository
        .getTodayRefills(getStartOfTodayTimestamp())
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    /**
     * Adds a new refill entry.
     */
    fun addRefill(type: String = "water") {
        viewModelScope.launch {
            repository.logRefill(type)
        }
    }

    /**
     * Deletes a refill entry.
     */
    fun deleteRefill(entry: RefillEntry) {
        viewModelScope.launch {
            repository.deleteRefill(entry)
        }
    }
}

/**
 * Factory class to instantiate RefillViewModel with repository dependency.
 */
class RefillViewModelFactory(private val repository: RefillRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RefillViewModel::class.java)) {
            return RefillViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
