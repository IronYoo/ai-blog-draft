package com.kotlin.aiblogdraft.api.domain

import com.kotlin.aiblogdraft.api.image.S3UploaderStub
import com.kotlin.aiblogdraft.storage.db.TransactionHandler
import com.kotlin.aiblogdraft.storage.db.repository.DraftImageGroupRepository
import com.kotlin.aiblogdraft.storage.db.repository.DraftImageRepository
import com.kotlin.aiblogdraft.storage.db.repository.DraftKeyRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.multipart.MultipartFile

@SpringBootTest
class DraftImageAppenderTest(
    private val draftKeyRepository: DraftKeyRepository,
    private val draftImageGroupRepository: DraftImageGroupRepository,
    private val draftImageRepository: DraftImageRepository,
    private val transactionHandler: TransactionHandler = mockk(relaxed = true),
) : BehaviorSpec({
        afterEach {
            draftImageRepository.deleteAll()
            draftImageGroupRepository.deleteAll()
        }

        given("이미지 업로드 요청 시") {
            val s3Uploader = S3UploaderStub()
            val sut =
                DraftImageAppender(
                    draftImageGroupRepository,
                    draftImageRepository,
                    s3Uploader,
                    transactionHandler,
                )
            val files = arrayOf(mockk<MultipartFile>(), mockk<MultipartFile>())
            When("정상적인 요청이면") {
                val result = sut.appendImages("test-key", files)
                then("요청한 파일 수 만큼 이미지 url을 저장한다.") {
                    val images = draftImageRepository.findAll()
                    images.size shouldBe 2
                }
            }
        }
    })
