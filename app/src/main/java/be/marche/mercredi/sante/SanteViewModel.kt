package be.marche.mercredi.sante

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.marche.mercredi.entity.SanteFiche
import be.marche.mercredi.entity.SanteQuestion
import be.marche.mercredi.entity.SanteReponse
import kotlinx.coroutines.launch

class SanteViewModel(
    private val santeRepository: SanteRepository
) : ViewModel() {

    var santeFiche: LiveData<SanteFiche>? = null
    var santeReponses: LiveData<List<SanteReponse>>? = null

    var santeQuestions: LiveData<List<SanteQuestion>>? = null
    val santeQuestion  = MutableLiveData<SanteQuestion>()
    val santeReponse  = MutableLiveData<SanteReponse>()

    init {
        santeQuestions = getAllQuestions()
    }

    fun loadQuestion(questionId: Int) {
        santeQuestion.value = santeRepository.getQuestionById(questionId).value
    }

    fun loadReponse(questionId: Int, santeFicheId: Int) {
        santeReponse.value = santeRepository.getReponseBySanteFicheIdAndQuestionId(santeFicheId, questionId).value
    }

    fun getAllQuestions(): LiveData<List<SanteQuestion>> {
        return santeRepository.getAllQuestions()
    }

    fun getQuestionById(questionId: Int): LiveData<SanteQuestion> {
        return santeRepository.getQuestionById(questionId)
    }

    fun getReponsesBySanteFicheId(santeFicheId: Int): LiveData<List<SanteReponse>> {
        return santeRepository.getReponsesBySanteFicheId(santeFicheId)
    }

    fun getSanteFicheByEnfantId(enfantId: Int): LiveData<SanteFiche> {
        santeFiche = santeRepository.getSanteFicheByEnfantId(enfantId)
        return santeFiche as LiveData<SanteFiche>
    }

    fun getReponseBySanteFicheIdAndQuestionId(santeFicheId: Int, questionId: Int): LiveData<SanteReponse> {
        return santeRepository.getReponseBySanteFicheIdAndQuestionId(santeFicheId, questionId)
    }

    fun insertSanteQuestions(questions: List<SanteQuestion>) {
        viewModelScope.launch {
            santeRepository.insertQuestions(questions)
        }
    }

    fun insertReponses(reponses: List<SanteReponse>) {
        viewModelScope.launch {
            santeRepository.insertReponses(reponses)
        }
    }

    fun insertSanteFiches(santeFiches: List<SanteFiche>) {
        viewModelScope.launch {
            santeRepository.insertSanteFiches(santeFiches)
        }
    }

    fun insertReponse(santeReponse: SanteReponse) {
        viewModelScope.launch {
            santeRepository.insertReponse(santeReponse)
        }
    }

    fun insertSanteFiche(santeFiche: SanteFiche) {
        viewModelScope.launch {
            santeRepository.insertSanteFiche(santeFiche)
        }
    }

}
