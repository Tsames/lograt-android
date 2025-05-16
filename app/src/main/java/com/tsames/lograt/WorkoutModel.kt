package com.tsames.lograt

import java.util.Date

enum class WorkoutType {
    Legs,
    Chest,
    Back,
    Shoulders,
    Abs,
    Run,
    Swim,
    Bike,
    Arms
}

data class WorkoutModel (
    val id: Int,
    val date: Date,
    val name: String,
    val type: WorkoutType?,
)