package com.kotlin.aiblogdraft.api.domain

import com.kotlin.aiblogdraft.api.exception.DraftKeyNotAllowedException
import com.kotlin.aiblogdraft.storage.db.entity.DraftKeyEntity
import com.kotlin.aiblogdraft.storage.db.repository.DraftKeyRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class DraftKeyFinderTest(
    private val draftKeyFinder: DraftKeyFinder,
    private val draftKeyRepository: DraftKeyRepository,
) : BehaviorSpec({
        afterEach {
            draftKeyRepository.deleteAll()
        }

        given("초안 키 조회 시") {
            When("존재하지 않는 키라면") {
                then("예외가 발생한다") {
                    shouldThrow<DraftKeyNotFoundException> {
                        draftKeyFinder.getValidDraftKey("not-found-key", 1L)
                    }
                }
            }

            When("해당 유저가 생성한 초안 키가 아니라면") {
                draftKeyRepository.save(DraftKeyEntity("test-key", 2L))
                then("사용할 수 없는 초안 키 예외가 발생한다") {
                    shouldThrow<DraftKeyNotAllowedException> {
                        draftKeyFinder.getValidDraftKey("test-key", 1L)
                    }
                }
            }

            When("정상적인 요청이면") {
                draftKeyRepository.save(DraftKeyEntity("test-key", 1L))
                then("초안 키를 반한환다 ") {
                    val draftKey = draftKeyFinder.getValidDraftKey("test-key", 1L)

                    draftKey shouldNotBe null
                    draftKey.key shouldBe "test-key"
                    draftKey.userId shouldBe 1L
                }
            }
        }
    })
