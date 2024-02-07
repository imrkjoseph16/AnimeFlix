package com.imrkjoseph.animenation.app.core

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.imrkjoseph.animenation.app.util.Default.Companion.ANIME_BASE_URL
import com.imrkjoseph.animenation.app.util.Default.Companion.MOVIES_BASE_URL
import com.imrkjoseph.animenation.app.util.Default.Companion.SERIES_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth()  = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseUser() = FirebaseAuth.getInstance().currentUser

    @Provides
    @Singleton
    fun provideFireStore() = Firebase.firestore

    @Provides
    @Singleton
    fun provideFirebaseStorage() = FirebaseStorage.getInstance()

    @Provides
    @Singleton
    @Named("animeClient")
    internal fun provideAnimeRetrofit(
        @Named("provideOkHttpClient")
        okHttpClient: OkHttpClient
    ) = Retrofit.Builder()
        .baseUrl(ANIME_BASE_URL)
        .addConverterFactory(JacksonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    @Named("seriesClient")
    internal fun provideSeriesRetrofit(
        @Named("provideOkHttpClient")
        okHttpClient: OkHttpClient
    ) = Retrofit.Builder()
        .baseUrl(SERIES_BASE_URL)
        .addConverterFactory(JacksonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    @Named("moviesClient")
    internal fun provideMoviesRetrofit(
        @Named("provideOkHttpClient")
        okHttpClient: OkHttpClient
    ) = Retrofit.Builder()
        .baseUrl(MOVIES_BASE_URL)
        .addConverterFactory(JacksonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build()


    @Provides
    @Singleton
    @Named("provideOkHttpClient")
    internal fun provideOkHttpClient() = OkHttpClient.Builder().apply {
        addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        connectTimeout(3, TimeUnit.MINUTES)
        writeTimeout(3, TimeUnit.MINUTES)
        readTimeout(3, TimeUnit.MINUTES)
    }.build()
}