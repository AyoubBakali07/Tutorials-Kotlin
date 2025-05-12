package com.example.toodo;

public class test2 {
    // MainActivity.kt
//package com.example.toodo
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import com.example.toodo.ui.theme.ToodoTheme
//
//    class MainActivity : ComponentActivity() {
//        override fun onCreate(savedInstanceState: Bundle?) {
//            super.onCreate(savedInstanceState)
//            setContent {
//                ToodoTheme {
//                    ProductScreen()
//                }
//            }
//        }
//    }
//
//// Priority options
//    enum class Priority(val label: String) {
//        Haute("Haute"),
//        Moyenne("Moyenne"),
//        Basse("Basse")
//    }
//
//// Data model for new entity: Product
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
//    class ProductViewModel : ViewModel() {
//        private val _products = MutableStateFlow<List<Product>>(emptyList())
//        val products: StateFlow<List<Product>> = _products
//
//        // For sort toggle
//        private val _ascending = MutableStateFlow(true)
//        val ascending: StateFlow<Boolean> = _ascending
//
//        init { fetchProducts() }
//
//        private fun fetchProducts() {
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
//        fun toggleSort() {
//            _ascending.value = !_ascending.value
//        }
//    }
//
//// UI: ProductScreen.kt
//package com.example.toodo
//
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
//    @Composable
//    fun ProductScreen(vm: ProductViewModel = viewModel()) {
//        val products by vm.products.collectAsState()
//        val ascending by vm.ascending.collectAsState()
//
//        var newName by remember { mutableStateOf("") }
//        var newPriority by remember { mutableStateOf(Priority.Moyenne) }
//        var nameError by remember { mutableStateOf(false) }
//
//        // Sort products by name ascending/descending
//        val sorted = if (ascending) products.sortedBy { it.name } else products.sortedByDescending { it.name }
//
//        Scaffold(
//                topBar = {
//                        TopAppBar(title = { Text("Liste de Produits") },
//                                actions = {
//                                        IconButton(onClick = { vm.toggleSort() }) {
//                                        Text(if (ascending) "A→Z" else "Z→A")
//                    }
//                })
//        }
//    ) { padding ->
//                Column(
//                        modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp),
//                        horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
//                PriorityDropdown(selected = newPriority, onSelect = { newPriority = it })
//                Spacer(modifier = Modifier.width(8.dp))
//                Column(modifier = Modifier.weight(1f)) {
//                    TextField(
//                            value = newName,
//                            onValueChange = {
//                                    newName = it
//                    if (it.isNotBlank()) nameError = false
//                        },
//                    label = { Text("Nom du produit") },
//                            isError = nameError,
//                            modifier = Modifier.fillMaxWidth()
//                    )
//                    if (nameError) Text("Le nom est requis", color = Color.Red)
//                }
//                Spacer(modifier = Modifier.width(8.dp))
//                Button(onClick = {
//                if (newName.isBlank()) {
//                    nameError = true
//                } else {
//                    vm.addProduct(Product(0, newName, false, newPriority.label))
//                    newName = ""
//                    newPriority = Priority.Moyenne
//                }
//                }) { Text("Ajouter") }
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            LazyColumn { items(sorted) { prod ->
//                    ProductItem(
//                            product = prod,
//                            onToggleStatus = { vm.updateProduct(prod.copy(status = !prod.status)) },
//                            onDelete = { vm.deleteProduct(prod.id) }
//                    )
//            }}
//        }
//        }
//    }
//
//    @Composable
//    fun ProductItem(
//            product: Product,
//            onToggleStatus: () -> Unit,
//    onDelete: () -> Unit
//) {
//        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
//                elevation = CardDefaults.cardElevation()) {
//            Column(modifier = Modifier.padding(16.dp)) {
//                Row(
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        modifier = Modifier.fillMaxWidth()
//                ) {
//                    Text(product.name, style = MaterialTheme.typography.titleMedium)
//                    Text(
//                            product.priority,
//                            color = when (product.priority) {
//                        Priority.Haute.label -> Color.Red
//                        Priority.Moyenne.label -> Color.Yellow
//                        else -> Color.Green
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
