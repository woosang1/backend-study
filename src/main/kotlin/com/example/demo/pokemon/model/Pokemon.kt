package com.example.demo.pokemon.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "pokemon")
data class Pokemon(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, length = 50)
    val name: String,

    @Column(nullable = false, length = 20)
    val type: String,

    @Column(nullable = false)
    val hp: Int,

    @Column(nullable = false)
    val attack: Int,

    @Column(nullable = false)
    val defense: Int,

    @Column(nullable = false)
    val speed: Int,

    @Column(length = 500)
    val imageUrl: String? = null,

    @Column(length = 1000)
    val description: String? = null
)