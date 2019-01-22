package be.marche.mercredi.repository

import be.marche.mercredi.entity.*
import kotlinx.coroutines.Deferred
import okhttp3.RequestBody
import retrofit2.Call
import okhttp3.Response
import retrofit2.http.*

interface MercrediService {
    @GET("api/all")
    fun getAllData(
        @Header("X-AUTH-TOKEN") token: String?
    ): Deferred<MercrediData>

    fun getOneEnfant(
        @Query(value = "enfantId") enfantId: Int,
        @Header("X-AUTH-TOKEN") token: String?
    ): Call<Enfant>

    fun getOneTuteur(
        @Header("X-AUTH-TOKEN") token: String?,
        @Query(value = "id") tuteurId: Int
    ): Call<Tuteur>

    @FormUrlEncoded
    @POST("logapi/")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<User>

    @Multipart
    @POST("api/enfant/photo")
    fun uploadImage(@Part("image") image: RequestBody)
    :Call<Response>

    @POST("api/update/tuteur")
    fun updateTuteur(
        @Header("X-AUTH-TOKEN") token: String?,
        @Body tuteur: Tuteur
    ): Call<Tuteur>

    @POST("api/update/enfant")
    fun updateEnfant(
        @Header("X-AUTH-TOKEN") token: String?,
        @Query(value = "id") enfantId: Int,
        @Body enfant: Enfant
    ): Call<Enfant>

    @POST("api/presence/new")
    fun newPresence(
        @Header("X-AUTH-TOKEN") token: String?,
        @Query(value = "id") enfantId: Int,
        @Body presence: Presence
    ): Call<Presence>

}