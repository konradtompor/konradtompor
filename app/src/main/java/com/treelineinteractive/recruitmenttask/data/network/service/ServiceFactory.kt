package com.treelineinteractive.recruitmenttask.data.network.service

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.treelineinteractive.recruitmenttask.data.network.ApiConst
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceFactory {
    inline fun <reified T> createService(): T = retrofit(baseUrl = ApiConst.BASE_URL).create(T::class.java)

    fun retrofit(baseUrl: String): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(okHttp())
        .build()

    private fun okHttp(): OkHttpClient {
        val builder = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })

        return builder.build()
    }
}
