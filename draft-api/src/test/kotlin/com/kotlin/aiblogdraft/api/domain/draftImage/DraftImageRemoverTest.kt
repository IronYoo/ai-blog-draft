package com.kotlin.aiblogdraft.api.domain.draftImage

import com.kotlin.aiblogdraft.api.exception.DraftImageNotAllowedException
import com.kotlin.aiblogdraft.storage.db.entity.DraftImageEntity
import com.kotlin.aiblogdraft.storage.db.entity.DraftImageGroupEntity
import com.kotlin.aiblogdraft.storage.db.entity.DraftTempEntity
import com.kotlin.aiblogdraft.storage.db.repository.DraftImageGroupRepository
import com.kotlin.aiblogdraft.storage.db.repository.DraftImageRepository
import com.kotlin.aiblogdraft.storage.db.repository.DraftTempRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class DraftImageRemoverTest(
    private val draftImageRemover: DraftImageRemover,
    private val draftTempRepository: DraftTempRepository,
    private val draftImageRepository: DraftImageRepository,
    private val draftImageGroupRepository: DraftImageGroupRepository,
) : BehaviorSpec({
        afterEach {
            draftImageRepository.deleteAll()
            draftImageGroupRepository.deleteAll()
        }

        given("임시 이미지 삭제 시") {
            var temp: DraftTempEntity
            var group: DraftImageGroupEntity
            var deleteImage: DraftImageEntity

            temp =
                draftTempRepository.save(
                    DraftTempEntity(
                        1L,
                    ),
                )
            group = draftImageGroupRepository.save(DraftImageGroupEntity(temp.id))
            deleteImage = draftImageRepository.save(DraftImageEntity("test-url", group.id))
            When("유저 정보가 다르면") {
                then("접근할 수 없는 이미지 예외가 발생한다") {
                    shouldThrow<DraftImageNotAllowedException> {
                        draftImageRemover.remove(deleteImage.id, 2L)
                    }
                }
            }

            temp =
                draftTempRepository.save(
                    DraftTempEntity(
                        1L,
                    ),
                )
            group = draftImageGroupRepository.save(DraftImageGroupEntity(temp.id))
            draftImageRepository.save(DraftImageEntity("test-url", group.id))
            deleteImage = draftImageRepository.save(DraftImageEntity("test-url2", group.id))
            When("해당 그룹에 삭제 대상외의 이미지가 있다면") {
                val deleteImageId = deleteImage.id
                draftImageRemover.remove(deleteImageId, 1L)
                val groupById = draftImageGroupRepository.findById(group.id).get()
                val image = draftImageRepository.findById(deleteImageId)
                then("이미지 그룹은 삭제하지 않고 삭제 대상 이미지만 삭제한다") {
                    groupById shouldNotBe null
                    image.isEmpty shouldBe true
                }
            }

            temp =
                draftTempRepository.save(
                    DraftTempEntity(
                        1L,
                    ),
                )
            group = draftImageGroupRepository.save(DraftImageGroupEntity(temp.id))
            deleteImage = draftImageRepository.save(DraftImageEntity("test-url", group.id))
            When("해당 그룹에 삭제 대상만 있다면") {
                val deleteImageId = deleteImage.id
                draftImageRemover.remove(deleteImageId, 1L)
                val groupById = draftImageGroupRepository.findById(group.id)
                val image = draftImageRepository.findById(deleteImageId)

                then("이미지와 함께 그룹도 삭제한다") {
                    groupById.isEmpty shouldBe true
                    image.isEmpty shouldBe true
                }
            }
        }
    })
