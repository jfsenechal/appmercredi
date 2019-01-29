package be.marche.mercredi.repository

import be.marche.mercredi.entity.*
import kotlinx.coroutines.Deferred
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import okhttp3.MultipartBody
import retrofit2.http.POST
import retrofit2.http.Multipart



interface MercrediService {
    @GET("api/all")
    fun getAllData(
    ): Deferred<MercrediData>

    @FormUrlEncoded
    @POST("logapi/")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<User>

    @POST("api/update/tuteur")
    fun updateTuteur(
        @Body tuteur: Tuteur
    ): Call<Tuteur>

    @Multipart
    @POST("api/update/enfant/photo/{id}")
    fun uploadImage(
        @Path("id") id: Int,
        @Part("image") image: RequestBody
    ): Call<ResponseBody>

    @Multipart
    @POST("api/update/enfant/photo/{id}")
    fun uploadImage2(
        @Part file: MultipartBody.Part,
        @Part("image") requestBody: RequestBody): Call<ResponseBody>

    @POST("api/update/enfant/{id}")
    fun updateEnfant(
        @Path("id") enfantId: Int,
        @Body enfant: Enfant
    ): Call<Enfant>

    @POST("api/presences/new/{id}")
    fun newPresences(
                @Path("id") enfantId: Int,
        @Body jours: List<Int>
    ): Call<ResponseRetrofit>

}