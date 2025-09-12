package member.contorller

import member.model.Member
import member.service.MemberService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController  // 2025: Kotlin DSL 지원으로 MockMvc 테스트 쉬움
class MemberController(private val service: MemberService) {

    @GetMapping("/hello")
    fun getHello(): String {
        return "이우상 테스트 hello~~! 이제 TODO API도 추가됐어요!"
    }

    @GetMapping("/todos")
    fun getAllTodos(): List<Member> = service.getAllTodos()

    @GetMapping("/todos/{id}")
    fun getTodoById(@PathVariable id: Long): ResponseEntity<Member> {
        val todo = service.getTodoById(id)
        return if (todo != null) ResponseEntity.ok(todo) else ResponseEntity.notFound().build()
    }

    @PostMapping("/todos")
    fun createTodo(@RequestBody member: Member): Member {
        return service.createTodo(member.title)  // title만 사용 (간단히)
    }

    @PutMapping("/todos/{id}")
    fun updateTodo(
        @PathVariable id: Long,
        @RequestBody member: Member
    ): ResponseEntity<Member> {
        val updated = service.updateTodo(id, member.title, member.completed)
        return if (updated != null) ResponseEntity.ok(updated) else ResponseEntity.notFound().build()
    }

    @DeleteMapping("/todos/{id}")
    fun deleteTodo(@PathVariable id: Long): ResponseEntity<Void> {
        val deleted = service.deleteTodo(id)
        return if (deleted) ResponseEntity.ok().build() else ResponseEntity.notFound().build()
    }
}