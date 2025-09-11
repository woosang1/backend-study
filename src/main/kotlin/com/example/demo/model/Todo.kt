package com.example.demo.model

data class Todo(
    val id: Long? = null,
    val title: String,
    val completed: Boolean = false
)