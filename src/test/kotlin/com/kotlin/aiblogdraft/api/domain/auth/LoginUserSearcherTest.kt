package com.kotlin.aiblogdraft.api.domain.auth

import com.kotlin.aiblogdraft.api.domain.auth.dto.Login
import com.kotlin.aiblogdraft.api.exception.LoginFailException
import com.kotlin.aiblogdraft.storage.db.entity.UserEntity
import com.kotlin.aiblogdraft.storage.db.repository.UserRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class LoginUserSearcherTest(
    private val userRepository: UserRepository,
    private val userAppender: UserAppender,
    private val loginUserSearcher: LoginUserSearcher,
) : BehaviorSpec({
        afterEach {
            userRepository.deleteAll()
        }

        given("사용자 로그인을 시도할 때") {
            When("사용자가 존재하지 않으면") {
                then("로그인 실패 예외가 발생한다") {
                    shouldThrow<LoginFailException> {
                        loginUserSearcher.search(Login("email", "password"))
                    }
                }
            }

            When("비밀번호가 잘못되면") {
                val user = UserEntity("name", "abc1234", "qwe@email.com")
                userAppender.append(user)
                then("로그인 실패 예외가 발생한다") {
                    shouldThrow<LoginFailException> {
                        loginUserSearcher.search(Login("qwe@email.com", "wrong_password"))
                    }
                }
            }

            When("정상적인 요청이면") {
                val user = UserEntity("name", "abc1234", "qwe@email.com")
                userAppender.append(user)
                val foundUser = loginUserSearcher.search(Login("qwe@email.com", "abc1234"))
                then("사용자 정보를 반환한다") {
                    foundUser.id shouldBe user.id
                }
            }
        }
    })
