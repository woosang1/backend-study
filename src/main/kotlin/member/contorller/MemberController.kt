package member.contorller

import member.dto.CreateMemberRequest
import member.dto.UpdateMemberRequest
import member.model.Member1
import member.service.MemberService
import org.springframework.web.bind.annotation.*

@RestController  // 2025: Kotlin DSL 지원으로 MockMvc 테스트 쉬움
class MemberController(private val service: MemberService) {

    @GetMapping("/hello")
    fun getHello(): String {
        return "이우상 테스트 hello~~! 이제 TODO API도 추가됐어요!"
    }

    @PostMapping
    fun create(@RequestBody request: CreateMemberRequest): Member1 {
        return service.create(request.name, request.email)
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): Member1 {
        return service.findById(id)
    }

    @GetMapping
    fun getAll(): List<Member1> {
        return service.getAll()
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody request: UpdateMemberRequest): Member1? {
        return service.update(id, request.name, request.email)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): Boolean {
        return service.delete(id)
    }
}