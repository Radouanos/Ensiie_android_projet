package com.android.example.ensiie_android_projet.task

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.android.example.ensiie_android_projet.R
import com.android.example.ensiie_android_projet.tasklist.Task
import java.util.*

class TaskActivity : AppCompatActivity() {
    companion object {
        const val ADD_TASK_REQUEST_CODE = 666
        const val TASK_KEY = "task key"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)
        val bvalider = findViewById<Button>(R.id.button_valider)
        val title = findViewById<EditText>(R.id.add_title)
        val description = findViewById<EditText>(R.id.add_description)
        bvalider.setOnClickListener{
            val newTask = Task(id = UUID.randomUUID().toString(), title = title.text.toString(), description = description.text.toString())
            intent.putExtra(TASK_KEY,newTask)
            setResult(RESULT_OK,intent)
            finish()
        }
    }
}
