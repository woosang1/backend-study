//package member.repository
//
//import member.model.Member
//import org.springframework.stereotype.Repository
//import java.util.concurrent.atomic.AtomicLong
//
//@Repository
//class MemberRepository {
//    private val members = mutableListOf<Member>()
//    private val idGenerator = AtomicLong(1)
//
//    fun findAll(): List<Member> = members.toList()
//
//    fun findAll(): List<Member> = members.toList()
//
//    fun findById(id: Long): Member? = members.find { it.id == id }
//
//    fun save(member: Member): Member {
//        val newTodo = member.copy(id = idGenerator.getAndIncrement())
//        members.add(newTodo)
//        return newTodo
//    }
//
//    fun update(id: Long, updatedMember: Member): Member? {
//        val index = members.indexOfFirst { it.id == id }
//        if (index != -1) {
//            members[index] = updatedMember.copy(id = id)
//            return members[index]
//        }
//        return null
//    }
//
//    fun deleteById(id: Long): Boolean {
//        return members.removeIf { it.id == id }
//    }
//}