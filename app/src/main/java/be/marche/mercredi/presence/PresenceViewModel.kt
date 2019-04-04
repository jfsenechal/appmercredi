package be.marche.mercredi.presence

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.marche.mercredi.entity.Enfant
import be.marche.mercredi.entity.Jour
import be.marche.mercredi.entity.Presence
import be.marche.mercredi.repository.MercrediRepository
import kotlinx.coroutines.launch

class PresenceViewModel(
    private val mercrediRepository: MercrediRepository,
    private val presenceRepository: PresenceRepository
) : ViewModel() {

    var jour: LiveData<Jour>? = null
    var jours: LiveData<List<Jour>>? = null
    var presences: LiveData<List<Presence>>? = null

    fun getPresencesByEnfantId(enfantId: Int) {
        presences = presenceRepository.getPresenceByEnfantId(enfantId)
    }

    fun getJoursByEnfantId(enfantId: Int) {
        jours = mercrediRepository.getJoursByEnfantId(enfantId)
    }

    fun insertPresence(presences: List<Presence>) {
        viewModelScope.launch {
            presenceRepository.insertPresences(presences)
        }
    }

    fun insertPresenceReal(enfant: Enfant, jours: List<Int>) {
        viewModelScope.launch {
            presenceRepository.insertPresenceReal(enfant, jours)
        }
    }
}
