package com.example.refill.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a single refill log entry saved in the local Room database.
 *
 * @param id Unique identifier generated automatically by Room.
 * @param timestamp System time in milliseconds when the refill was logged.
 * @param type The type of refill, defaulting to "water" for now.
 */
@Entity(tableName = "refills")
data class RefillEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val timestamp: Long,
    val type: String = "water"
)
