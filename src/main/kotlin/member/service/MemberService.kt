package member.service

import member.repository.MemberRepository
import member.model.Member
import org.springframework.stereotype.Service

@Service
class MemberService(private val repository: MemberRepository) {

    fun getAllTodos(): List<Member> = repository.findAll()

    fun getTodoById(id: Long): Member? = repository.findById(id)

    fun createTodo(title: String): Member = repository.save(Member(title = title))

    fun updateTodo(id: Long, title: String, completed: Boolean): Member? =
        repository.findById(id)?.let { todo ->
            repository.update(id, todo.copy(title = title, completed = completed))
        }

    fun deleteTodo(id: Long): Boolean = repository.deleteById(id)
}