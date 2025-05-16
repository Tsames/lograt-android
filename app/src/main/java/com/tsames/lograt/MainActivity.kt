package com.tsames.lograt

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.jvm.java

class MainActivity : BaseActivity() {
    private lateinit var workoutAdapter: WorkoutAdapter
    private val workouts = mutableListOf<Workout>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setupToolbar(toolbar, "Home", showUpButton = false)

        val workoutGrid = findViewById<RecyclerView>(R.id.workoutGrid)
        workoutAdapter = WorkoutAdapter(workouts) { workout ->
            val intent = Intent(this, Workout::class.java)
            intent.putExtra("WORKOUT_ID", workout.id)
            startActivity(intent)
        }
        workoutGrid.layoutManager = GridLayoutManager(this, 2)
        workoutGrid.adapter = workoutAdapter

        val workoutButton = findViewById<Button>(R.id.workoutButton)
        workoutButton.setOnClickListener {
            val newWorkout = Workout(workouts.size + 1, "Workout ${workouts.size + 1}")
            workoutAdapter.addWorkout(newWorkout)

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