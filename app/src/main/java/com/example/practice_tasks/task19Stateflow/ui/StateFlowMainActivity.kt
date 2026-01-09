package com.example.practice_tasks.task19Stateflow.ui

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.practice_tasks.R
import kotlinx.coroutines.launch

class StateFlowMainActivity : AppCompatActivity() {
    private val viewModel: ViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_state_flow_main)

        val btnLoad = findViewById<Button>(R.id.btnLoadUser)
        val txtResult = findViewById<TextView>(R.id.txtResult)

        btnLoad.setOnClickListener {
            viewModel.loadUsers()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    viewModel.uiState.collect { State ->
                        when(State) {
                            UiState.Idle -> txtResult.text = "Idle..."
                            UiState.Loading -> txtResult.text = "Loading..."
                            is UiState.Success -> {
                                txtResult.text = State.users.joinToString { it.name }
                            }
                            is UiState.Error -> {
                                txtResult.text = "Error: ${State.message}"
                            }

                        }
                    }
                }

                launch {
                    viewModel.toastEvent.collect {
                        Toast.makeText(this@StateFlowMainActivity,it,Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }
}