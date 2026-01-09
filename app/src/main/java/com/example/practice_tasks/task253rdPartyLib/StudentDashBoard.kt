package com.example.practice_tasks.task253rdPartyLib

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.practice_tasks.task18Coroutines.ui.DashboardActivity
import com.example.practice_tasks.task253rdPartyLib.model.StudentResource
import com.example.practice_tasks.task253rdPartyLib.ui.EmptyState
import com.example.practice_tasks.task253rdPartyLib.ui.ShimmerList
import com.example.practice_tasks.task253rdPartyLib.ui.StudentCard
import com.example.practice_tasks.task253rdPartyLib.ui.StudentList
import com.example.practice_tasks.task253rdPartyLib.ui.theme.Practice_tasksTheme
import kotlinx.coroutines.delay
import timber.log.Timber

class StudentDashBoard : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {

                DashboardScreen()
            }
        }
    }
}

@Composable
fun DashboardScreen() {
    var isLoading by remember { mutableStateOf(true) }

//    val resources = remember {
//        emptyList<StudentResource>()
//    }

    LaunchedEffect(Unit) {

        Timber.d("DashBoard Loading Started")

        delay(2000)
        isLoading = false
    }

  when{
      isLoading -> {
          ShimmerList()
      }
//          Lottie Animation
//      resources.isEmpty() -> {
//          Box(
//
//              modifier = Modifier
//                  .fillMaxSize(),
//              contentAlignment = Alignment.Center
//          ){
//              EmptyState()
//          }
//      }
      else -> {
          StudentList()
      }
  }


}

@Preview(showBackground = true)
@Composable
fun DashBoardPreview() {
    Practice_tasksTheme {
        DashboardScreen()
    }
}