package com.example.demo.repository

import com.example.demo.model.Todo
import org.springframework.stereotype.Repository
import java.util.concurrent.atomic.AtomicLong

@Repository
class TodoRepository {
    private val todos = mutableListOf<Todo>()
    private val idGenerator = AtomicLong(1)

    fun findAll(): List<Todo> = todos.toList()

    fun findById(id: Long): Todo? = todos.find { it.id == id }

    fun save(todo: Todo): Todo {
        val newTodo = todo.copy(id = idGenerator.getAndIncrement())
        todos.add(newTodo)
        return newTodo
    }

    fun update(id: Long, updatedTodo: Todo): Todo? {
        val index = todos.indexOfFirst { it.id == id }
        if (index != -1) {
            todos[index] = updatedTodo.copy(id = id)
            return todos[index]
        }
        return null
    }

    fun deleteById(id: Long): Boolean {
        return todos.removeIf { it.id == id }
    }
}