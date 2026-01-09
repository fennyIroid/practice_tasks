package com.example.practice_tasks.task18Coroutines.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.practice_tasks.R
import com.example.practice_tasks.task18Coroutines.ui.DashboardViewModel
import com.example.practice_tasks.task18Coroutines.data.DashboardRepository
import kotlinx.coroutines.launch


class DashboardActivity : AppCompatActivity() {

    private val viewModel: DashboardViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T{
                return DashboardViewModel(DashboardRepository()) as T
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dashboard)

        val txtProfile = findViewById<TextView>(R.id.txtProfile)
        val txtFeedError = findViewById<TextView>(R.id.txtFeedError)
        val progressBar = findViewById<ProgressBar>(R.id.prgressBar)
        val btnCancel = findViewById<Button>(R.id.btnCancel)
        val txtFeed = findViewById<TextView>(R.id.tvFeed)
        val txtNotifications = findViewById<TextView>(R.id.txtNotifications)

        viewModel.loadDashboard()

        lifecycleScope.launch {
            viewModel.profile.collect { profile ->
                profile?.let {
                   txtProfile.text = "${it.name} - ${it.role}"
                }

            }
        }

        lifecycleScope.launch {
            viewModel.feed.collect { feed ->
                progressBar.visibility = View.GONE
                txtFeed.text = feed.joinToString("\n"){"${it?.title}"}
            }
        }

        lifecycleScope.launch {
            viewModel.notification.collect { notifications ->
                if (notifications.isNotEmpty()) {
                    txtNotifications.text =
                        notifications.joinToString("\n") { "🔔 ${it?.message}" }
                }
            }
        }


        lifecycleScope.launch {
            viewModel.feedError.collect { error->
                txtFeedError.visibility = if (error) View.VISIBLE else View.GONE
            }
        }

        btnCancel.setOnClickListener {
            viewModel.cancelLoading()
            progressBar.visibility = View.GONE
        }
        }

}
