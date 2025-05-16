package com.tsames.lograt

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Date

class WorkoutLog : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_workout)

        // Get workout Data through Intent
        val workoutId = intent.getIntExtra("WORKOUT_ID", -1)
        val workoutName = intent.getStringExtra("WORKOUT_NAME") ?: "Unknown"
        val workoutDate = Date(intent.getLongExtra("WORKOUT_DATE", 0))
        val workoutTag = intent.getStringExtra("WORKOUT_TAG") ?: "No Tag"

        //Toolbar
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setupToolbar(toolbar, workoutName, showUpButton = true)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed() // Navigate back to the previous activity
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}