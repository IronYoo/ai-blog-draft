package com.kotlin.aiblogdraft.api.domain

import com.kotlin.aiblogdraft.storage.db.repository.DraftKeyRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class DraftKeyAppenderTest(
    private val draftKeyAppender: DraftKeyAppender,
    private val draftKeyRepository: DraftKeyRepository,
) : BehaviorSpec({
        beforeEach {
            draftKeyRepository.deleteAll()
        }

        given("초안 작성 키 발급 시") {
            When("키가 정상 발급되면") {
                val key = draftKeyAppender.appendKey(AppendDraftKey(1L))
                then("키를 반환한다.") {
                    key shouldNotBe null
                }
            }
        }
    })
