package com.example.toodo

import CounterViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.toodo.ui.theme.ToodoTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.compose.material.*


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTodo()
        }
    }
}
@Composable
fun MyTodo() {
    var titre by remember { mutableStateOf("Chargement...") }

    // Appel Retrofit asynchrone via enqueue (callback classique)
    LaunchedEffect(Unit) {
        val call = RetrofitClient.api.getTodo()
        call.enqueue(object : Callback<todo> {
            override fun onResponse(call: Call<todo>, response: Response<todo>) {
                if (response.isSuccessful) {
                    val todo = response.body()
                    titre = todo?.title ?: "Réponse vide"
                } else {
                    titre = "Erreur HTTP ${response.code()}"
                }
            }

            override fun onFailure(call: Call<todo>, t: Throwable) {
                titre = "Erreur réseau : ${t.message}"
            }
        })
    }

    // Interface utilisateur simple avec Compose
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = titre, style = MaterialTheme.typography.titleLarge)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ToodoTheme {
        MyTodo()
    }
}
