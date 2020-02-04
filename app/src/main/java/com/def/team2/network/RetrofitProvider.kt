package com.def.team2.network

import android.content.Context
import com.def.team2.util.KEY_TOKEN
import com.def.team2.util.sharedPreferences
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitProvider constructor(private val context: Context) {

    companion object {
        const val BASE_URL = "http://3.133.57.166:8080"
    }

    val idolKingdomApi: Api by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(provideOkHttpClient(provideLoggingInterceptor()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }

    private fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        val b = OkHttpClient.Builder()
        b.addInterceptor { chain ->
            val accessToken = context.sharedPreferences().getString(KEY_TOKEN, "").orEmpty() // TODO KEy
            return@addInterceptor chain.proceed(chain.request().newBuilder().let {
                it.header("Authorization", "Bearer $accessToken")
                it.build()
            })
        }
        b.addInterceptor(interceptor)
        return b.build()
    }

    private fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return interceptor
    }
}