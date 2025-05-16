package com.tsames.lograt

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Calendar
import kotlin.jvm.java
import java.util.Date

class MainActivity : BaseActivity() {
    private lateinit var workoutAdapter: WorkoutAdapter
    private val workouts = mutableListOf<WorkoutModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Toolbar
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setupToolbar(toolbar, "Home", showUpButton = false)

        // Helper function to create a Date object for a specific day in May
        fun createDate(day: Int): Date {
            val calendar = Calendar.getInstance()
            calendar.set(2025, Calendar.MAY, day) // Year: 2023, Month: May, Day: specified day
            return calendar.time
        }

        // Dummy Workouts
        workouts.addAll(
            listOf(
                WorkoutModel(1, createDate(12), "Chest & Triceps Day", WorkoutType
                    .Chest),
                WorkoutModel(2, createDate(13), "Leg Day", WorkoutType.Legs),
                WorkoutModel(3, createDate(14), "Back & Biceps Day", WorkoutType.Back),
                WorkoutModel(4, createDate(15), "Shoulders & Abs", WorkoutType
                    .Shoulders)
            )
        )

        // Recent Workouts Grid
        val workoutGrid = findViewById<RecyclerView>(R.id.workoutGrid)
        workoutGrid.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        workoutAdapter = WorkoutAdapter(workouts) { workout ->
            val intent = Intent(this, WorkoutLog::class.java)
            intent.putExtra("WORKOUT_ID", workout.id)
            startActivity(intent)
        }
        workoutGrid.adapter = workoutAdapter

        // Create workout button & Listener
        val workoutButton = findViewById<Button>(R.id.workoutButton)
        workoutButton.setOnClickListener {
            val newWorkout = WorkoutModel(workouts.size + 1, Date(),"Workout ${workouts.size + 
                    1}", null)
            workoutAdapter.addWorkout(newWorkout)
            workoutGrid.apply {
                layoutManager?.scrollToPosition(0) // Scroll to the top
            }

            val intent = Intent(this, WorkoutLog::class.java)
            intent.putExtra("WORKOUT_ID", newWorkout.id)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}