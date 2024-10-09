package com.kotlin.aiblogdraft.api.domain.auth

import com.kotlin.aiblogdraft.api.exception.EmailDuplicationException
import com.kotlin.storage.db.entity.UserEntity
import com.kotlin.storage.db.repository.UserRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserAppenderTest(
    private val userAppender: UserAppender,
    private val userRepository: UserRepository,
) : BehaviorSpec({
        afterEach {
            userRepository.deleteAll()
        }

        given("사용자 추가 시") {
            When("중복된 이메일이 존재할 경우") {
                userRepository.save(UserEntity(username = "exist", email = "test@example.com", password = "password123"))

                then("이메일 중복 예외가 발생한다") {
                    val newUser = UserEntity(username = "name", email = "test@example.com", password = "password123")
                    shouldThrow<EmailDuplicationException> {
                        userAppender.append(newUser)
                    }
                }
            }

            When("새로운 사용자인 경우") {
                userAppender.append(UserEntity(username = "name", email = "test@example.com", password = "password123"))
                then("사용자가 성공적으로 추가된다") {
                    val savedUser = userRepository.findByEmail("test@example.com")
                    savedUser shouldNotBe null
                    savedUser?.email shouldBe "test@example.com"
                }
            }
        }
    })
