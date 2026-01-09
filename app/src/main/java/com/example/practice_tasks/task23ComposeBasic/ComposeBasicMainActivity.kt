package com.example.practice_tasks.task23ComposeBasic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class ComposeBasicMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                ProfileScreen()
            }
        }
    }
}

@Composable
fun ProfileCard(
    name: String,
    role: String,
    isEditing: Boolean,
    onEditclick:() -> Unit) {
    // Glass Card Container
    Box(
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .wrapContentHeight()
    ) {

        // Blurred background layer (real glass effect)
        Box(
            modifier = Modifier
                .matchParentSize()
                .blur(20.dp)
                .background(Color.White.copy(alpha = 0.15f))
        )

        // Glass Card
        Card(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color.White.copy(alpha = 0.35f),
                    shape = RoundedCornerShape(24.dp)
                ),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(6.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFD77DD2).copy(alpha = 0.2f),
                contentColor = Color.Black
            )
        ) {

            Column(
                modifier = Modifier
                    .padding(top = 72.dp, bottom = 24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = name,
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = role,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onEditclick,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        if (isEditing) "Editing..." else "Edit Profile"
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileScreen() {

    var isEditing by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F1EE)),
        contentAlignment = Alignment.Center
    ) {

        ProfileCard(
            name = "Dummy",
            role = "Android Dev",
            isEditing = isEditing,
            onEditclick = {isEditing =! isEditing}
        )


        // Profile Image (floating)
        Box(
            modifier = Modifier
                .size(120.dp)
                .offset(y = (-140).dp)
                .background(Color.Gray, CircleShape)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    MaterialTheme {
        ProfileScreen()
    }
}
