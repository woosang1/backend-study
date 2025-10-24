package com.example.demo.pokemon.controller

import com.example.demo.pokemon.dto.CreatePokemonRequest
import com.example.demo.pokemon.dto.UpdatePokemonRequest
import com.example.demo.pokemon.model.Pokemon
import com.example.demo.pokemon.service.PokemonService
import com.example.demo.pokemon.service.PokeApiListResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/pokemon")
class PokemonController(private val service: PokemonService) {

    @GetMapping("/hello")
    fun getHello(): String {
        return "포켓몬 API 서버가 실행 중입니다! 🎮"
    }

    // 모든 포켓몬 조회
    @GetMapping
    fun getAllPokemons(): ResponseEntity<List<Pokemon>> {
        return ResponseEntity.ok(service.getAllPokemons())
    }

    // ID로 포켓몬 조회
    @GetMapping("/{id}")
    fun getPokemonById(@PathVariable id: Long): ResponseEntity<Pokemon> {
        return try {
            ResponseEntity.ok(service.findById(id))
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }

    // 이름으로 포켓몬 조회
    @GetMapping("/name/{name}")
    fun getPokemonByName(@PathVariable name: String): ResponseEntity<Pokemon> {
        val pokemon = service.findByName(name)
        return if (pokemon != null) {
            ResponseEntity.ok(pokemon)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    // 포켓몬 생성
    @PostMapping
    fun createPokemon(@RequestBody request: CreatePokemonRequest): ResponseEntity<Pokemon> {
        val pokemon = Pokemon(
            name = request.name,
            type = request.type,
            hp = request.hp,
            attack = request.attack,
            defense = request.defense,
            speed = request.speed,
            imageUrl = request.imageUrl,
            description = request.description
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createPokemon(pokemon))
    }

    // 포켓몬 업데이트
    @PutMapping("/{id}")
    fun updatePokemon(@PathVariable id: Long, @RequestBody request: UpdatePokemonRequest): ResponseEntity<Pokemon> {
        val pokemon = Pokemon(
            name = request.name,
            type = request.type,
            hp = request.hp,
            attack = request.attack,
            defense = request.defense,
            speed = request.speed,
            imageUrl = request.imageUrl,
            description = request.description
        )
        val updatedPokemon = service.updatePokemon(id, pokemon)
        return if (updatedPokemon != null) {
            ResponseEntity.ok(updatedPokemon)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    // 포켓몬 삭제
    @DeleteMapping("/{id}")
    fun deletePokemon(@PathVariable id: Long): ResponseEntity<Void> {
        return if (service.deletePokemon(id)) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }

    // PokeAPI에서 포켓몬 정보 가져오기 (비동기)
    @GetMapping("/api/{name}")
    fun fetchPokemonFromApi(@PathVariable name: String): Mono<ResponseEntity<Pokemon>> {
        return service.fetchPokemonFromApi(name.lowercase())
            .map { pokemon -> ResponseEntity.ok(pokemon) }
            .onErrorReturn(ResponseEntity.notFound().build())
    }

    // PokeAPI에서 포켓몬 정보를 가져와서 DB에 저장
    @PostMapping("/api/{name}")
    fun fetchAndSavePokemon(@PathVariable name: String): ResponseEntity<String> {
        return try {
            val pokemon = service.fetchAndSavePokemon(name.lowercase()).get()
            ResponseEntity.ok("포켓몬 '${pokemon.name}'이(가) 성공적으로 저장되었습니다!")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body("포켓몬을 가져오는 중 오류가 발생했습니다: ${e.message}")
        }
    }

    // PokeAPI에서 포켓몬 목록 조회
    @GetMapping("/api/list")
    fun fetchPokemonList(
        @RequestParam(defaultValue = "20") limit: Int,
        @RequestParam(defaultValue = "0") offset: Int
    ): Mono<ResponseEntity<PokeApiListResponse>> {
        return service.fetchPokemonList(limit, offset)
            .map { response -> ResponseEntity.ok(response) }
            .onErrorReturn(ResponseEntity.badRequest().build())
    }
}