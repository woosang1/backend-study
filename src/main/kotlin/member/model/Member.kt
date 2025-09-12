package member.model

data class Member(
    val id: Long? = null,
    val title: String,
    val completed: Boolean = false
)