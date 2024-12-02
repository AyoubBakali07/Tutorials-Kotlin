package com.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project.ui.theme.ProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GreetingImage(
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun GreetingImage(modifier: Modifier = Modifier) {
    // List of image resources, titles, and descriptions to randomly choose from
    val imageList = listOf(
        Triple(R.drawable.essaouira, "Essaouira", "Essaouira is a charming coastal city in Morocco."),
        Triple(R.drawable.essaouira_3, "Essaouira Harbor", "The harbor of Essaouira is famous for its fishing industry."),
        Triple(R.drawable.essaouira_4, "Ramparts of Essaouira", "The 18th-century ramparts surround the medina of Essaouira.")
    )

    // Remember the current image, title, and description
    var currentImage by remember { mutableStateOf(imageList[0]) }

    Box(modifier = modifier.fillMaxSize()) {
        // Constant background image
        Image(
            painter = painterResource(id = R.drawable.travel), // Replace with your constant background image
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize() // Full background
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
        ) {
            // Foreground image with white border that changes on click
            Image(
                painter = painterResource(id = currentImage.first),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(250.dp) // Adjust size as needed
                    .border(4.dp, Color.White) // White border
                    .clickable {
                        // Randomly choose a new image from the list
                        currentImage = imageList.random()
                    }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Title text below the image
            Text(
                text = currentImage.second,
                fontSize = 24.sp,
                color = Color.White // Adjust color as needed
            )

            // Description text below the title
            Text(
                text = currentImage.third,
                fontSize = 16.sp,
                color = Color.White, // Adjust color as needed
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BirthdayCardPreview() {
    ProjectTheme {
        GreetingImage()
    }
}
