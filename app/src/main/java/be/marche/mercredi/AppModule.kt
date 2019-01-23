package be.marche.mercredi

import be.marche.mercredi.database.AppDatabase
import be.marche.mercredi.enfant.EnfantRepository
import be.marche.mercredi.enfant.EnfantViewModel
import be.marche.mercredi.presence.PresenceRepository
import be.marche.mercredi.presence.PresenceViewModel
import be.marche.mercredi.repository.MercrediRepository
import be.marche.mercredi.repository.MercrediService
import be.marche.mercredi.repository.UserRepository
import be.marche.mercredi.sync.RequestInterceptor
import be.marche.mercredi.sync.SyncViewModel
import be.marche.mercredi.tuteur.TuteurRepository
import be.marche.mercredi.tuteur.TuteurViewModel
import be.marche.mercredi.user.LoginViewModel
import be.marche.mercredi.user.UserViewModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {

    single { AppDatabase.buildDatabase(androidApplication()) }

    single { get<AppDatabase>().enfantDao() }
    single { get<AppDatabase>().tuteurDao() }
    single { get<AppDatabase>().mercrediDao() }
    single { get<AppDatabase>().presenceDao() }
    single { get<AppDatabase>().userDao() }

    single { EnfantRepository(get()) }
    single { TuteurRepository(get()) }
    single { MercrediRepository(get()) }
    single { PresenceRepository(get()) }
    single { UserRepository(get()) }

    viewModel { MercrediViewModel(get(), get()) }
    viewModel { SyncViewModel(get(), get(), get(), get(), get()) }
    viewModel { EnfantViewModel(get(), get()) }
    viewModel { TuteurViewModel(get(), get()) }
    viewModel { PresenceViewModel(get(), get(), get()) }
    viewModel { LoginViewModel(get(), get()) }
    viewModel { UserViewModel(get()) }

    single { RequestInterceptor(get()) }
    single { createOkHttpClient<OkHttpClient>(get()) }
    single { createWebService<MercrediService>(get(), "http://172.17.30.218/") }//http://192.168.0.8/
}

inline fun <reified T> createOkHttpClient(t: RequestInterceptor): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        })
        .addInterceptor(t)
        .build()
}

inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(T::class.java)
}