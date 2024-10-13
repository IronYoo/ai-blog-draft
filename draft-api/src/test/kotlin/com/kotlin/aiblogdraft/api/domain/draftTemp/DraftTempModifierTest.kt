package com.kotlin.aiblogdraft.api.domain.draftTemp

import com.kotlin.aiblogdraft.api.domain.draft.temp.DraftTempModifier
import com.kotlin.aiblogdraft.api.exception.DraftTempNotAllowedException
import com.kotlin.aiblogdraft.api.exception.DraftTempNotFoundException
import com.kotlin.aiblogdraft.storage.db.entity.DraftTempEntity
import com.kotlin.aiblogdraft.storage.db.repository.DraftTempRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDateTime

@SpringBootTest
class DraftTempModifierTest
    constructor(
        private val draftTempRepository: DraftTempRepository,
        private val draftTempModifier: DraftTempModifier,
    ) : BehaviorSpec({

            afterEach {
                draftTempRepository.deleteAll()
            }

            given("임시 초안의 자동 삭제 시간을 연장할 때") {

                When("존재하지 않는 임시 초안 ID가 주어지면") {
                    then("찾을 수 없는 임시 초안 예외가 발생한다") {
                        shouldThrow<DraftTempNotFoundException> {
                            draftTempModifier.postponeRemove(1L, 999L) // 존재하지 않는 초안 ID
                        }
                    }
                }

                When("유저의 임시 초안이 아니면") {
                    val draftTemp =
                        draftTempRepository.save(
                            DraftTempEntity(
                                2L,
                            ),
                        ) // 다른 유저의 초안
                    then("임시 초안 접급 불가 예외가 발생한다") {
                        shouldThrow<DraftTempNotAllowedException> {
                            draftTempModifier.postponeRemove(1L, draftTemp.id) // 다른 유저의 초안 ID 사용
                        }
                    }
                }

                When("정상적인 요청이면") {
                    val draftTemp =
                        draftTempRepository.save(
                            DraftTempEntity(
                                1L,
                            ),
                        )
                    val expectExpireAt =
                        LocalDateTime
                            .now()
                            .plusDays(7)
                            .toLocalDate()
                            .atStartOfDay()
                            .plusDays(1)
                    draftTempModifier.postponeRemove(1L, draftTemp.id)

                    then("자동 삭제 시간이 7일 연장된다") {
                        val updatedDraftTemp = draftTempRepository.findByIdOrNull(draftTemp.id)!!
                        updatedDraftTemp.expireAt shouldBe expectExpireAt
                    }
                }
            }
        })
