package com.example.demo.member.controller

import com.example.demo.member.dto.CreateMemberRequest
import com.example.demo.member.dto.UpdateMemberRequest
import com.example.demo.member.model.Member
import com.example.demo.member.service.MemberService
import org.springframework.web.bind.annotation.*

@RestController  // 2025: Kotlin DSL 지원으로 MockMvc 테스트 쉬움
class MemberController(private val service: MemberService) {

    @GetMapping("/hello")
    fun getHello(): String {
        return "이우상 테스트 hello~~! 이제 TODO API도 추가됐어요!"
    }

    @PostMapping
    fun create(@RequestBody request: CreateMemberRequest): Member {
        return service.create(request.name, request.email)
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): Member {
        return service.findById(id)
    }

    @GetMapping("/all")
    fun getAll(): List<Member> {
        return service.getAll()
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody request: UpdateMemberRequest): Member? {
        return service.update(id, request.name, request.email)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): Boolean {
        return service.delete(id)
    }
}