package com.kotlin.aiblogdraft.api.domain.draft

import com.kotlin.aiblogdraft.api.exception.DraftIsNotDone
import com.kotlin.aiblogdraft.api.exception.DraftNotFoundException
import com.kotlin.aiblogdraft.storage.db.entity.DraftEntity
import com.kotlin.aiblogdraft.storage.db.enum.DraftEntityType
import com.kotlin.aiblogdraft.storage.db.repository.DraftRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class DraftReaderTest(
    private val draftRepository: DraftRepository,
    private val draftReader: DraftReader,
) : BehaviorSpec({
        afterEach { draftRepository.deleteAll() }

        given("유저의 초안 조회 시") {
            val draft1 = draftRepository.save(DraftEntity("key1", DraftEntityType.RESTAURANT, "title1", 1L))
            val draft2 = draftRepository.save(DraftEntity("key2", DraftEntityType.RESTAURANT, "title2", 1L))
            When("정상적인 요청이면") {
                val draftsByUserId = draftReader.readByUserId(1L)
                then("등록된 초안을 모두 반환한다") {
                    draftsByUserId.size shouldBe 2
                    draftsByUserId.forEach {
                        when (it.key) {
                            "key1" -> it.title shouldBe "title1"
                            "key2" -> it.title shouldBe "title2"
                        }
                    }
                }
            }
        }

        given("식별자로 초안을 조회할 때") {
            When("존재하지 않는 초안이면") {
                then("존재하지 않는 초안입니다 예외가 발생한다.") {
                    shouldThrow<DraftNotFoundException> {
                        draftReader.readById(1)
                    }
                }
            }

            val draft = draftRepository.save(DraftEntity("key1", DraftEntityType.RESTAURANT, "title1", 1L))
            When("존재하는 초안이면") {
                val draftById = draftReader.readById(draft.id)
                then("초안 엔티티를 반환한다.") {
                    draftById.key shouldBe "key1"
                    draftById.type shouldBe DraftEntityType.RESTAURANT
                    draftById.title shouldBe "title1"
                    draftById.userId shouldBe 1L
                }
            }
        }

        given("본문 조회 시") {
            val draft1 = draftRepository.save(DraftEntity("key1", DraftEntityType.RESTAURANT, "title1", 1L))
            When("처리되지 않은 상태라면") {
                then("완료되지 않은 초안 예외가 발생한다.") {
                    shouldThrow<DraftIsNotDone> {
                        draftReader.readDoneById(draft1.id)
                    }
                }
            }

            val draft2 = draftRepository.save(DraftEntity("key1", DraftEntityType.RESTAURANT, "title1", 1L))
            draft2.writeContent("test content")
            draftRepository.save(draft2)
            When("본문 작성이 완료된 초안이면") {
                val done = draftReader.readDoneById(draft2.id)
                then("초안 엔티티를 반환한다.") {
                    done.id shouldBe draft2.id
                }
            }
        }
    })
