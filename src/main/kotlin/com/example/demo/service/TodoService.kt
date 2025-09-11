package com.example.demo.service

import com.example.demo.repository.TodoRepository
import com.example.demo.model.Todo
import org.springframework.stereotype.Service

@Service
class TodoService(private val repository: TodoRepository) {

    fun getAllTodos(): List<Todo> = repository.findAll()

    fun getTodoById(id: Long): Todo? = repository.findById(id)

    fun createTodo(title: String): Todo = repository.save(Todo(title = title))

    fun updateTodo(id: Long, title: String, completed: Boolean): Todo? =
        repository.findById(id)?.let { todo ->
            repository.update(id, todo.copy(title = title, completed = completed))
        }

    fun deleteTodo(id: Long): Boolean = repository.deleteById(id)
}