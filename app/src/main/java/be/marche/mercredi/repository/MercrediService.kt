package be.marche.mercredi.repository

import kotlinx.coroutines.Deferred
import be.marche.mercredi.entity.Enfant
import be.marche.mercredi.entity.Tuteur
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "212a06f2e52fbd14ecb684fae35a1d9d"

interface MercrediService {
    @GET("all")
    fun getAllData(

    ): Deferred<MercrediData>

    fun getOneEnfant(
        @Query(value = "q") enfantId: Int,
        @Query("APPID") apikey: String = API_KEY
    ): Call<Enfant>

    fun getOneTuteur(
        @Query(value = "q") tuteurId: Int,
        @Query("APPID") apikey: String = API_KEY
    ): Call<Tuteur>
}