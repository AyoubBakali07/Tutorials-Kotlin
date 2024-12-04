import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import com.example.tuto3_plus.ui.theme.Tuto3_plusTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Tuto3_plusTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    InteractiveButtonsApp()
                }
            }
        }
    }
}

@Composable
fun InteractiveButtonsApp(){
    MaterialTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OpenWebsiteButton()
                SendEmailButton()
                DialPhoneNumberButton()
            }
        }
    }
}

@Composable
fun OpenWebsiteButton() {
    val context = LocalContext.current // Access the current context
    Button(onClick = {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"))
        context.startActivity(intent) // Use context to start the activity
    }) {
        Text("Ouvrir un site web")
    }
}

@Composable
fun SendEmailButton() {
    val context = LocalContext.current // Access the current context
    Button(onClick = {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:test@example.com")
            putExtra(Intent.EXTRA_SUBJECT, "Sujet de l'email")
        }
        context.startActivity(intent) // Use context to start the activity
    }) {
        Text("Envoyer un email")
    }
}

@Composable
fun DialPhoneNumberButton() {
    val context = LocalContext.current // Access the current context
    Button(onClick = {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:+123456789"))
        context.startActivity(intent) // Use context to start the activity
    }) {
        Text("Appeler un numéro")
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    Tuto3_plusTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            OpenWebsiteButton()
            SendEmailButton()
            DialPhoneNumberButton()
        }
    }
}
