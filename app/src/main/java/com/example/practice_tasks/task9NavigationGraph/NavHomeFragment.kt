package com.example.practice_tasks.task9NavigationGraph

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.practice_tasks.R

class NavHomeFragment : Fragment(R.layout.fragment_nav_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnInfo = view.findViewById<Button>(R.id.btnInfo)
        btnInfo.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_dialog)
        }

        btnInfo.setOnLongClickListener {
            requireActivity()
                .findViewById<androidx.drawerlayout.widget.DrawerLayout>(R.id.main)
                .openDrawer(android.view.Gravity.START)
            true
        }
    }
}