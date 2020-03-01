package com.capstone.androidproject.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.capstone.androidproject.R
import com.capstone.androidproject.SharedPreferenceConfig.App

class HomeFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v:View = inflater.inflate(R.layout.fragment_home, container, false)

        val point:Int = 1000
        var username:TextView = v.findViewById(R.id.userName)
        var userpoint:TextView = v.findViewById(R.id.userPoint)
        username.setText(App.prefs.name)
        userpoint.setText(point.toString())

        return v
    }

}
