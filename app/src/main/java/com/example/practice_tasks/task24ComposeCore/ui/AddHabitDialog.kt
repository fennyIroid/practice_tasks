package com.example.practice_tasks.task24ComposeCore.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun AddHabitDialog(
    onAdd: (String) -> Unit,
    onDismiss: () -> Unit
){
    var habitName by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "ADD NEW HABIT")
        },
        text = {
            TextField(
                value = habitName,
                onValueChange = {habitName = it},
                placeholder = {Text("Enter habit name")},
                singleLine = true
            )
        },

        confirmButton = {
            TextButton(
                onClick = {
                    if(habitName.isNotBlank()){
                        onAdd(habitName)
                        onDismiss()
                    }
                }
            ) {
                Text("ADD")
            }
        },

        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "CANCEL")
            }
        }

    )
}