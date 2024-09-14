package com.kotlin.aiblogdraft.api.domain.draftImage

import com.kotlin.aiblogdraft.storage.db.entity.DraftImageEntity
import com.kotlin.aiblogdraft.storage.db.entity.DraftImageGroupEntity
import com.kotlin.aiblogdraft.storage.db.repository.DraftImageGroupRepository
import com.kotlin.aiblogdraft.storage.db.repository.DraftImageRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class DraftImageFinderTest(
    private val draftImageFinder: DraftImageFinder,
    private val draftImageGroupRepository: DraftImageGroupRepository,
    private val draftImageRepository: DraftImageRepository,
) : BehaviorSpec({
        afterEach {
            draftImageRepository.deleteAll()
            draftImageGroupRepository.deleteAll()
        }

        given("이미지 그룹 조회 시") {
            val group1 = draftImageGroupRepository.save(DraftImageGroupEntity("test-key"))
            val group2 = draftImageGroupRepository.save(DraftImageGroupEntity("test-key"))
            val image1 = draftImageRepository.save(DraftImageEntity("url1", group1.id))
            val image2 = draftImageRepository.save(DraftImageEntity("url2", group1.id))
            val image3 = draftImageRepository.save(DraftImageEntity("url3", group2.id))

            When("정상적인 요청이면") {
                val imageGroups = draftImageFinder.findImageGroups("test-key")
                then("이미지를 그룹으로 묶어서 생성순으로 반환한다.") {
                    imageGroups.size shouldBe 2
                    imageGroups[0].images.size shouldBe 2
                    imageGroups[0].images.map { it.url }.contains("url1") shouldBe true
                    imageGroups[0].images.map { it.url }.contains("url2") shouldBe true
                    imageGroups[1].images.size shouldBe 1
                    imageGroups[1].images.map { it.url }.contains("url3") shouldBe true
                }
            }
        }
    })
