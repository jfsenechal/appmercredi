package be.marche.mercredi.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class SanteQuestion(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val intitule: String,
    val complement: Boolean,
    val complement_label: String
) {
    override fun toString(): String {
        return intitule
    }
}