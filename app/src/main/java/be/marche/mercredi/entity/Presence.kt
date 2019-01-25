package be.marche.mercredi.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Enfant::class,
            parentColumns = ["id"],
            childColumns = ["enfantId"]
        )
    ]
)
data class Presence(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val enfantId: Int,
    val date: String,
    val date_fr: String,
    val absent: Boolean = false,
    var payer: Boolean
)