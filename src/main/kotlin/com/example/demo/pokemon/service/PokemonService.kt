package com.example.demo.pokemon.service

import com.example.demo.member.model.Member
import com.example.demo.pokemon.repository.PokemonRepository
import org.springframework.stereotype.Service

@Service
class PokemonService(private val repository: PokemonRepository) {

    // ID로 회원 조회 (필수 반환)
    fun findById(id: Long): Member = repository.findById(id).orElseThrow {
        NoSuchElementException("Member with id $id not found")
    }

    // 모든 회원 조회
    fun getAll(): List<Member> = repository.findAll()

    // 회원 생성
    fun create(name: String, email: String): Member = repository.save(Member(name = name, email = email))

    // 회원 업데이트
    fun update(id: Long, name: String, email: String): Member? =
        repository.findById(id).orElse(null)?.let { member ->
            repository.save(member.copy(name = name, email = email))
        }

    // 회원 삭제
    fun delete(id: Long): Boolean {
        return if (repository.existsById(id)) {
            repository.deleteById(id)
            true
        } else {
            false
        }
    }
}