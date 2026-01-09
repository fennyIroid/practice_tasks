package com.example.practice_tasks.task7Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.practice_tasks.R
class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Home Fragement", "onCreate")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("HomeFragment", "onViewCreated")

        val btnOpenProfile = view.findViewById<Button>(R.id.btnOpenProfile)

        btnOpenProfile.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("name", "Fenny")

            val profileFragment = ProfileFragment()
            profileFragment.arguments = bundle


            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, profileFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("HomeFragment", "onDestroyView")

    }

}