package be.marche.mercredi.repository

import be.marche.mercredi.entity.*
import kotlinx.coroutines.Deferred
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
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

    @POST("api/update/tuteur")
    fun updateTuteur(
        @Header("X-AUTH-TOKEN") token: String?,
        @Body tuteur: Tuteur
    ): Call<Tuteur>

    @Multipart
    @POST("api/update/enfant/photo/{id}")
    fun uploadImage(
        @Header("X-AUTH-TOKEN") token: String?,
        @Path("id") id: Int,
        @Part("image") image: RequestBody
    ): Call<ResponseBody>

    @POST("api/update/enfant/{id}")
    fun updateEnfant(
        @Header("X-AUTH-TOKEN") token: String?,
        @Path("id") enfantId: Int,
        @Body enfant: Enfant
    ): Call<Enfant>

    @POST("api/presences/new/{id}")
    fun newPresences(
        @Header("X-AUTH-TOKEN") token: String?,
        @Path("id") enfantId: Int,
        @Body presences: List<Presence>
    ): Call<List<Presence>>

}