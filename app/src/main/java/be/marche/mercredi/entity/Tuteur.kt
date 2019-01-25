package be.marche.mercredi.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity()
data class Tuteur(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val nom: String,
    val prenom: String,
    var adresse: String,
    @SerializedName("code_postal")
    var codePostal: String,
    var localite: String,
    var telephone: String? = null,
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
    var slugname: String? = null
)