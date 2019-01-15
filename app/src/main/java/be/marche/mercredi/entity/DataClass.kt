package be.marche.mercredi.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Ecole::class,
            parentColumns = ["id"],
            childColumns = ["ecoleId"]
        ),
        ForeignKey(
            entity = AnneeScolaire::class,
            parentColumns = ["id"],
            childColumns = ["anneeScolaireId"]
        )
    ]
)
data class Enfant(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var nom: String,
    var prenom: String,
    var birthday: String,
    var anneeScolaireId: Int,
    var ecoleId: Int,
    var numeroNational: String?,
    var sexe: String?,
    var slugname: String?,
    var remarques: String?,
    @ColumnInfo(name = "photo_url")
    @SerializedName("imageUrl")
    var photoUrl: String
)

@Entity()
data class Tuteur(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val nom: String,
    val prenom: String,
    var adresse: String,
    var codePostal: String,
    var localite: String,
    var telephone: String,
    var sexe: String? = null,
    var civilite: String? = null,
    var email: String? = null,
    var gsm: String? = null,
    @SerializedName("telephone_bureau")
    var telephoneBureau: String? = null,
    @SerializedName("nom_conjoint")
    var nomConjoint: String? = null,
    @SerializedName("prenom_conjoint")
    var prenomConjoint: String? = null,
    @SerializedName("telephone_conjoint")
    var telephoneConjoint: String? = null,
    @SerializedName("telephone_bureau_conjoint")
    var telephoneBureauConjoint: String? = null,
    @SerializedName("gsm_conjoint")
    var gsmConjoint: String? = null,
    @SerializedName("email_conjoint")
    var emailConjoint: String? = null,
    var updated: String? = null,
    var slugname: String? = null
)

@Entity()
data class Presence(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val date: String,
    val absent: Boolean = false,
    var payer: Boolean
)

@Entity()
data class Paiement(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val date: String,
    val montant: String,
    val type: String?,
    val mode: String?
)

@Entity()
data class Jour(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @SerializedName("date_jour")
    val date: String,
    val prix1: String,
    val prix2: String,
    val prix3: String,
    val remarques: String?,
    val color: String?
)

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