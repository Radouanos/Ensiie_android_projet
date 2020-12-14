package com.android.example.ensiie_android_projet.tasklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.example.ensiie_android_projet.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class TaskListFragment : Fragment()
{
    //private val taskList = listOf("Task 1", "Task 2", "Task 3")
    private val taskList = mutableListOf(
            Task(id = "id_1", title = "Task 1", description = "description 1"),
            Task(id = "id_2", title = "Task 2"),
            Task(id = "id_3", title = "Task 3")
    )
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        val rootView = inflater.inflate(R.layout.fragment_task_list,container,false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        val fab = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)

        recyclerView.layoutManager = LinearLayoutManager(activity)
        val adapter = TaskListAdapter()
        recyclerView.adapter = adapter
        adapter.submitList(taskList)

        fab.setOnClickListener{
            taskList.add(Task(id = UUID.randomUUID().toString(), title = "Task ${taskList.size + 1}"))
            adapter.notifyDataSetChanged()
        }
        adapter.onDeleteTask = {
            task ->
                taskList.remove(task)
                adapter.notifyDataSetChanged()
        }
    }
}
