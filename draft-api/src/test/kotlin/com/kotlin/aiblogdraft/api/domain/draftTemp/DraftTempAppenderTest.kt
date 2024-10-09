package com.kotlin.aiblogdraft.api.domain.draftTemp

import com.kotlin.aiblogdraft.api.domain.draftTemp.dto.AppendDraftTemp
import com.kotlin.storage.db.repository.DraftTempRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class DraftTempAppenderTest(
    private val draftTempAppender: DraftTempAppender,
    private val draftTempRepository: DraftTempRepository,
) : BehaviorSpec({
        afterEach {
            draftTempRepository.deleteAll()
        }

        given("임시 초안 생성 시") {
            When("생성에 성공하면") {
                val id = draftTempAppender.append(AppendDraftTemp(1L))
                then("식별자를 반환한다.") {
                    id shouldNotBe null
                }
            }
        }
    })
