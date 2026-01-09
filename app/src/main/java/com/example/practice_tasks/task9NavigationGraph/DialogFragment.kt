package com.example.practice_tasks.task9NavigationGraph

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class DialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle("Info")
            .setMessage("This is a dialog destination")
            .setPositiveButton("OK", null)
            .create()
    }
}
