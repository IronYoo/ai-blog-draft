package com.kotlin.aiblogdraft.api.domain.draftTemp

import com.kotlin.aiblogdraft.api.exception.DraftTempNotAllowedException
import com.kotlin.aiblogdraft.api.exception.DraftTempNotFoundException
import com.kotlin.aiblogdraft.storage.db.entity.DraftTempEntity
import com.kotlin.aiblogdraft.storage.db.repository.DraftTempRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class DraftTempFinderTest(
    private val draftTempFinder: DraftTempFinder,
    private val draftTempRepository: DraftTempRepository,
) : BehaviorSpec({
        afterEach {
            draftTempRepository.deleteAll()
        }

        given("임시 초안 조회 시") {
            When("존재하지 않으면") {
                then("존재하지 않음 예외가 발생한다") {
                    shouldThrow<DraftTempNotFoundException> {
                        draftTempFinder.getValid(1L, 1L)
                    }
                }
            }

            When("해당 유저가 생성한 임시 초안이 아니라면") {
                val temp = draftTempRepository.save(DraftTempEntity(2L))
                then("사용할 수 없는 임시 초안 예외가 발생한다") {
                    shouldThrow<DraftTempNotAllowedException> {
                        draftTempFinder.getValid(temp.id, 1L)
                    }
                }
            }

            When("정상적인 요청이면") {
                val temp = draftTempRepository.save(DraftTempEntity(1L))
                then("임시 초안을 반한환다") {
                    val findTemp = draftTempFinder.getValid(temp.id, 1L)

                    findTemp shouldNotBe null
                    findTemp.userId shouldBe 1L
                }
            }
        }
    })
