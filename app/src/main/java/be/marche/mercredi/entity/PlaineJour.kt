package be.marche.mercredi.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Plaine::class,
            parentColumns = ["id"],
            childColumns = ["plaineId"]
        )
    ]
)
data class PlaineJour(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val plaineId: Int,
    val date_jour: String,
    val date_jour_fr: String
)