package com.android.example.ensiie_android_projet.tasklist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.android.example.ensiie_android_projet.MainActivity
import com.android.example.ensiie_android_projet.R
import com.android.example.ensiie_android_projet.network.Api
import com.android.example.ensiie_android_projet.network.TasksRepository
import com.android.example.ensiie_android_projet.task.TaskActivity
import com.android.example.ensiie_android_projet.task.TaskActivity.Companion.ADD_TASK_REQUEST_CODE
import com.android.example.ensiie_android_projet.task.TaskActivity.Companion.EDIT_TASK_REQUEST_CODE
import com.android.example.ensiie_android_projet.task.TaskListViewModel
import com.android.example.ensiie_android_projet.userinfo.UserInfoActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch
import java.util.*

class TaskListFragment : Fragment()
{
    private val viewModel : TaskListViewModel by viewModels()

    val adapter = TaskListAdapter()

    var textret:TextView?=null

    var avatar:ImageView?=null

    private val intent_add_task = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result : ActivityResult ->
        if(result.resultCode == Activity.RESULT_OK)
        {
            val task = result.data!!.getSerializableExtra(TaskActivity.TASK_KEY) as Task
                viewModel.addTask(task)
        }
    }

    private val intent_edit_task = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = result.data!!.getSerializableExtra(TaskActivity.TASK_KEY) as Task
                viewModel.editTask(task)
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
        avatar = view.findViewById<ImageView>(R.id.avatar)
        avatar?.setOnClickListener {
            val intent = Intent(activity,UserInfoActivity::class.java)
            startActivity(intent)
        }
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        fab.setOnClickListener{
            intent_add_task.launch(Intent(activity,TaskActivity::class.java))
        }
        adapter.onDeleteTask = {
            task ->
                lifecycleScope.launch{
                    viewModel.deleteTask(task)
                }
        }
        adapter.onEditTask = {
            task ->
            intent_edit_task.launch(Intent(activity,TaskActivity::class.java).putExtra("task",task))
        }

        textret = view.findViewById(R.id.retrotext)

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

        viewModel.taskList.observe(viewLifecycleOwner, androidx.lifecycle.Observer { newList->
            adapter.submitList(newList.orEmpty())
            adapter.notifyDataSetChanged()
        })
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch{
            val userInfo = Api.userService.getInfo().body()!!
            textret?.text = "${userInfo.firstName} ${userInfo.lastName}"
        }
        viewModel.loadTasks()

        avatar?.load("https://goo.gl/gEgYUd"){
            transformations(CircleCropTransformation())
        }
    }
}
