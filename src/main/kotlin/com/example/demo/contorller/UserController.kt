package com.example.demo.contorller

import com.example.demo.model.Todo
import com.example.demo.service.TodoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController  // 2025: Kotlin DSL 지원으로 MockMvc 테스트 쉬움
class TodoController(private val service: TodoService) {

    @GetMapping("/hello")
    fun getHello(): String {
        return "이우상 테스트 hello~~! 이제 TODO API도 추가됐어요!"
    }

    @GetMapping("/todos")
    fun getAllTodos(): List<Todo> = service.getAllTodos()

    @GetMapping("/todos/{id}")
    fun getTodoById(@PathVariable id: Long): ResponseEntity<Todo> {
        val todo = service.getTodoById(id)
        return if (todo != null) ResponseEntity.ok(todo) else ResponseEntity.notFound().build()
    }

    @PostMapping("/todos")
    fun createTodo(@RequestBody todo: Todo): Todo {
        return service.createTodo(todo.title)  // title만 사용 (간단히)
    }

    @PutMapping("/todos/{id}")
    fun updateTodo(
        @PathVariable id: Long,
        @RequestBody todo: Todo
    ): ResponseEntity<Todo> {
        val updated = service.updateTodo(id, todo.title, todo.completed)
        return if (updated != null) ResponseEntity.ok(updated) else ResponseEntity.notFound().build()
    }

    @DeleteMapping("/todos/{id}")
    fun deleteTodo(@PathVariable id: Long): ResponseEntity<Void> {
        val deleted = service.deleteTodo(id)
        return if (deleted) ResponseEntity.ok().build() else ResponseEntity.notFound().build()
    }
}