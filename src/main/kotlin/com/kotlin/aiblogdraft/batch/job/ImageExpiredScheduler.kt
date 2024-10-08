package com.kotlin.aiblogdraft.batch.job

import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled

@Configuration
@EnableScheduling
class ImageExpiredScheduler(
    private val imageExpiredRunner: ImageExpiredRunner,
) {
    @Scheduled(cron = "0 0 * * * *") // 매 정시에 실행
    fun runDeleteExpiredImagesJob() {
        imageExpiredRunner.runJob()
    }
}
