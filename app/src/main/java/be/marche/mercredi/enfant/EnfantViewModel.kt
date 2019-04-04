package be.marche.mercredi.enfant

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.marche.mercredi.entity.Enfant
import kotlinx.coroutines.launch

class EnfantViewModel(
    private val enfantRepository: EnfantRepository
) : ViewModel() {

    lateinit var enfant: LiveData<Enfant>
    var enfantPhoto: MutableLiveData<String>? = null

    val enfants: LiveData<List<Enfant>> = enfantRepository.getAllEnfants()

    fun getEnfantById(enfantId: Int): LiveData<Enfant> {
        return enfantRepository.getEnfantById(enfantId)
    }

    fun save(enfant: Enfant) {
        viewModelScope.launch {
            enfantRepository.updateEnfant(enfant)
        }
    }

    fun saveReal(enfant: Enfant) {
        viewModelScope.launch {
            enfantRepository.saveReal(enfant)
        }
    }

}