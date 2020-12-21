package com.android.example.ensiie_android_projet.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.example.ensiie_android_projet.tasklist.Task

class TasksRepository {
    private val tasksWebService = Api.tasksWebService

    // Ces deux variables encapsulent la même donnée:
    // [_taskList] est modifiable mais privée donc inaccessible à l'extérieur de cette classe
    private val _taskList = MutableLiveData<List<Task>>()
    // [taskList] est publique mais non-modifiable:
    // On pourra seulement l'observer (s'y abonner) depuis d'autres classes
    public val taskList: LiveData<List<Task>> = _taskList

    public var response: String?=null

    suspend fun refresh() {
        // Call HTTP (opération longue):
        val tasksResponse = tasksWebService.getTasks()
        // À la ligne suivante, on a reçu la réponse de l'API:
        if (tasksResponse.isSuccessful) {
            val fetchedTasks = tasksResponse.body()
            // on modifie la valeur encaapsulée, ce qui va notifier ses Observers et donc déclencher leur callback
            _taskList.value = fetchedTasks!!
        }
    }
    suspend fun delete(task:Task){
        val tasksResponse = tasksWebService.deleteTask(task.id)
        if(tasksResponse.isSuccessful){
            val editableList = _taskList.value.orEmpty().toMutableList()
            editableList.remove(task)
            _taskList.value=editableList
            response = "deleted"
        }
    }
    suspend fun updateTask(task:Task){
        val taskResponse = tasksWebService.updateTask(task,task.id)
        if(taskResponse.isSuccessful){
            val editableList = _taskList.value.orEmpty().toMutableList()
            val position = editableList.indexOfFirst { task.id == it.id }
            editableList[position] = task
            _taskList.value=editableList
            response="updated"
        }
    }

    suspend fun addTask(task:Task){
        val taskResponse = tasksWebService.createTask(task)
        if(taskResponse.isSuccessful)
        {
            val editableList = _taskList.value.orEmpty().toMutableList()
            editableList.add(task)
            _taskList.value=editableList
            response="added"
        }
    }
}