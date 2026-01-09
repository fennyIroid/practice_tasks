package com.example.practice_tasks.task21Architechture.ui.dashboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.practice_tasks.task21Architechture.data.model.Habit
import com.example.practice_tasks.task21Architechture.data.repository.HabitRepositoryImplementation
import com.example.practice_tasks.task21Architechture.data.source.FakeHabitDataSource
import com.example.practice_tasks.task21Architechture.domain.usecase.GetHabitUseCase
import com.example.practice_tasks.task21Architechture.domain.usecase.MarkHabitDone
import com.example.practice_tasks.task21Architechture.ui.dashboard.ui.theme.Practice_tasksTheme

/* ---------------- ACTIVITY ---------------- */

class HabitDashboardActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val dataSource = FakeHabitDataSource()
        val repository = HabitRepositoryImplementation(dataSource)
        val getHabitUseCase = GetHabitUseCase(repository)
        val markHabitDoneUseCase = MarkHabitDone(repository)

        val viewModel = HabitViewModel(
            getHabitUseCase = getHabitUseCase,
            markHabitDoneUseCase = markHabitDoneUseCase
        )

        setContent {
            Practice_tasksTheme {
                HabitDashboardScreen(viewModel)
            }
        }
    }
}

/* ---------------- SCREEN (STATEFUL) ---------------- */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitDashboardScreen(viewModel: HabitViewModel) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HabitDashboardContent(
        uiState = uiState,
        onHabitDoneClick = viewModel::onHabitDoneClicked
    )
}

/* ---------------- UI ONLY (PREVIEWABLE) ---------------- */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitDashboardContent(
    uiState: HabitUiState,
    onHabitDoneClick: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Habit Streak") })
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {

            if (uiState.isLoading) {
                CircularProgressIndicator()
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.habits) { habit ->
                        HabitCard(
                            habit = habit,
                            onDoneClick = { onHabitDoneClick(habit.id) }
                        )
                    }
                }
            }
        }
    }
}

/* ---------------- CARD ---------------- */

@Composable
private fun HabitCard(
    habit: Habit,
    onDoneClick: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = habit.name,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onDoneClick,
                enabled = !habit.isDoneToday
            ) {
                Text(if (habit.isDoneToday) "Done Today" else "Mark Done")
            }
        }
    }
}

/* ---------------- PREVIEW ---------------- */

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HabitDashboardPreview() {
    Practice_tasksTheme {
        HabitDashboardContent(
            uiState = HabitUiState(
                isLoading = false,
                habits = listOf(
                    Habit(1, "Drink Water", 7, false),
//                    Habit(2, "Exercise", 3, false),
//                    Habit(3, "Read a Book", 1, false),
                )
            ),
            onHabitDoneClick = {}
        )
    }
}
