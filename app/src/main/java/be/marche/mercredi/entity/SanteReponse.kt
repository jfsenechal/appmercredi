package be.marche.mercredi.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = SanteFiche::class,
            parentColumns = ["id"],
            childColumns = ["santeFicheId"]
        )
    ]
)
data class SanteReponse(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val santeFicheId: Int,
    val reponse: String,//oui ou non
    val remarque: String
)