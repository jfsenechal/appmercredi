package be.marche.mercredi.sync

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.marche.mercredi.enfant.EnfantRepository
import be.marche.mercredi.presence.PresenceRepository
import be.marche.mercredi.repository.MercrediRepository
import be.marche.mercredi.repository.MercrediService
import be.marche.mercredi.sante.SanteRepository
import be.marche.mercredi.tuteur.TuteurRepository
import kotlinx.coroutines.launch

class SyncViewModel(
    val mercrediService: MercrediService,
    val enfantRepository: EnfantRepository,
    val tuteurRepository: TuteurRepository,
    val mercrediRepository: MercrediRepository,
    val presenceRepository: PresenceRepository,
    val santeRepository: SanteRepository
) :
    ViewModel() {

var token: String? = null
    fun refreshData() {

        viewModelScope.launch {

            val request = mercrediService.getAllData()

            val response = request.await()

            response.let {
                mercrediRepository.insertEcoles(it.ecoles)
                mercrediRepository.insertAnneesScolaires(it.annees)
                enfantRepository.insertEnfants(it.enfants)
                tuteurRepository.insertTuteurs(it.tuteur)
                mercrediRepository.insertJours(it.jours)
                santeRepository.insertQuestions(it.santeQuestions)
                santeRepository.insertSanteFiches(it.santeFiches)
                santeRepository.insertReponses(it.santeReponses)
                presenceRepository.insertPresences(it.presences)
            }
        }
    }

}