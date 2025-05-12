//// Projet : Toodo - Application CRUD Livraisons (Test API JsonPlaceholder)
//// 📅 Année 2024-2025 | Spécialité : Développement Mobile Android – Jetpack Compose
//
//// ==== Modèle Métier : model/Livraison.kt ====
//package com.example.toodo.model
//
///**
// * Statut possible d'une Livraison
// */
//enum class Status(val label: String) {
//    EN_COURS("En cours"),
//    TERMINE("Terminé"),
//    ANNULE("Annulé")
//}
//
///**
// * Priorité possible d'une Livraison
// */
//enum class Priority(val label: String) {
//    HAUTE("Haute"),
//    MOYENNE("Moyenne"),
//    BASSE("Basse")
//}
//
///**
// * Entité Livraison
// */
//data class Livraison(
//        val id: Int = 0,
//        val nom: String,
//        val statut: Status,
//        val priorite: Priority
//)
//
//Start from here
//// ==== Réseau : network/ApiService.kt ====
//package com.example.toodo.network
//
//        import com.example.toodo.model.Livraison
//        import retrofit2.http.Body
//        import retrofit2.http.DELETE
//        import retrofit2.http.GET
//        import retrofit2.http.POST
//        import retrofit2.http.PUT
//        import retrofit2.http.Path
//
//interface ApiService {
//    // Utilisation de JsonPlaceholder /todos pour tester CRUD
//    @GET("todos")
//    suspend fun getLivraisons(): List<Livraison>
//
//    @POST("todos")
//    suspend fun addLivraison(@Body livraison: Livraison): Livraison
//
//    @PUT("todos/{id}")
//    suspend fun updateLivraison(
//            @Path("id") id: Int,
//            @Body livraison: Livraison
//    ): Livraison
//
//    @DELETE("todos/{id}")
//    suspend fun deleteLivraison(@Path("id") id: Int)
//}
//
//// ==== Réseau : network/RetrofitClient.kt ====
//package com.example.toodo.network
//
//        import retrofit2.Retrofit
//        import retrofit2.converter.gson.GsonConverterFactory
//
//        object RetrofitClient {
//// Pointé vers JsonPlaceholder
//private const val BASE_URL = "https://jsonplaceholder.typicode.com/"
//        val api: ApiService by lazy {
//        Retrofit.Builder()
//        .baseUrl(BASE_URL)
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//        .create(ApiService::class.java)
//        }
//        }
//
//// ==== ViewModel : viewmodel/LivraisonViewModel.kt ====
//        package com.example.toodo.viewmodel
//
//        import androidx.lifecycle.ViewModel
//        import androidx.lifecycle.viewModelScope
//        import com.example.toodo.model.Livraison
//        import com.example.toodo.model.Priority
//        import com.example.toodo.model.Status
//        import com.example.toodo.network.RetrofitClient
//        import kotlinx.coroutines.flow.MutableStateFlow
//        import kotlinx.coroutines.flow.StateFlow
//        import kotlinx.coroutines.flow.combine
//        import kotlinx.coroutines.flow.stateIn
//        import kotlinx.coroutines.flow.SharingStarted
//        import kotlinx.coroutines.launch
//
///**
// * ViewModel gérant l'état des Livraisons
// */
//class LivraisonViewModel : ViewModel() {
//private val _livraisons = MutableStateFlow<List<Livraison>>(emptyList())
//private val _filterPriority = MutableStateFlow<Priority?>(null)
//private val _error = MutableStateFlow<String?>(null)
//
//        // Liste filtrée dynamiquement
//        val livraisonsFiltree: StateFlow<List<Livraison>> =
//        combine(_livraisons, _filterPriority) { list, filter ->
//        filter?.let { list.filter { it.priorite == filter } } ?: list
//        }
//        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
//
//        val filterPriority: StateFlow<Priority?> = _filterPriority
//        val error: StateFlow<String?> = _error
//
//        init { fetchLivraisons() }
//
//        fun fetchLivraisons() {
//        viewModelScope.launch {
//        try {
//        _livraisons.value = RetrofitClient.api.getLivraisons()
//        _error.value = null
//        } catch (e: Exception) {
//        _error.value = e.localizedMessage
//        }
//        }
//        }
//
//        fun addLivraison(nom: String, statut: Status, priorite: Priority) {
//        if (nom.isBlank()) {
//        _error.value = "Le nom ne peut pas être vide"
//        return
//        }
//        viewModelScope.launch {
//        try {
//        val ajout = RetrofitClient.api.addLivraison(
//        Livraison(nom = nom, statut = statut, priorite = priorite)
//        )
//        _livraisons.value = _livraisons.value + ajout
//        _error.value = null
//        } catch (e: Exception) {
//        _error.value = e.localizedMessage
//        }
//        }
//        }
//
//        fun updateLivraison(l: Livraison) {
//        viewModelScope.launch {
//        try {
//        val maj = RetrofitClient.api.updateLivraison(l.id, l)
//        _livraisons.value = _livraisons.value.map { if (it.id == l.id) maj else it }
//        _error.value = null
//        } catch (e: Exception) {
//        _error.value = e.localizedMessage
//        }
//        }
//        }
//
//        fun deleteLivraison(id: Int) {
//        viewModelScope.launch {
//        try {
//        RetrofitClient.api.deleteLivraison(id)
//        _livraisons.value = _livraisons.value.filter { it.id != id }
//        _error.value = null
//        } catch (e: Exception) {
//        _error.value = e.localizedMessage
//        }
//        }
//        }
//
//        fun setFilter(priorite: Priority?) {
//        _filterPriority.value = priorite
//        }
//        }
//
//// ==== UI : ui/MainActivity.kt ====
//        package com.example.toodo.ui
//
//        import android.os.Bundle
//        import androidx.activity.ComponentActivity
//        import androidx.activity.compose.setContent
//        import androidx.compose.foundation.layout.*
//        import androidx.compose.foundation.lazy.LazyColumn
//        import androidx.compose.foundation.lazy.items
//        import androidx.compose.material.*
//        import androidx.compose.runtime.*
//        import androidx.compose.ui.Alignment
//        import androidx.compose.ui.Modifier
//        import androidx.compose.ui.unit.dp
//        import com.example.toodo.model.Livraison
//        import com.example.toodo.model.Priority
//        import com.example.toodo.model.Status
//        import com.example.toodo.viewmodel.LivraisonViewModel
//
//class MainActivity : ComponentActivity() {
//        override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        val viewModel = LivraisonViewModel()
//        setContent {
//        MaterialTheme {
//        Surface(color = MaterialTheme.colors.background) {
//        AppContent(viewModel)
//        }
//        }
//        }
//        }
//        }
//
//@Composable
//fun AppContent(vm: LivraisonViewModel) {
//        val livs by vm.livraisonsFiltree.collectAsState()
//        val filter by vm.filterPriority.collectAsState()
//        val error by vm.error.collectAsState()
//        var showDialog by remember { mutableStateOf(false) }
//        var editItem by remember { mutableStateOf<Livraison?>(null) }
//
//        Scaffold(
//        topBar = {
//        TopAppBar(
//        title = { Text("Gestion des Livraisons") },
//        actions = { FilterDropdown(filter) { vm.setFilter(it) } }
//        )
//        },
//        floatingActionButton = {
//        FloatingActionButton(onClick = { editItem = null; showDialog = true }) {
//        Icon(Icons.Default.Add, contentDescription = "Ajouter")
//        }
//        }
//        ) { padding ->
//        Box(modifier = Modifier.padding(padding)) {
//        if (error != null) Text(error!!, color = MaterialTheme.colors.error, modifier = Modifier.align(Alignment.Center))
//        LazyColumn { items(livs, key = { it.id }) { LivraisonRow(it, { editItem = it; showDialog = true }, { vm.deleteLivraison(it.id) }) } }
//        }
//        }
//
//        if (showDialog) {
//        AddEditDialog(
//        initial = editItem,
//        onCancel = { showDialog = false },
//        onConfirm = { nom, stat, prio ->
//        if (editItem == null) vm.addLivraison(nom, stat, prio)
//        else vm.updateLivraison(editItem!!.copy(nom = nom, statut = stat, priorite = prio))
//        showDialog = false
//        }
//        )
//        }
//        }
//
//// ... le reste des composables (LivraisonRow, FilterDropdown, AddEditDialog) reste inchangé
