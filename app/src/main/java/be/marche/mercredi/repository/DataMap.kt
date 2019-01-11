package be.marche.mercredi.repository

import be.marche.mercredi.entity.Ecole
import be.marche.mercredi.entity.Enfant
import be.marche.mercredi.entity.Tuteur

data class MercrediData(
    val enfants: List<Enfant>,
    val tuteur: Tuteur,
    val ecoles: List<Ecole>
)

