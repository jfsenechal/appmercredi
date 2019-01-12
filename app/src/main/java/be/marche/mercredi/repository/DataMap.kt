package be.marche.mercredi.repository

import be.marche.mercredi.entity.*

data class MercrediData(
    val enfants: List<Enfant>,
    val tuteur: Tuteur,
    val ecoles: List<Ecole>,
    val annees: List<AnneeScolaire>,
    val jours: List<Jour>
)
