package com.kotlin.aiblogdraft.api.domain.ai

import com.kotlin.aiblogdraft.api.domain.draft.dto.DraftAppendEvent
import com.kotlin.aiblogdraft.api.domain.draft.dto.DraftType
import com.kotlin.aiblogdraft.api.exception.DraftNotFoundException
import com.kotlin.aiblogdraft.storage.db.entity.DraftContentEntity
import com.kotlin.aiblogdraft.storage.db.repository.DraftContentRepository
import com.kotlin.aiblogdraft.storage.db.repository.DraftRepository
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.messages.UserMessage
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.ai.model.Media
import org.springframework.ai.openai.OpenAiChatOptions
import org.springframework.ai.openai.api.OpenAiApi
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import java.net.URL

@Component
class AiDraftGenerator(
    private val chatClient: ChatClient,
    private val draftRepository: DraftRepository,
    private val draftContentRepository: DraftContentRepository,
) {
    @Async
    @EventListener
    fun generate(event: DraftAppendEvent) {
        val draftWithImageGroups = draftRepository.findByIdWithImageGroups(event.draftId) ?: throw DraftNotFoundException()
        val draft = draftWithImageGroups.draft

        draft.process()
        draftRepository.save(draft)

        val draftType = DraftType.findByEntityType(draft.type)
        val prompt =
            """
            너는 지금부터 ${draftType.description}에 대한 글을 쓰는 전문 블로거야.

            너는 항상 최선을 다하고 좋은 글을 작성해서 나를 기쁘게 해주고 있어.

            ${draft.title} 블로그 포스트를 작성해줘.

            포스트는 다음 구조를 따라야 해.

            - 사진을 분석해서 ${draftType.description}을 소개하는 글을 작성한다.
            - ${draftType.description}을 직접 언급하지 않는다. 단지 참고만 할 뿐이다.
            - 음식 사진이 있다면 개인적인 느낌을 묘사해서 글에 포함시킨다.
            - 작성하는 글에는 중복되는 내용이 없도록 한다.
            - “~다” 로 끝나는 말투대신 “~요” 로 끝나는 말투를 주로 사용한다.
            - 번호를 쓰꺼나 줄 바꿈을 해서 문장을 구별짓는 것은 하면 안된다.
            - 여러장의 사진을 받았을 때, 한 사진으로 합쳐져 있다고 생각하며 글을 작성한다.
            - 절대로 글을 시작하는 느낌과 끝맺는 느낌을 주면 안된다. 
            - 너가 직접 경험한 듯 작성한다.

            10가지 예시를 줄게

            1.
            저희가 주문한 마늘통닭 순살이에요!
            위에 마늘이 한가득 올라가있죠?
            칠곡3지구치킨 닭동가리는
            치킨크기가 많이 크지도 작지도 않아 좋았고
            눈으로봐도 바삭한게 느껴졌어요
            순살 후라이드를 먹은 적이 있었는데
            바삭함이 극강이었거든요ㅜㅜ
            제 최애 치킨집이에요

            2.
            저희는 마늘통닭 순살, 어묵우동 을 주문했어요!
            마늘통닭은 마늘양을 기본/많이 중 하나 선택할 수 있어 좋았는데 저희는 기본으로 했어요
            칠곡3지구치킨 닭동가리는 기본안주로 치킨무, 샐러드, 콩나물국이 제공되었어요
            샐러드는 새콤달콤하고 콩나물국은 시원해 닭발처럼 매운걸 먹을때 딱 좋을것 같았어요!

            3.
            마늘소스가 잔뜩 발려져있는데도
            치킨의 바삭함이 잘 느껴졌고
            속은 부드러워 좋았어요
            무엇보다 치킨이 알싸한게 마늘향이 제대로 느껴졌고
            소스가 달달해 맛있었어요!
            마늘통닭이 완전 제 취향저격이라 앞으로 닭동가리오면 마늘통닭만 먹게될것 같아요ㅋㅋㅋㅋ칠곡3지구술집 닭동가리의 우동도 정말 좋아하는데 면발이 탱글하고 국물이 맛있어 소주가 술술들어가는 맛이에요

            4.
            특히 곳곳의 디자인들이 매우 눈에 들어 왔는데요
            아기자기한 것들을 보니 사장님께서 엄청 신경 쓰신 것이 보입니다
            그리고 간단한 것들은 따로 바에 준비되어 있습니다
            
            5. 
            저희 동네도 수제버거가 전쟁 중인데요
            그 중 분위기나 간판 등 매력적인 곳이 있다고 해서 다녀왔습니다
            송도유원지 맛집 버거치즈스마일입니다

            6.
            일단 로고가 매우 매력적이잖아요?
            버거치즈스마일! 사실 이 글을 쓰기 위해 다녀온 것 말고도 몇 번 더 다녀왔는데요
            그만큼 저에겐 송도유원지 맛집이 된 곳 중 하니입니다
            치즈X3이라는 건 치즈치즈치즈 버거라는 뜻입니다!
            저렇게 쟁반처럼 생긴 곳에 나오거든요

            7.
            가격 보이시나요? 어떻게 이 가격에 고기가 제공이 될지요?
            미박삼겹살 13,000원 / 특목살 13,000원인데요
            저희는 미박삼겹살 3인분에 특목살 1인분
            그리고 물냉면과 비빔면을 주문했습니다!!
            
            8.
            주문하면 고기가 나오는데요
            가장 먼저 비계로 솥뚜껑을 닦아 주십니다!!!
            아주 깔끔하죠!
            그리고 등장한 고기!!! 너무 좋아요!
            
            9.
            목살도 아주 두툼하고 좋은데요
            삼겹살은 말로 할 것도 없구요!
            역시 배곧맛집으로 이끈 숙성이고 나발이고 고기가 좋으면 끝입니다!!
            고기를 진짜 잘 구워주시는데요
            먹기 좋게 잘라 주십니다!!!
            
            10.
            비빔면은 정말 양이 많습니다!!
            그냥 먹어도 좋은데, 쌈이랑 싸먹는 걸 좋아하구요
            고기랑만 먹어도 진짜 너무 잘 어울리는 조합이라 생각합니다!!
            
            ---

            여기까지 내가 너에게 주는 예시야.
            이제 내가 보내준 이미지를 가지고 글을 작성해줘.
            
            글은 2문장에서 4문장 사이가 좋아.
            """.trimIndent()

        val imageGroups = draftWithImageGroups.groups
        imageGroups.forEach { group ->
            val userMessage =
                UserMessage(
                    prompt,
                    group.images.map { Media(it.imgType, URL(it.url)) },
                )
            val response =
                chatClient
                    .prompt(
                        Prompt(userMessage, OpenAiChatOptions.builder().withModel(OpenAiApi.ChatModel.GPT_4_O).build()),
                    ).call()
                    .content()

            draftContentRepository.save(DraftContentEntity(response, group.id))
            draft.done()

            draftRepository.save(draft)
        }
    }
}
