package com.example.livraison.Repository

import com.example.livraison.model.Livraison
import com.example.livraison.network.RetrofitClient
class LivraisonRepository {
    private val api = RetrofitClient.apiService
    suspend fun fetchAll() = api.getAll()
    suspend fun add(l: Livraison) = api.create(l)
    suspend fun update(l: Livraison) = api.update(l.id, l)
    suspend fun delete(id: String) = api.delete(id)
}