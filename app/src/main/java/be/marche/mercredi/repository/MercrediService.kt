package be.marche.mercredi.repository

import be.marche.mercredi.entity.Enfant
import be.marche.mercredi.entity.Tuteur
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

private const val API_KEY = "test_api_key"

interface MercrediService {
    @GET("all")
    fun getAllData(
        @Header("X-AUTH-TOKEN") token: String?
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