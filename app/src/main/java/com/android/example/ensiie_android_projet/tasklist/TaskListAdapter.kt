package com.android.example.ensiie_android_projet.tasklist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.example.ensiie_android_projet.R
/*
* Pour comprendre le principe de ListAdapter:
* https://blog.usejournal.com/why-you-should-be-using-the-new-and-improved-listadapter-in-android-17a2ab7ca644
* */
class TaskListAdapter : androidx.recyclerview.widget.ListAdapter<Task,TaskListAdapter.TaskViewHolder>(TaskDiffCallback())
{
    var onDeleteTask :((Task) -> Unit)?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListAdapter.TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskListAdapter.TaskViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(taskTitle: Task) {
            itemView.apply { // `apply {}` permet d'éviter de répéter `itemView.*`
                val title = findViewById<TextView>(R.id.task_title)
                val description = findViewById<TextView>(R.id.task_description)
                val delbutton = findViewById<ImageButton>(R.id.del_button)
                title.text=taskTitle.title
                description.text=taskTitle.description
                delbutton.setOnClickListener{onDeleteTask?.invoke(taskTitle)}
            }
        }
    }
}
class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.title == newItem.title && newItem.description == oldItem.description
    }
}