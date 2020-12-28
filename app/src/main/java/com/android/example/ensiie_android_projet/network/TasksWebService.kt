package com.android.example.ensiie_android_projet.network

import com.android.example.ensiie_android_projet.LoginForm
import com.android.example.ensiie_android_projet.LoginResponse
import com.android.example.ensiie_android_projet.tasklist.Task
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface TasksWebService {
    @GET("tasks")
    suspend fun getTasks(): Response<List<Task>>

    @DELETE("tasks/{id}")
    suspend fun deleteTask(@Path("id") id: String?): Response<Unit>

    @POST("tasks")
    suspend fun createTask(@Body task: Task): Response<Task>

    @PATCH("tasks/{id}")
    suspend fun updateTask(@Body task: Task, @Path("id") id: String? = task.id): Response<Task>
}