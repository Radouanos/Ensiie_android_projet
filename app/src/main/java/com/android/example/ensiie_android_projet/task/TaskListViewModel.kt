package com.android.example.ensiie_android_projet.task

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.example.ensiie_android_projet.network.TasksRepository
import com.android.example.ensiie_android_projet.tasklist.Task
import kotlinx.coroutines.launch

// Le ViewModel met à jour la liste de task qui est une LiveData
class TaskListViewModel : ViewModel() {
    // [_taskList] est modifiable mais privée donc inaccessible à l'extérieur de cette classe
    private val _taskList = MutableLiveData<List<Task>>()
    // [taskList] est publique mais non-modifiable:
    // On pourra seulement l'observer (s'y abonner) depuis d'autres classes
    public val taskList : LiveData<List<Task>> = _taskList
    private val repository = TasksRepository()

    fun loadTasks()
    {
        viewModelScope.launch {
            val fetchedTasks = repository.loadTasks()
            _taskList.value=fetchedTasks!!
        }
    }
    fun deleteTask(task:Task)
    {
        viewModelScope.launch {
            if(repository.delete(task))
            {
                val editableList = _taskList.value.orEmpty().toMutableList()
                editableList.remove(task)
                _taskList.value=editableList

            }
        }
    }
    fun addTask(task:Task)
    {
        viewModelScope.launch {
            if(repository.addTask(task))
            {
                val editableList = _taskList.value.orEmpty().toMutableList()
                editableList.add(task)
                _taskList.value=editableList

            }
        }
    }
    fun editTask(task:Task)
    {
        viewModelScope.launch {
            if(repository.updateTask(task))
            {
                val editableList = _taskList.value.orEmpty().toMutableList()
                val position = editableList.indexOfFirst { task.id == it.id }
                editableList[position]=task
                _taskList.value=editableList

            }
        }
    }
}