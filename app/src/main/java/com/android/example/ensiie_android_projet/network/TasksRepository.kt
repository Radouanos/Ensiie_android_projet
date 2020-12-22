package com.android.example.ensiie_android_projet.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.example.ensiie_android_projet.tasklist.Task

class TasksRepository {
    private val tasksWebService = Api.tasksWebService

    suspend fun loadTasks(): List<Task>? {
        // Call HTTP (opération longue):
        val response = tasksWebService.getTasks()
        // retourner la réponse de l'API
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun delete(task:Task) : Boolean {
        val tasksResponse = tasksWebService.deleteTask(task.id)
        return (tasksResponse.isSuccessful)
    }
    suspend fun updateTask(task:Task) : Boolean {
        val taskResponse = tasksWebService.updateTask(task,task.id)
        return (taskResponse.isSuccessful)
    }

    suspend fun addTask(task:Task):Boolean {
        val taskResponse = tasksWebService.createTask(task)
        return (taskResponse.isSuccessful)
    }
}