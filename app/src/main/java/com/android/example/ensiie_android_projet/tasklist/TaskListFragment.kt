package com.android.example.ensiie_android_projet.tasklist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.example.ensiie_android_projet.MainActivity
import com.android.example.ensiie_android_projet.R
import com.android.example.ensiie_android_projet.task.TaskActivity
import com.android.example.ensiie_android_projet.task.TaskActivity.Companion.ADD_TASK_REQUEST_CODE
import com.android.example.ensiie_android_projet.task.TaskActivity.Companion.EDIT_TASK_REQUEST_CODE
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

    private val intent_add_task = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result : ActivityResult ->
        if(result.resultCode == Activity.RESULT_OK)
        {
            val intent = result.data!!.getSerializableExtra(TaskActivity.TASK_KEY) as Task
            taskList.add(intent)
            adapter.notifyDataSetChanged()
        }
    }

    private val intent_edit_task = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data!!.getSerializableExtra(TaskActivity.TASK_KEY) as Task
            taskList.set(taskList.indexOf(taskList.find { it.id == intent.id }), intent)
            adapter.notifyDataSetChanged()
        }
    }

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
            intent_add_task.launch(Intent(activity,TaskActivity::class.java))
        }
        adapter.onDeleteTask = {
            task ->
                taskList.remove(task)
                adapter.notifyDataSetChanged()
        }
        adapter.onEditTask = {
            task ->
            intent_edit_task.launch(Intent(activity,TaskActivity::class.java).putExtra("task",task))
        }
        adapter.onShareTask = {
            task ->
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, task.title+" + "+task.description)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, "choisir une application")
            startActivity(shareIntent)
        }
    }
}
