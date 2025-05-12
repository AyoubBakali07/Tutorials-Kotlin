package com.example.toodo.network
        import retrofit2.Retrofit
        import retrofit2.converter.gson.GsonConverterFactory

        object RetrofitClient {
// Pointé vers JsonPlaceholder
private const val BASE_URL = "https://jsonplaceholder.typicode.com/"
        val api: ApiService by lazy {
        Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)
        }
        }