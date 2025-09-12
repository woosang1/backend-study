package com.example.demo.member.repository

import com.example.demo.member.model.Member1
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository2 : JpaRepository<Member1, Long>