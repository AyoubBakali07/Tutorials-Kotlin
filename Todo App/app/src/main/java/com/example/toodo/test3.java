//// Projet : Toodo - Application CRUD Livraisons (JsonPlaceholder)
//// 📅 Année 2024-2025 | Spécialité : Développement Mobile Android – Jetpack Compose
//
//// ==== MODEL : model/Livraison.kt ====
//package com.example.toodo.model
//
///** Statut possible d'une Livraison */
//enum class Status(val label: String) {
//    EN_COURS("En cours"),
//    TERMINE("Terminé"),
//    ANNULE("Annulé")
//}
//
///** Priorité possible d'une Livraison */
//enum class Priority(val label: String) {
//    HAUTE("Haute"),
//    MOYENNE("Moyenne"),
//    BASSE("Basse")
//}
//
///** Entité Domaine */
//data class Livraison(
//        val id: Int = 0,
//        val nom: String,
//        val statut: Status,
//        val priorite: Priority
//)
//
//// ==== NETWORK DTO : network/TodoDTO.kt ====
//package com.example.toodo.network
//
//        import com.google.gson.annotations.SerializedName
//
///** Représente un TODO de JsonPlaceholder */
//        data class TodoDTO(
//        @SerializedName("userId") val userId: Int,
//        @SerializedName("id") val id: Int,
//        @SerializedName("title") val title: String,
//        @SerializedName("completed") val completed: Boolean
//)
//
//// ==== NETWORK SERVICE : network/ApiService.kt ====
//package com.example.toodo.network
//
//        import retrofit2.http.Body
//        import retrofit2.http.DELETE
//        import retrofit2.http.GET
//        import retrofit2.http.POST
//        import retrofit2.http.PUT
//        import retrofit2.http.Path
//
///** Endpoints JSONPlaceholder /todos */
//interface ApiService {
//    @GET("todos")
//    suspend fun getTodos(): List<TodoDTO>
//
//    @POST("todos")
//    suspend fun addTodo(@Body todo: TodoDTO): TodoDTO
//
//    @PUT("todos/{id}")
//    suspend fun updateTodo(
//            @Path("id") id: Int,
//            @Body todo: TodoDTO
//    ): TodoDTO
//
//    @DELETE("todos/{id}")
//    suspend fun deleteTodo(@Path("id") id: Int)
//}
//
//// ==== NETWORK CLIENT : network/RetrofitClient.kt ====
//package com.example.toodo.network
//
//        import retrofit2.Retrofit
//        import retrofit2.converter.gson.GsonConverterFactory
//
//        object RetrofitClient {
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
//// ==== VIEWMODEL : viewmodel/LivraisonViewModel.kt ====
//        package com.example.toodo.viewmodel
//
//        import androidx.lifecycle.ViewModel
//        import androidx.lifecycle.viewModelScope
//        import com.example.toodo.model.Livraison
//        import com.example.toodo.model.Priority
//        import com.example.toodo.model.Status
//        import com.example.toodo.network.RetrofitClient
//        import com.example.toodo.network.TodoDTO
//        import kotlinx.coroutines.flow.MutableStateFlow
//        import kotlinx.coroutines.flow.StateFlow
//        import kotlinx.coroutines.flow.combine
//        import kotlinx.coroutines.flow.SharingStarted
//        import kotlinx.coroutines.flow.stateIn
//        import kotlinx.coroutines.launch
//
///** ViewModel gérant l'état des Livraisons */
//class LivraisonViewModel : ViewModel() {
//private val _livraisons = MutableStateFlow<List<Livraison>>(emptyList())
//private val _filterPriority = MutableStateFlow<Priority?>(null)
//private val _error = MutableStateFlow<String?>(null)
//
//        // Liste filtrée dynamiquement selon priorité
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
//        /** Charge et mappe les todos JSONPlaceholder en Livraisons */
//        fun fetchLivraisons() {
//        viewModelScope.launch {
//        try {
//        val todos = RetrofitClient.api.getTodos()
//        _livraisons.value = todos.map { dto ->
//        Livraison(
//        id = dto.id,
//        nom = dto.title,
//        statut = if (dto.completed) Status.TERMINE else Status.EN_COURS,
//        priorite = Priority.MOYENNE
//        )
//        }
//        _error.value = null
//        } catch (e: Exception) {
//        _error.value = e.localizedMessage
//        }
//        }
//        }
//
//        /** Ajoute une livraison (title + completed=false) */
//        fun addLivraison(nom: String, statut: Status, priorite: Priority) {
//        if (nom.isBlank()) {
//        _error.value = "Le nom ne peut pas être vide"
//        return
//        }
//        viewModelScope.launch {
//        try {
//        val dto = TodoDTO(userId = 1, id = 0, title = nom, completed = statut == Status.TERMINE)
//        val result = RetrofitClient.api.addTodo(dto)
//        _livraisons.value = _livraisons.value + Livraison(
//        id = result.id,
//        nom = result.title,
//        statut = if (result.completed) Status.TERMINE else Status.EN_COURS,
//        priorite = priorite
//        )
//        _error.value = null
//        } catch (e: Exception) {
//        _error.value = e.localizedMessage
//        }
//        }
//        }
//
//        /** Met à jour une livraison existante */
//        fun updateLivraison(l: Livraison) {
//        viewModelScope.launch {
//        try {
//        val dto = TodoDTO(
//        userId = 1,
//        id = l.id,
//        title = l.nom,
//        completed = l.statut == Status.TERMINE
//        )
//        val updated = RetrofitClient.api.updateTodo(l.id, dto)
//        _livraisons.value = _livraisons.value.map {
//        if (it.id == updated.id) it.copy(
//        nom = updated.title,
//        statut = if (updated.completed) Status.TERMINE else Status.EN_COURS
//        ) else it
//        }
//        _error.value = null
//        } catch (e: Exception) {
//        _error.value = e.localizedMessage
//        }
//        }
//        }
//
//        /** Supprime une livraison */
//        fun deleteLivraison(id: Int) {
//        viewModelScope.launch {
//        try {
//        RetrofitClient.api.deleteTodo(id)
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
//// ==== UI : ui/MainActivity.kt & Composables ====
//        package com.example.toodo.ui
//
//        import android.os.Bundle
//        import androidx.activity.ComponentActivity
//        import androidx.activity.compose.setContent
//        import androidx.compose.foundation.layout.*
//        import androidx.compose.foundation.lazy.LazyColumn
//        import androidx.compose.foundation.lazy.items
//        import androidx.compose.material.*
//        import androidx.compose.material.icons.Icons
//        import androidx.compose.material.icons.filled.Add
//        import androidx.compose.material.icons.filled.ArrowDropDown
//        import androidx.compose.material.icons.filled.Delete
//        import androidx.compose.material.icons.filled.Edit
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
//        val vm = LivraisonViewModel()
//        setContent {
//        MaterialTheme {
//        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
//        AppContent(vm)
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
//        LazyColumn { items(livs, key = { it.id }) { LivraisonRow(it, onEdit = { editItem = it; showDialog = true }, onDelete = { vm.deleteLivraison(it.id) }) } }
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
//@Composable
//fun LivraisonRow(
//        livraison: Livraison,
//        onEdit: (Livraison) -> Unit,
//        onDelete: (Livraison) -> Unit
//        ) {
//        Card(modifier = Modifier.fillMaxWidth().padding(8.dp), elevation = 4.dp) {
//        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
//        Column(modifier = Modifier.weight(1f)) {
//        Text(livraison.nom, style = MaterialTheme.typography.h6)
//        Text(livraison.statut.label)
//        Text(livraison.priorite.label)
//        }
//        IconButton(onClick = { onEdit(livraison) }) { Icon(Icons.Default.Edit, contentDescription = "Modifier") }
//        IconButton(onClick = { onDelete(livraison) }) { Icon(Icons.Default.Delete, contentDescription = "Supprimer") }
//        }
//        }
//        }
//
//@Composable
//fun FilterDropdown(selected: Priority?, onSelect: (Priority?) -> Unit) {
//        var expanded by remember { mutableStateOf(false) }
//        Box {
//        TextButton(onClick = { expanded = true }) {
//        Text(selected?.label ?: "Toutes")
//        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
//        }
//        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
//        DropdownMenuItem(onClick = { onSelect(null); expanded = false }) { Text("Toutes") }
//        Priority.values().forEach { prio -> DropdownMenuItem(onClick = { onSelect(prio); expanded = false }) { Text(prio.label) } }
//        }
//        }
//        }
//
//@Composable
//fun AddEditDialog(
//        initial: Livraison?,
//        onCancel: () -> Unit,
//        onConfirm: (String, Status, Priority) -> Unit
//        ) {
//        var nom by remember { mutableStateOf(initial?.nom ?: "") }
//        var statut by remember { mutableStateOf(initial?.statut ?: Status.EN_COURS) }
//        var priorite by remember { mutableStateOf(initial?.priorite ?: Priority.MOYENNE) }
//        var error by remember { mutableStateOf<String?>(null) }
//
//        AlertDialog(
//        onDismissRequest = onCancel,
//        title = { Text(if (initial == null) "Ajouter Livraison" else "Modifier Livraison") },
//        text = {
//        Column {
//        OutlinedTextField(value = nom, onValueChange = { nom = it }, label = { Text("Nom") }, isError = nom.isBlank())
//        Spacer(Modifier.height(8.dp))
//        Status.values().forEach { st -> Row(Modifier.fillMaxWidth()) { RadioButton(selected = statut == st, onClick = { statut = st }); Text(st.label, Modifier.padding(start = 4.dp)) } }
//        Spacer(Modifier.height(8.dp))
//        Priority.values().forEach { pr -> Row(Modifier.fillMaxWidth()) { RadioButton(selected = priorite == pr, onClick = { priorite = pr }); Text(pr.label, Modifier.padding(start = 4.dp)) } }
//        error?.let { Text(it, color = MaterialTheme.colors.error) }
//        }
//        },
//        confirmButton = { TextButton(onClick = { if (nom.isBlank()) error = "Nom requis" else onConfirm(nom, statut, priorite) }) { Text("Valider") } },
//        dismissButton = { TextButton(onClick = onCancel) { Text("Annuler") } }
//        )
//        }
