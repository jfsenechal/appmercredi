package be.marche.mercredi.repository

import be.marche.mercredi.entity.*
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.POST


private const val API_KEY = "test_api_key"

interface MercrediService {
    @GET("apil/all")
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

    @FormUrlEncoded
    @POST("logapi/")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<User>

    @POST("tuteur/update")
    fun updateTuteur(
        @Body tuteur: Tuteur
    ): Call<Tuteur>

    @POST("enfant/update")
    fun updateEnfant(
        @Query(value = "id") enfantId: Int,
        @Body enfant: Enfant
    ): Call<Enfant>

    @POST("presence/new")
    fun newPresence(
        @Query(value = "id") enfantId: Int,
        @Body presence: Presence
    ): Call<Presence>

}