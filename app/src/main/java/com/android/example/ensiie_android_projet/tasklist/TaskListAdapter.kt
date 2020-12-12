package com.android.example.ensiie_android_projet.tasklist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.example.ensiie_android_projet.R

class TaskListAdapter (private val taskList : List<Task>) : RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListAdapter.TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskListAdapter.TaskViewHolder, position: Int) {
        holder.bind(taskList[position])
    }

    override fun getItemCount(): Int {
        return taskList.size;
    }
    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(taskTitle: Task) {
            itemView.apply { // `apply {}` permet d'éviter de répéter `itemView.*`
                val title = findViewById<TextView>(R.id.task_title)
                val description = findViewById<TextView>(R.id.task_description)
                title.text=taskTitle.title
                description.text=taskTitle.description
            }
        }
    }
}