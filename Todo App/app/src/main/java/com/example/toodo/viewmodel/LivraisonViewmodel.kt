package com.example.toodo.viewmodel
        import androidx.lifecycle.ViewModel
        import androidx.lifecycle.viewModelScope
        import com.example.toodo.model.Livraison
        import com.example.toodo.model.Priority
        import com.example.toodo.model.Status
        import com.example.toodo.network.RetrofitClient
        import kotlinx.coroutines.flow.MutableStateFlow
        import kotlinx.coroutines.flow.StateFlow
        import kotlinx.coroutines.flow.combine
        import kotlinx.coroutines.flow.stateIn
        import kotlinx.coroutines.flow.SharingStarted
        import kotlinx.coroutines.launch


class LivraisonViewModel : ViewModel() {
private val _livraisons = MutableStateFlow<List<Livraison>>(emptyList())
private val _filterPriority = MutableStateFlow<Priority?>(null)
private val _error = MutableStateFlow<String?>(null)

        // Liste filtrée dynamiquement
        val livraisonsFiltree: StateFlow<List<Livraison>> =
        combine(_livraisons, _filterPriority) { list, filter ->
        filter?.let { list.filter { it.priorite == filter } } ?: list
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

        val filterPriority: StateFlow<Priority?> = _filterPriority
        val error: StateFlow<String?> = _error

        init { fetchLivraisons() }

        fun fetchLivraisons() {
        viewModelScope.launch {
        try {
        _livraisons.value = RetrofitClient.api.getLivraisons()
        _error.value = null
        } catch (e: Exception) {
        _error.value = e.localizedMessage
        }
        }
        }

        fun addLivraison(nom: String, statut: Status, priorite: Priority) {
        if (nom.isBlank()) {
        _error.value = "Le nom ne peut pas être vide"
        return
        }
        viewModelScope.launch {
        try {
        val ajout = RetrofitClient.api.addLivraison(
        Livraison(nom = nom, statut = statut, priorite = priorite)
        )
        _livraisons.value = _livraisons.value + ajout
        _error.value = null
        } catch (e: Exception) {
        _error.value = e.localizedMessage
        }
        }
        }

        fun updateLivraison(l: Livraison) {
        viewModelScope.launch {
        try {
        val maj = RetrofitClient.api.updateLivraison(l.id, l)
        _livraisons.value = _livraisons.value.map { if (it.id == l.id) maj else it }
        _error.value = null
        } catch (e: Exception) {
        _error.value = e.localizedMessage
        }
        }
        }

        fun deleteLivraison(id: Int) {
        viewModelScope.launch {
        try {
        RetrofitClient.api.deleteLivraison(id)
        _livraisons.value = _livraisons.value.filter { it.id != id }
        _error.value = null
        } catch (e: Exception) {
        _error.value = e.localizedMessage
        }
        }
        }

        fun setFilter(priorite: Priority?) {
        _filterPriority.value = priorite
        }
        }

