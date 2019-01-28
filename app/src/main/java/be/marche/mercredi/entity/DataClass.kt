package be.marche.mercredi.entity

import androidx.room.*


@Entity()
data class Ecole(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val nom: String

) {
    override fun toString(): String {
        return nom
    }
}

@Entity()
data class AnneeScolaire(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val nom: String
) {
    override fun toString(): String {
        return nom
    }
}

data class ResponseRetrofit(
    val isError: Boolean,
    val message: String
)