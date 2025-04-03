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
import kotlinx.coroutines.delay


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTodo()
//            CompteurAvecCoroutine()

        }
    }
}
@Composable
fun MyTodo() {
    var compteur by remember { mutableStateOf(0) }

    // ✅ Solution sécurisée et fluide
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000) // ✅ Attente non-bloquante
            compteur++  // ✅ Mise à jour sécurisée via Compose
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Compteur : $compteur", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { compteur = 0 }) {
            Text("Réinitialiser")
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
