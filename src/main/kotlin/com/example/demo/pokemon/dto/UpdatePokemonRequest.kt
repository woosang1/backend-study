package com.example.demo.pokemon.dto

data class UpdatePokemonRequest(
    val name: String,
    val type: String,
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val speed: Int,
    val imageUrl: String? = null,
    val description: String? = null
)