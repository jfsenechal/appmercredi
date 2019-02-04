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
    val questionId: Int,
    var reponse: String,//oui ou non
    var remarque: String?
)