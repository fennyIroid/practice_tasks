package com.example.practice_tasks.task253rdPartyLib.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.practice_tasks.task253rdPartyLib.model.StudentResource

@Composable
fun StudentList() {
    val resources = listOf(
        StudentResource(
            "Data Structures",
            "https://picsum.photos/300/200"
        ),
        StudentResource(
            "Operating Systems",
            "https://picsum.photos/301/200"
        ),
        StudentResource(
            "Machine Learning",
            "https://picsum.photos/302/200"
        )
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(resources){resource ->
            StudentCard(resource)
        }
    }
}