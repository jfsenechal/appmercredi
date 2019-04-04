package be.marche.mercredi.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Ecole::class,
            parentColumns = ["id"],
            childColumns = ["ecoleId"]
        )
    ]
)
data class Enfant(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var nom: String,
    var prenom: String,
    var birthday: Date,
    @SerializedName("annee_scolaire")
    var anneeScolaire: String,
    @SerializedName("ecole_id")
    var ecoleId: Int,
    @SerializedName("numero_national")
    var numeroNational: String?,
    var sexe: String?,
    var slugname: String?,
    var remarques: String?,
    @ColumnInfo(name = "photo_url")
    @SerializedName("photo_url")
    var photoUrl: String
)