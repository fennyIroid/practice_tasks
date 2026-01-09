package com.example.habittracker

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.practice_tasks.task24ComposeCore.Habit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitList(
    habits: List<Habit>,
    onToggle: (Habit) -> Unit,
    onDelete: (Habit) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(
            items = habits,
            key = { it.title }
        ) { habit ->

            val dismissState = rememberSwipeToDismissBoxState(
                confirmValueChange = {
                    if (it == SwipeToDismissBoxValue.EndToStart) {
                        onDelete(habit)
                        true
                    } else false
                }
            )

            val backgroundColor by animateColorAsState(
                targetValue =if (habit.isDone)
                    Color(0xFFD1F2EB)
                else
                    Color(0xFFF2F2F2)
            )

            AnimatedVisibility(
                visible = true,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                SwipeToDismissBox(
                    state = dismissState,
                    backgroundContent = {},
                    content = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                                .background(
                                    color = backgroundColor,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .combinedClickable(
                                    onClick = { onToggle(habit) },
                                    onLongClick = { onDelete(habit) }
                                )
                                .padding(16.dp)
                        ) {
                            Text(
                                text = habit.title,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                )
            }


        }

    }
}
