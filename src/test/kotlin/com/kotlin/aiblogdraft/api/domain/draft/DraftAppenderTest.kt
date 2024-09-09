package com.kotlin.aiblogdraft.api.domain.draft

import com.kotlin.aiblogdraft.storage.db.entity.DraftKeyEntity
import com.kotlin.aiblogdraft.storage.db.repository.DraftKeyRepository
import com.kotlin.aiblogdraft.storage.db.repository.DraftRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull

@SpringBootTest
class DraftAppenderTest(
    private val draftAppender: DraftAppender,
    private val draftKeyRepository: DraftKeyRepository,
    private val draftRepository: DraftRepository,
) : BehaviorSpec({
        afterEach {
            draftKeyRepository.deleteAll()
            draftRepository.deleteAll()
        }

        given("초안 추가 시") {
            draftKeyRepository.save(DraftKeyEntity("test-key", 1L))
            val appendDraft =
                AppendDraft(
                    key = "test-key",
                    type = DraftType.RESTAURANT,
                    title = "test-title",
                )
            When("정상적인 요청이면") {
                val draft = draftAppender.append(appendDraft)
                val draftKey = draftKeyRepository.findByIdOrNull("test-key")

                then("키를 삭제하고 초안을 추가한다") {
                    draftKey shouldBe null
                    draft.key shouldBe "test-key"
                    draft.type shouldBe DraftType.RESTAURANT.draftEntityType
                    draft.title shouldBe "test-title"
                }
            }
        }
    })
