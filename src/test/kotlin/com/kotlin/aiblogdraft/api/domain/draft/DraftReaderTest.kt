package com.kotlin.aiblogdraft.api.domain.draft

import com.kotlin.aiblogdraft.storage.db.entity.DraftEntity
import com.kotlin.aiblogdraft.storage.db.enum.DraftEntityType
import com.kotlin.aiblogdraft.storage.db.repository.DraftRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class DraftReaderTest(
    private val draftRepository: DraftRepository,
    private val draftReader: DraftReader,
) : BehaviorSpec({
        afterEach {
            draftRepository.deleteAll()
        }

        given("유저의 초안 조회 시") {
            val draft1 = draftRepository.save(DraftEntity(DraftEntityType.RESTAURANT, "title1", 1L))
            val draft2 = draftRepository.save(DraftEntity(DraftEntityType.RESTAURANT, "title2", 1L))
            When("정상적인 요청이면") {
                val draftsByUserId = draftReader.readAllByUserId(1L)
                then("등록된 초안을 모두 반환한다") {
                    draftsByUserId.size shouldBe 2
                    draftsByUserId.forEach {
                        when (it.id) {
                            draft1.id -> it.title shouldBe "title1"
                            draft2.id -> it.title shouldBe "title2"
                        }
                    }
                }
            }
        }
    })
