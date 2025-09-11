package com.example.demo.contorller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController {

    @GetMapping("/hello")
    fun getHello(): String {
        return "이우상 테스트 hello~~!"
    }
}