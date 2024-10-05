package com.kotlin.aiblogdraft.api.domain.draft

import com.kotlin.aiblogdraft.api.exception.DraftNotFoundException
import com.kotlin.aiblogdraft.storage.db.entity.DraftEntity
import com.kotlin.aiblogdraft.storage.db.entity.DraftImageEntity
import com.kotlin.aiblogdraft.storage.db.entity.DraftImageGroupEntity
import com.kotlin.aiblogdraft.storage.db.enum.DraftEntityType
import com.kotlin.aiblogdraft.storage.db.repository.DraftImageGroupRepository
import com.kotlin.aiblogdraft.storage.db.repository.DraftImageRepository
import com.kotlin.aiblogdraft.storage.db.repository.DraftRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class DraftFinderTest(
    private val draftRepository: DraftRepository,
    private val draftImageGroupRepository: DraftImageGroupRepository,
    private val draftImageRepository: DraftImageRepository,
    private val draftFinder: DraftFinder,
) : BehaviorSpec({
        afterEach {
            draftRepository.deleteAll()
            draftImageGroupRepository.deleteAll()
            draftImageRepository.deleteAll()
        }

        given("식별자로 초안을 조회할 때") {
            When("존재하지 않는 초안이면") {
                then("존재하지 않는 초안입니다 예외가 발생한다.") {
                    shouldThrow<DraftNotFoundException> {
                        draftFinder.findDetail(1L, 1L)
                    }
                }
            }

            val draft = draftRepository.save(DraftEntity(DraftEntityType.RESTAURANT, "title1", 1L))
            val draftImageGroup = DraftImageGroupEntity(1L)
            draftImageGroup.updateDraftId(draft.id)
            val imageGroup = draftImageGroupRepository.save(draftImageGroup)
            draftImageRepository.save(DraftImageEntity("test-url", imageGroup.id))
            When("존재하는 초안이면") {
                val result = draftFinder.findDetail(draft.id, 1L)
                then("이미지와 함께 초안 데이터를 반환한다") {
                    result.draft.type shouldBe DraftEntityType.RESTAURANT
                    result.draft.title shouldBe "title1"
                    result.groups.size shouldBe 1
                    result.groups[0].images.size shouldBe 1
                    result.groups[0].images[0].url shouldBe "test-url"
                }
            }
        }
    })
