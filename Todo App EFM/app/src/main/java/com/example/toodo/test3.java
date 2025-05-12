package com.example.toodo;

public class test3 {
    // MainActivity.kt
//package com.example.toodo
//
//import android.app.NotificationChannel
//import android.app.NotificationManager
//import android.content.Context
//import android.os.Build
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
//import com.example.toodo.ui.theme.ToodoTheme
//
//    class MainActivity : ComponentActivity() {
//        private val CHANNEL_ID = "todo_notifications"
//
//        override fun onCreate(savedInstanceState: Bundle?) {
//            super.onCreate(savedInstanceState)
//            // Create notification channel for Android O+
//            createNotificationChannel()
//
//            setContent {
//                ToodoTheme {
//                    Surface(color = MaterialTheme.colorScheme.background) {
//                        // Pass context lambdas for showing notifications
//                        ProductScreen(
//                                onItemAdded = { name ->
//                                        NotificationHelper.showNotification(
//                                                this,
//                                                CHANNEL_ID,
//                                                "Produit ajouté",
//                                                "Le produit '$name' a été ajouté."
//                                        )
//                                },
//                                onItemDeleted = { name ->
//                                        NotificationHelper.showNotification(
//                                                this,
//                                                CHANNEL_ID,
//                                                "Produit supprimé",
//                                                "Le produit '$name' a été supprimé."
//                                        )
//                                }
//                        )
//                    }
//                }
//            }
//        }
//
//        private fun createNotificationChannel() {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                val name = "Notifications Toodo"
//                val descriptionText = "Notifications pour ajout/suppression de produits"
//                val importance = NotificationManager.IMPORTANCE_DEFAULT
//                val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
//                    description = descriptionText
//                }
//                val manager: NotificationManager =
//                        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//                manager.createNotificationChannel(channel)
//            }
//        }
//    }
//
//// NotificationHelper.kt
//package com.example.toodo
//
//import android.app.PendingIntent
//import android.content.Context
//import androidx.core.app.NotificationCompat
//import androidx.core.app.NotificationManagerCompat
//
//    object NotificationHelper {
//        private var notificationId = 0
//
//        fun showNotification(
//                context: Context,
//                channelId: String,
//                title: String,
//                content: String
//    ) {
//            val builder = NotificationCompat.Builder(context, channelId)
//                    .setSmallIcon(R.drawable.ic_notification)
//                    .setContentTitle(title)
//                    .setContentText(content)
//                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//
//            with(NotificationManagerCompat.from(context)) {
//                notify(notificationId++, builder.build())
//            }
//        }
//    }
//
//// Priority.kt
//package com.example.toodo
//
//import androidx.compose.ui.graphics.Color
//
///**
// * Enum representing priority levels with labels and colors
// */
//    enum class Priority(val label: String, val color: Color) {
//        Haute("Haute", Color.Red),
//        Moyenne("Moyenne", Color.Yellow),
//        Basse("Basse", Color.Green)
//    }
//
//// Product.kt
//package com.example.toodo
//
///**
// * Data model for Product entity
// */
//    data class Product(
//            val id: Int,
//            val name: String,
//            val status: Boolean,
//            val priority: String = Priority.Moyenne.label
//    )
//
//// ApiService.kt
//package com.example.toodo
//
//import retrofit2.http.*
//
//    /**
//     * Retrofit interface for CRUD operations on Product
//     */
//    interface ApiService {
//        @GET("products")
//        suspend fun getProducts(): List<Product>
//
//        @POST("products")
//        suspend fun addProduct(@Body product: Product): Product
//
//        @PUT("products/{id}")
//        suspend fun updateProduct(@Path("id") id: Int, @Body product: Product): Product
//
//        @DELETE("products/{id}")
//        suspend fun deleteProduct(@Path("id") id: Int)
//    }
//
//// RetrofitClient.kt
//package com.example.toodo
//
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//
//    /**
//     * Singleton for Retrofit setup
//     */
//    object RetrofitClient {
//        private const val BASE_URL = "https://your-laravel-api.com/api/"
//        val api: ApiService by lazy {
//            Retrofit.Builder()
//                    .baseUrl(BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build()
//                    .create(ApiService::class.java)
//        }
//    }
//
//// ProductViewModel.kt
//package com.example.toodo
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//
//    /**
//     * ViewModel managing Product list, filter and sort state
//     */
//    class ProductViewModel : ViewModel() {
//        private val _products = MutableStateFlow<List<Product>>(emptyList())
//        val products: StateFlow<List<Product>> = _products
//
//        private val _filter = MutableStateFlow("Toutes")
//        val filter: StateFlow<String> = _filter
//
//        private val _ascending = MutableStateFlow(true)
//        val ascending: StateFlow<Boolean> = _ascending
//
//        init { fetchProducts() }
//
//        fun fetchProducts() {
//            viewModelScope.launch {
//                try {
//                    _products.value = RetrofitClient.api.getProducts()
//                } catch (e: Exception) {
//                    _products.value = emptyList()
//                }
//            }
//        }
//
//        fun addProduct(product: Product) {
//            viewModelScope.launch {
//                try {
//                    val created = RetrofitClient.api.addProduct(product)
//                    _products.value = _products.value + created
//                } catch (_: Exception) {}
//            }
//        }
//
//        fun updateProduct(product: Product) {
//            viewModelScope.launch {
//                try {
//                    val updated = RetrofitClient.api.updateProduct(product.id, product)
//                    _products.value = _products.value.map { if (it.id == product.id) updated else it }
//                } catch (_: Exception) {}
//            }
//        }
//
//        fun deleteProduct(id: Int) {
//            viewModelScope.launch {
//                try {
//                    RetrofitClient.api.deleteProduct(id)
//                    _products.value = _products.value.filter { it.id != id }
//                } catch (_: Exception) {}
//            }
//        }
//
//        fun toggleSort() { _ascending.value = ! _ascending.value }
//
//        fun setFilter(priority: String) { _filter.value = priority }
//    }
//
//// UI: ProductScreen.kt
//package com.example.toodo
//
//import androidx.compose.animation.animateContentSize
//import androidx.compose.foundation.layout.*
//            import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material3.*
//            import androidx.compose.runtime.*
//            import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.viewmodel.compose.viewModel
//
///**
// * Main screen composable with filter, list, and CRUD controls
// */
//    @Composable
//    fun ProductScreen(
//            vm: ProductViewModel = viewModel(),
//    onItemAdded: (String) -> Unit,
//    onItemDeleted: (String) -> Unit
//) {
//        val products by vm.products.collectAsState()
//        val filter by vm.filter.collectAsState()
//        val ascending by vm.ascending.collectAsState()
//
//        var newName by remember { mutableStateOf("") }
//        var newPriority by remember { mutableStateOf(Priority.Moyenne) }
//        var nameError by remember { mutableStateOf(false) }
//
//        // Filter and sort
//        val filtered = products.filter {
//            filter == "Toutes" || it.priority == filter
//        }
//        val sorted = if (ascending) filtered.sortedBy { it.name } else filtered.sortedByDescending { it.name }
//
//        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
//            // Filter dropdown
//            PriorityDropdown(
//                    selectedLabel = filter,
//                    onSelect = { vm.setFilter(it) },
//                    allOption = "Toutes"
//            )
//
//            Spacer(Modifier.height(8.dp))
//
//            // Add new product row
//            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
//                PriorityDropdown(selected = newPriority, onSelect = { newPriority = it })
//                Spacer(Modifier.width(8.dp))
//                Column(modifier = Modifier.weight(1f)) {
//                    TextField(
//                            value = newName,
//                            onValueChange = {
//                                    newName = it
//                    if (it.isNotBlank()) nameError = false
//                    },
//                    label = { Text("Nom du produit") },
//                            isError = nameError,
//                            modifier = Modifier.fillMaxWidth()
//                )
//                    if (nameError) Text("Le nom est requis", color = Color.Red)
//                }
//                Spacer(Modifier.width(8.dp))
//                Button(onClick = {
//                if (newName.isBlank()) {
//                    nameError = true
//                } else {
//                    vm.addProduct(Product(0, newName, false, newPriority.label))
//                    onItemAdded(newName)
//                    newName = ""; newPriority = Priority.Moyenne
//                }
//            }) { Text("Ajouter") }
//            }
//
//            Spacer(Modifier.height(16.dp))
//
//            // Product list with animations
//            LazyColumn(modifier = Modifier.fillMaxSize()) {
//                items(
//                        items = sorted,
//                        key = { it.id }
//                ) { prod ->
//                        // Animated item placement
//                        ProductItem(
//                                product = prod,
//                                onToggleStatus = { vm.updateProduct(prod.copy(status = !prod.status)) },
//                                onDelete = {
//                                        vm.deleteProduct(prod.id)
//                                        onItemDeleted(prod.name)
//                                },
//                                modifier = Modifier
//                                        .fillMaxWidth()
//                                        .padding(vertical = 4.dp)
//                                        .animateContentSize()
//                        )
//                }
//            }
//        }
//    }
//
//    /**
//     * DropdownMenu for filtering priorities including 'Toutes'
//     */
//    @Composable
//    fun PriorityDropdown(
//            selectedLabel: String,
//            onSelect: (String) -> Unit,
//    allOption: String
//) {
//        var expanded by remember { mutableStateOf(false) }
//        Box {
//            Button(onClick = { expanded = true }) {
//                Text(selectedLabel)
//            }
//            DropdownMenu( expanded = expanded, onDismissRequest = { expanded = false } ) {
//                DropdownMenuItem(text = { Text(allOption) }, onClick = {
//                        onSelect(allOption); expanded = false
//            })
//                Priority.values().forEach { pr ->
//                        DropdownMenuItem(text = { Text(pr.label) }, onClick = {
//                                onSelect(pr.label); expanded = false
//                })
//                }
//            }
//        }
//    }
//
//    /**
//     * Individual product card with animated removal and status toggle
//     */
//    @Composable
//    fun ProductItem(
//            product: Product,
//            onToggleStatus: () -> Unit,
//    onDelete: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//        Card(modifier = modifier, elevation = CardDefaults.cardElevation()) {
//            Column(modifier = Modifier.padding(16.dp)) {
//                Row(
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        modifier = Modifier.fillMaxWidth()
//                ) {
//                    Text(product.name, style = MaterialTheme.typography.titleMedium)
//                    Text(
//                            product.priority,
//                            color = when (product.priority) {
//                        Priority.Haute.label -> Priority.Haute.color
//                        Priority.Moyenne.label -> Priority.Moyenne.color
//                        else -> Priority.Basse.color
//                    }
//                )
//                }
//                Spacer(Modifier.height(4.dp))
//                Text(if (product.status) "Vendu" else "En stock")
//                Spacer(Modifier.height(8.dp))
//                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//                    Button(onClick = onToggleStatus) { Text("Basculer") }
//                    Button(onClick = onDelete) { Text("Supprimer") }
//                }
//            }
//        }
//    }

}
