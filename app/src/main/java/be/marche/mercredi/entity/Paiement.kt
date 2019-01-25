package be.marche.mercredi.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class Paiement(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val date: String,
    val montant: String,
    val type: String?,
    val mode: String?
)