package be.marche.mercredi.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class Plaine(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val intitule: String,
    val inscriptionOuverture: Boolean,
    val prix1: String,
    val prix2: String,
    val prix3: String,
    val remarques: String?
)