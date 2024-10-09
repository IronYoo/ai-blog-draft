package com.kotlin.aiblogdraft.api.domain.draft

import com.kotlin.aiblogdraft.api.domain.draft.dto.AppendDraft
import com.kotlin.aiblogdraft.api.domain.draft.dto.DraftType
import com.kotlin.aiblogdraft.storage.db.entity.DraftImageGroupEntity
import com.kotlin.aiblogdraft.storage.db.entity.DraftTempEntity
import com.kotlin.aiblogdraft.storage.db.repository.DraftImageGroupRepository
import com.kotlin.aiblogdraft.storage.db.repository.DraftRepository
import com.kotlin.aiblogdraft.storage.db.repository.DraftTempRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull

@SpringBootTest
class DraftAppenderTest(
    private val draftAppender: DraftAppender,
    private val draftTempRepository: DraftTempRepository,
    private val draftRepository: DraftRepository,
    private val draftImageGroupRepository: DraftImageGroupRepository,
) : BehaviorSpec({
        afterEach {
            draftTempRepository.deleteAll()
            draftRepository.deleteAll()
        }

        given("초안 추가 시") {
            val temp =
                draftTempRepository.save(
                    DraftTempEntity(
                        1L,
                    ),
                )
            val appendDraft =
                AppendDraft(
                    type = DraftType.RESTAURANT,
                    title = "test-title",
                )
            When("정상적인 요청이면") {
                draftImageGroupRepository.save(DraftImageGroupEntity(1L))
                val draft = draftAppender.append(temp.id, 1L, appendDraft)
                val findTemp = draftTempRepository.findByIdOrNull(draft.id)

                then("임시 초안을 삭제하고 초안을 추가한다") {
                    findTemp shouldBe null
                    draft.type shouldBe DraftType.RESTAURANT.draftEntityType
                    draft.title shouldBe "test-title"
                }
            }
        }
    })
