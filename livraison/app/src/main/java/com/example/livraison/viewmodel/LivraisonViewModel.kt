package com.example.livraison.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.livraison.model.Livraison
import com.example.livraison.Repository.LivraisonRepository
import com.example.livraison.util.NotificationHelper
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LivraisonViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = LivraisonRepository()
    private val _list = MutableStateFlow<List<Livraison>>(emptyList())
    val list: StateFlow<List<Livraison>> = _list.asStateFlow()

    // For filter
    private val _filter = MutableStateFlow<String>("Tous")
    val filter: StateFlow<String> = _filter.asStateFlow()

    // Combined to apply dynamic filter
    val filteredList = combine(_list, _filter) { items, pri ->
        if (pri == "Tous") items else items.filter { it.priorite == pri }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        refresh()
    }

    fun setFilter(prio: String) {
        _filter.value = prio
    }

    fun refresh() = viewModelScope.launch {
        _list.value = repo.fetchAll()
    }

    fun add(l: Livraison, onSuccess: () -> Unit) = viewModelScope.launch {
        repo.add(l)
        NotificationHelper(getApplication()).show("Livraison ajoutée")
        refresh()
        onSuccess()
    }

    fun update(l: Livraison, onSuccess: () -> Unit) = viewModelScope.launch {
        repo.update(l)
        refresh()
        onSuccess()
    }

    fun delete(id: String) = viewModelScope.launch {
        repo.delete(id)
        NotificationHelper(getApplication()).show("Livraison supprimée")
        refresh()
    }
}