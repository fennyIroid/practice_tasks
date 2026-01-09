package com.example.practice_tasks.task24ComposeCore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.habittracker.HabitList
import com.example.practice_tasks.task24ComposeCore.ui.AddHabitDialog
import com.example.practice_tasks.task24ComposeCore.ui.theme.Practice_tasksTheme
import kotlinx.coroutines.launch

class HabitTracker : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                HabitApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitApp(
    habitViewModel: HabitViewModel = viewModel()
) {

    val habits by habitViewModel.habits.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var showAddDialog by remember { mutableStateOf(false) }



    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Habits 🔄🎯") }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showAddDialog =true
                }
            ) {
                Text("+")
            }
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            if (habits.isEmpty()) {
                Text(
                    text = "No habits yet",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            } else {
                HabitList(
                    habits = habits,
                    onToggle = habitViewModel::toggleHabit,   //  ViewModel handles logic
                    onDelete = habitViewModel::deleteHabitApi,   //  ViewModel handles logic
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        if (showAddDialog) {
            AddHabitDialog(
                onAdd = { name ->
                    habitViewModel.addHabitApi(name)
                    scope.launch {
                        snackbarHostState.showSnackbar("Habit Added")
                    }
                },
                onDismiss = {
                    showAddDialog = false
                }
            )
        }

    }
}

@Preview(
    name = "Phone",
    device = Devices.PIXEL_4,
    showSystemUi = true,
    showBackground = true
)
@Composable
fun HabitAppPreview() {
    Practice_tasksTheme {
        HabitApp()
    }
}
