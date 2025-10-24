package com.example.demo.pokemon.service

import com.example.demo.pokemon.model.Pokemon
import com.example.demo.pokemon.repository.PokemonRepository
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.util.concurrent.CompletableFuture

@Service
class PokemonService(
    private val repository: PokemonRepository,
    private val webClient: WebClient
) {

    // 모든 포켓몬 조회
    fun getAllPokemons(): List<Pokemon> = repository.findAll()

    // ID로 포켓몬 조회
    fun findById(id: Long): Pokemon = repository.findById(id).orElseThrow {
        NoSuchElementException("Pokemon with id $id not found")
    }

    // 이름으로 포켓몬 조회
    fun findByName(name: String): Pokemon? = repository.findByNameIgnoreCase(name)

    // 포켓몬 생성
    fun createPokemon(pokemon: Pokemon): Pokemon = repository.save(pokemon)

    // 포켓몬 업데이트
    fun updatePokemon(id: Long, pokemon: Pokemon): Pokemon? =
        repository.findById(id).orElse(null)?.let { existingPokemon ->
            repository.save(pokemon.copy(id = id))
        }

    // 포켓몬 삭제
    fun deletePokemon(id: Long): Boolean {
        return if (repository.existsById(id)) {
            repository.deleteById(id)
            true
        } else {
            false
        }
    }

    // PokeAPI에서 포켓몬 정보 가져오기
    fun fetchPokemonFromApi(name: String): Mono<Pokemon> {
        return webClient.get()
            .uri("/pokemon/$name")
            .retrieve()
            .bodyToMono(PokeApiResponse::class.java)
            .map { response ->
                Pokemon(
                    name = response.name.replaceFirstChar { it.uppercase() },
                    type = response.types.firstOrNull()?.type?.name ?: "Unknown",
                    hp = response.stats.find { it.stat.name == "hp" }?.baseStat ?: 0,
                    attack = response.stats.find { it.stat.name == "attack" }?.baseStat ?: 0,
                    defense = response.stats.find { it.stat.name == "defense" }?.baseStat ?: 0,
                    speed = response.stats.find { it.stat.name == "speed" }?.baseStat ?: 0,
                    imageUrl = response.sprites.frontDefault,
                    description = "Pokemon from PokeAPI"
                )
            }
    }

    // PokeAPI에서 포켓몬 정보를 가져와서 DB에 저장
    fun fetchAndSavePokemon(name: String): CompletableFuture<Pokemon> {
        return fetchPokemonFromApi(name)
            .map { pokemon ->
                repository.save(pokemon)
            }
            .toFuture()
    }

    // 포켓몬 목록 조회 (PokeAPI)
    fun fetchPokemonList(limit: Int = 20, offset: Int = 0): Mono<PokeApiListResponse> {
        return webClient.get()
            .uri("/pokemon?limit=$limit&offset=$offset")
            .retrieve()
            .bodyToMono(PokeApiListResponse::class.java)
    }
}

// PokeAPI 응답 DTO들
data class PokeApiResponse(
    val name: String,
    val types: List<PokemonType>,
    val stats: List<PokemonStat>,
    val sprites: PokemonSprites
)

data class PokemonType(
    val type: TypeInfo
)

data class TypeInfo(
    val name: String
)

data class PokemonStat(
    val stat: StatInfo,
    val baseStat: Int
)

data class StatInfo(
    val name: String
)

data class PokemonSprites(
    val frontDefault: String?
)

data class PokeApiListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonListItem>
)

data class PokemonListItem(
    val name: String,
    val url: String
)