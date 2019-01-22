package be.marche.mercredi.tuteur

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import be.marche.mercredi.entity.Tuteur
import be.marche.mercredi.repository.MercrediService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class TuteurViewModel(
    private val tuteurRepository: TuteurRepository,
         private             val mercrediService: MercrediService) :
    ViewModel() {

    /**
     * This is the job for all coroutines started by this ViewModel.
     *
     * Cancelling this job will cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob = Job()

    /**
     * This is the scope for all coroutines launched by [TuteurViewModel].
     *
     * Since we pass [viewModelJob], you can cancel all coroutines launched by [viewModelScope] by calling
     * viewModelJob.cancel().  This is called in [onCleared].
     */
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    /**
     * Cancel all coroutines when the ViewModel is cleared.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun getTuteurById(tuteurId: Int): LiveData<Tuteur> {
        return tuteurRepository.getTuteurById(tuteurId)
    }

    fun saveReal(tuteur: Tuteur, token: String) {
        viewModelScope.launch {

            val request = mercrediService.updateTuteur(token, tuteur)
            request.enqueue(object : Callback<Tuteur> {
                override fun onFailure(call: Call<Tuteur>, t: Throwable) {

                }

                override fun onResponse(call: Call<Tuteur>, response: Response<Tuteur>) {
                    response.let {
                        val user = it.body()
                        if (user != null) {
                            Timber.i("zeze reponse tuteur ok ${response.body()}")
                            save(user)

                        } else {
                            Timber.i("zeze reponse tuteur ko ${response.body()}")

                        }
                    }
                }
            })
        }
    }

    fun save(tuteur: Tuteur) {
        viewModelScope.launch {
            tuteurRepository.update(tuteur)
        }
    }

    var tuteur: LiveData<Tuteur>? = null
}