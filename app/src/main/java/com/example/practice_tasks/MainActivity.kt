package com.example.practice_tasks

import com.example.practice_tasks.task24ComposeCore.HabitTracker
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.practice_tasks.databinding.ActivityMainBinding
import com.example.practice_tasks.task10PermissionImgPick.PermissionImgPickMainActivity
import com.example.practice_tasks.task11SPandDataStore.Task11MainActivity
import com.example.practice_tasks.task12DBRoom_ExpenseTracker.activity.AddExpenseActivity
import com.example.practice_tasks.task12DBRoom_ExpenseTracker.activity.ExpenseListActivity
import com.example.practice_tasks.task13Workmanager.Task13MainActivity
import com.example.practice_tasks.task14Retrofit.ui.notes.NotesActivity
import com.example.practice_tasks.task15MultipartApi.ui.ProfileActivity
import com.example.practice_tasks.task16PushNotifications.ReminderDetailsActivity
import com.example.practice_tasks.task17FirebaseCrashlytics.Task17MainActivity
import com.example.practice_tasks.task18Coroutines.ui.DashboardActivity
import com.example.practice_tasks.task19Stateflow.ui.StateFlowMainActivity
import com.example.practice_tasks.task2.DataBindingActivity
import com.example.practice_tasks.task2.ScopeFunctionsActivity
import com.example.practice_tasks.task2.ViewBindingActivity
import com.example.practice_tasks.task21Architechture.ui.dashboard.HabitDashboardActivity
import com.example.practice_tasks.task22Hilt.ui.SettingsActivityTask22
import com.example.practice_tasks.task23ComposeBasic.ComposeBasicMainActivity
import com.example.practice_tasks.task3Ui.DynamicListActivity
import com.example.practice_tasks.task4Layouts.LayoutPracticeActivity
import com.example.practice_tasks.task5Styles.ThemesPracticeActivity
import com.example.practice_tasks.task7Fragments.MainActivityFragment
import com.example.practice_tasks.task8intents.IntentMainActivity
import com.example.practice_tasks.task9NavigationGraph.NavGraphMainActivity
import com.google.firebase.messaging.FirebaseMessaging
import com.example.practice_tasks.task253rdPartyLib.StudentDashBoard
import com.example.practice_tasks.task26Maps.MapsComposeDemo
import com.example.practice_tasks.task27SocialLogin.ui.SocialLoginActivity
import com.example.practice_tasks.task28Socket.SocketMainActivity
import kotlin.jvm.java

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val requestPermissionLauncher = registerForActivityResult(
        androidx.activity.result.contract.ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
           startActivity(Intent(this, ReminderDetailsActivity::class.java))
        } else {
            // Explain to the user that the feature is unavailable because the
            // features requires a permission that the user has denied.
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()



        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ✅ Handle Notification Click (Background)
        intent.extras?.getString("reminderId")?.let { id ->
            startActivity(Intent(this, ReminderDetailsActivity::class.java).apply {
                putExtra("reminderId", id)
            })
        } ?: intent.extras?.getString("REMINDER_ID")?.let { id ->
            // Fallback for older format if needed
            startActivity(Intent(this, ReminderDetailsActivity::class.java).apply {
                putExtra("reminderId", id)
            })
        }

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FCM", "Fetching FCM registration token failed", task.exception)
                return@addOnCompleteListener
            }
            val token = task.result
            Log.d("FCM", "Token: $token")
        }

        binding.btnViewBinding.setOnClickListener {
            startActivity(Intent(this, ViewBindingActivity::class.java))
        }

        binding.btnDataBinding.setOnClickListener {
            startActivity(Intent(this, DataBindingActivity::class.java))
        }

        binding.btnScopeFunctions.setOnClickListener {
            startActivity(Intent(this, ScopeFunctionsActivity::class.java))
        }

        binding.btnUiComponent.setOnClickListener {
            startActivity(Intent(this, DynamicListActivity::class.java))
        }

        binding.btnLayoutTask.setOnClickListener {
            startActivity(Intent(this, LayoutPracticeActivity::class.java))
        }

        binding.btnStyleTask.setOnClickListener {
            startActivity(Intent(this, ThemesPracticeActivity::class.java))
        }

        binding.btnFragment.setOnClickListener {
            startActivity(Intent(this, MainActivityFragment::class.java))
        }

        binding.btnIntentTask.setOnClickListener {
            startActivity(Intent(this, IntentMainActivity::class.java))
        }

        binding.btnNavigationGraph.setOnClickListener {
            startActivity(Intent(this, NavGraphMainActivity::class.java))
        }

        binding.btnPermissionImgPick.setOnClickListener {
            startActivity(Intent(this, PermissionImgPickMainActivity::class.java))
        }

        binding.btnTask11.setOnClickListener {
            startActivity(Intent(this, Task11MainActivity::class.java))
        }

        binding.btnTask12.setOnClickListener {
            startActivity(Intent(this, ExpenseListActivity::class.java))
        }

        binding.btnTask13.setOnClickListener {
            startActivity(Intent(this, Task13MainActivity::class.java))
        }

        binding.btnRetrofit.setOnClickListener {
            startActivity(Intent(this, NotesActivity::class.java))
        }

        binding.btnMultipart.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        binding.btnPushNotification.setOnClickListener {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                if (androidx.core.content.ContextCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.POST_NOTIFICATIONS
                    ) == android.content.pm.PackageManager.PERMISSION_GRANTED
                ) {
                    startActivity(Intent(this, ReminderDetailsActivity::class.java))
                } else {
                    requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                }
            } else {
                startActivity(Intent(this, ReminderDetailsActivity::class.java))
            }

        }

        binding.btnCrashlytics.setOnClickListener {
            startActivity(Intent(this, Task17MainActivity::class.java))
        }

        binding.btnCoroutines.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }

        binding.btnStateflow.setOnClickListener {
            startActivity(Intent(this, StateFlowMainActivity::class.java))
        }

        binding.btnArchitecture.setOnClickListener {
            startActivity(Intent(this, HabitDashboardActivity::class.java))
        }

        binding.btnHilt.setOnClickListener {
            startActivity(Intent(this, SettingsActivityTask22::class.java))
        }

        binding.btnComposeCore.setOnClickListener {
            startActivity(Intent(this, HabitTracker::class.java))
        }

        binding.btnComposeIntro.setOnClickListener {
            startActivity(Intent(this, ComposeBasicMainActivity::class.java))
        }


        binding.btn3rdParty.setOnClickListener {
            startActivity(Intent(this, StudentDashBoard::class.java))
        }

        binding.btnMaps.setOnClickListener {
            startActivity(Intent(this, MapsComposeDemo::class.java))
        }

        // [MODIFIED] Added navigation to Social Login Activity
        binding.btnSocialLogin.setOnClickListener {
            startActivity(Intent(this, SocialLoginActivity::class.java))
        }

        binding.btnSocket.setOnClickListener {
            startActivity(Intent(this, SocketMainActivity::class.java))
        }


    }
}