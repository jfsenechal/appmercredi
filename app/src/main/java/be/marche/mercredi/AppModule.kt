package be.marche.mercredi

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import be.marche.mercredi.database.AppDatabase
import be.marche.mercredi.enfant.EnfantRepository
import be.marche.mercredi.enfant.EnfantViewModel
import be.marche.mercredi.repository.MercrediRepository
import be.marche.mercredi.repository.MercrediService
import be.marche.mercredi.sync.SyncViewModel
import be.marche.mercredi.tuteur.TuteurRepository
import be.marche.mercredi.tuteur.TuteurViewModel
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

    single { EnfantRepository(get()) }
    single { TuteurRepository(get()) }
    single { MercrediRepository(get()) }

    single { createOkHttpClient() }
    single { createWebService<MercrediService>(get(), "https://cst.marche.be/") }

    viewModel { MercrediViewModel(get(), get(), get(), get()) }
    viewModel { SyncViewModel(get(), get(), get(), get()) }
    viewModel { EnfantViewModel(get()) }
    viewModel { TuteurViewModel(get()) }
}

fun createOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
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