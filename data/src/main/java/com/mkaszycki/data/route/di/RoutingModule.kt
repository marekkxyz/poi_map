package com.mkaszycki.data.route.di

import com.mkaszycki.data.BuildConfig
import com.mkaszycki.data.route.RoutingService
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RoutingModule {
    @Provides
    @Singleton
    fun providesRoutingService(gson: Gson): RoutingService {
        return Retrofit.Builder()
            .baseUrl("https://router.hereapi.com/v8/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
                setLevel(
                    if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor.Level.BODY
                    } else {
                        HttpLoggingInterceptor.Level.NONE
                    }
                )
            }).build())
            .build()
            .create(RoutingService::class.java)
    }
}