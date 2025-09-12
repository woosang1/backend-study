package com.example.demo

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@Disabled("이 클래스의 모든 테스트를 일시적으로 비활성화")
@SpringBootTest
class DemoApplicationTests {

    @Disabled("테스트를 일시적으로 비활성화")
    @Test
    fun contextLoads() {
    }
}