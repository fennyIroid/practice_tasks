package com.example.practice_tasks.task26Maps.ui

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen() {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val defaultLocation = LatLng(20.5937, 78.9629)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultLocation, 5f)
    }

    val markerState = remember {
        MarkerState(position = defaultLocation)
    }

    val placeLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            val place = Autocomplete.getPlaceFromIntent(result.data!!)
            place.latLng?.let { latLng ->
                markerState.position = latLng
                coroutineScope.launch {
                    cameraPositionState.animate(
                        CameraUpdateFactory.newLatLngZoom(latLng, 15f)
                    )
                }
            }
        } else if (result.resultCode == Activity.RESULT_CANCELED) {
            // User dismissed autocomplete – DO NOTHING
            // This prevents weird behavior
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Search Place") },
                actions = {
                    TextButton(onClick = {
                        val fields = listOf(
                            Place.Field.NAME,
                            Place.Field.LAT_LNG
                        )

                        val intent = Autocomplete.IntentBuilder(
                            AutocompleteActivityMode.FULLSCREEN,
                            fields
                        ).build(context)

                        placeLauncher.launch(intent)

                    }) {
                        Text("Search")
                    }
                }
            )
        }
    ) { paddingValues ->

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            contentPadding = paddingValues
        ) {
            Marker(
                state = markerState,
                title = "Selected Location"
            )
        }
    }
}
