package be.marche.mercredi.tuteur

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.marche.mercredi.entity.Tuteur
import kotlinx.coroutines.launch

class TuteurViewModel(
    private val tuteurRepository: TuteurRepository
) :
    ViewModel() {

    fun getTuteurById(tuteurId: Int): LiveData<Tuteur> {
        return tuteurRepository.getTuteurById(tuteurId)
    }

    fun saveReal(tuteur: Tuteur) {
        viewModelScope.launch {
            tuteurRepository.saveReal(tuteur)
        }
    }

    fun save(tuteur: Tuteur) {
        viewModelScope.launch {
            tuteurRepository.update(tuteur)
        }
    }

    var tuteur: LiveData<Tuteur>? = null
}