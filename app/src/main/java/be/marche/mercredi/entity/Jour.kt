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
data class Jour(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val enfantId: Int,
    val date_jour: String,
    val date_jour_fr: String,
    val prix1: String,
    val prix2: String,
    val prix3: String,
    val remarques: String?,
    val color: String?
)