package com.example.tasktimerapp.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao{

    @Query("SELECT * FROM Tasks")
    fun getAllMyTasks(): LiveData<List<Tasks>>

    @Insert
    fun insertTask(tasks: Tasks)

    @Query("DELETE FROM Tasks where id=:pk")
    fun delete(pk: Int)

    @Query("UPDATE Tasks SET TotalTimer=:timer where id=:pk")
    fun update(timer: String,pk: Int)

    @Delete
    fun deleteOBJ(tasks: Tasks)

    @Update
    fun updateOBJ(tasks: Tasks)

}

