package com.example.practice_tasks.task26Maps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.practice_tasks.task26Maps.ui.MapScreen
import com.example.practice_tasks.task26Maps.ui.theme.Practice_tasksTheme

class MapsComposeDemo : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Practice_tasksTheme {
                MapScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MapPreview() {
    Practice_tasksTheme {
        MapScreen()
    }
}
