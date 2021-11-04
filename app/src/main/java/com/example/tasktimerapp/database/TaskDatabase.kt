package com.example.tasktimerapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase



@Database(entities = [Tasks::class],version = 1,exportSchema = false)
abstract class TaskDatabase: RoomDatabase() {

    companion object{
        var instance: TaskDatabase?=null;
        fun getInstance(ctx: Context): TaskDatabase
        {
            if(instance!=null)
            {
                return  instance as TaskDatabase;
            }
            instance= Room.databaseBuilder(ctx,TaskDatabase::class.java,"TaskDB").run { allowMainThreadQueries() }.build();
            return instance as TaskDatabase;
        }
    }
    abstract fun taskDao():TaskDao;
}