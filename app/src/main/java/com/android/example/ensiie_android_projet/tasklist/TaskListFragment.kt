package com.android.example.ensiie_android_projet.tasklist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.example.ensiie_android_projet.MainActivity
import com.android.example.ensiie_android_projet.R
import com.android.example.ensiie_android_projet.task.TaskActivity
import com.android.example.ensiie_android_projet.task.TaskActivity.Companion.ADD_TASK_REQUEST_CODE
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

    val adapter = TaskListAdapter()

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
        recyclerView.adapter = adapter
        adapter.submitList(taskList)

        fab.setOnClickListener{
            val intent = Intent(activity,TaskActivity::class.java)
            startActivityForResult(intent,ADD_TASK_REQUEST_CODE)
        }
        adapter.onDeleteTask = {
            task ->
                taskList.remove(task)
                adapter.notifyDataSetChanged()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == AppCompatActivity.RESULT_OK && requestCode == ADD_TASK_REQUEST_CODE) {
            val task = data!!.getSerializableExtra(TaskActivity.TASK_KEY) as Task
            taskList.add(task)
            adapter.notifyDataSetChanged()
        }
    }
}
