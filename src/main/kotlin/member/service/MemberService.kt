package member.service

import MemberRepository2
import member.model.Member1
import org.springframework.stereotype.Service

@Service
class MemberService(private val repository: MemberRepository2) {

    // ID로 회원 조회 (필수 반환)
    fun findById(id: Long): Member1 = repository.findById(id).orElseThrow {
        NoSuchElementException("Member with id $id not found")
    }

    // 모든 회원 조회
    fun getAll(): List<Member1> = repository.findAll()

    // 회원 생성
    fun create(name: String, email: String): Member1 = repository.save(Member1(name = name, email = email))

    // 회원 업데이트
    fun update(id: Long, name: String, email: String): Member1? =
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