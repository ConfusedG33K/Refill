package com.example.refill.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * Repository layer connecting the Room DAO to the UI/ViewModel layer.
 */
class RefillRepository(private val refillDao: RefillDao) {

    /**
     * Inserts a new refill with the current timestamp on IO thread.
     */
    suspend fun logRefill(type: String = "water") {
        withContext(Dispatchers.IO) {
            val entry = RefillEntry(
                timestamp = System.currentTimeMillis(),
                type = type
            )
            refillDao.insertRefill(entry)
        }
    }

    /**
     * Deletes a refill entry on IO thread.
     */
    suspend fun deleteRefill(entry: RefillEntry) {
        withContext(Dispatchers.IO) {
            refillDao.deleteRefill(entry)
        }
    }

    /**
     * Returns a Flow observing today's refills given midnight's timestamp.
     */
    fun getTodayRefills(startOfDayTimestamp: Long): Flow<List<RefillEntry>> {
        return refillDao.getTodayRefills(startOfDayTimestamp)
    }
}
