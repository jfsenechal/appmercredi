package be.marche.mercredi.tuteur

import androidx.lifecycle.LiveData
import be.marche.mercredi.database.TuteurDao
import be.marche.mercredi.entity.Tuteur
import be.marche.mercredi.repository.MercrediService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class TuteurRepository(
    private val tuteurDao: TuteurDao,
    private val mercrediService: MercrediService
) {

    suspend fun update(tuteur: Tuteur) {
        withContext(Dispatchers.IO) {
            tuteurDao.updateTuteurs(listOf(tuteur))
        }
    }

    suspend fun insertTuteurs(tuteur: Tuteur) {
        withContext(Dispatchers.IO) {
            tuteurDao.insertTuteur(tuteur)
        }
    }

    fun getTuteurById(tuteurId: Int): LiveData<Tuteur> {
        return tuteurDao.getTuteurById(tuteurId)
    }

    suspend fun saveReal(tuteur: Tuteur) {
        var ok: Boolean = false
        val request = mercrediService.updateTuteur(tuteur)
        request.enqueue(object : Callback<Tuteur> {
            override fun onFailure(call: Call<Tuteur>, t: Throwable) {

            }

            override fun onResponse(call: Call<Tuteur>, response: Response<Tuteur>) {
                response.let {
                    val user = it.body()
                    if (user != null) {
                        Timber.i("zeze reponse tuteur ok ${response.body()}")
                        ok = true

                    } else {
                        Timber.i("zeze reponse tuteur ko ${response.body()}")

                    }
                }
            }
        })

        if(ok)
            update(tuteur)
    }

}