package com.example.budgetwiseexpensetracker.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "personal_info")
data class PersonalInfoEntity(
    @PrimaryKey(autoGenerate = false) val id: Int? = 1, // Single record for personal info
    val name: String,
    val imagePath: ByteArray? // Path to the image in the device's storage
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PersonalInfoEntity

        if (id != other.id) return false
        if (name != other.name) return false
        if (!imagePath.contentEquals(other.imagePath)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + name.hashCode()
        result = 31 * result + imagePath.contentHashCode()
        return result
    }
}
