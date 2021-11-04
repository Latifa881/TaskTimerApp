package com.example.tasktimerapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Tasks")
data class Tasks(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id : Int = 0,
    @ColumnInfo(name = "Name") var name: String,
    @ColumnInfo(name = "Description") var description: String,
    @ColumnInfo(name = "TotalTimer") var totalTimer: String,
)