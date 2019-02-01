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
data class SanteFiche(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val enfantId: Int,
    val personneUrgence: String?,
    val medecinNom: String?,
    val medecinTelephone: String?,
    val remarques: String?
)