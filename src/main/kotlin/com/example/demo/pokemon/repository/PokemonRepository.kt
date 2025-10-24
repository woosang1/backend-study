package com.example.demo.pokemon.repository

import com.example.demo.pokemon.model.Pokemon
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PokemonRepository : JpaRepository<Pokemon, Long> {
    fun findByNameIgnoreCase(name: String): Pokemon?
}