package com.example.refill.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) providing database operations for refill entries.
 */
@Dao
interface RefillDao {

    /**
     * Inserts a new refill entry into the database.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRefill(entry: RefillEntry)

    /**
     * Deletes a refill entry from the database.
     */
    @Delete
    suspend fun deleteRefill(entry: RefillEntry)

    /**
     * Observes all refill entries recorded on or after the given [startOfDayTimestamp].
     * Entries are ordered with the most recent refill first.
     */
    @Query("SELECT * FROM refills WHERE timestamp >= :startOfDayTimestamp ORDER BY timestamp DESC")
    fun getTodayRefills(startOfDayTimestamp: Long): Flow<List<RefillEntry>>
}
