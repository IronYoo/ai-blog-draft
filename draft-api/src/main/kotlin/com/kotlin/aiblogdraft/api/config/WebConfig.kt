package com.kotlin.aiblogdraft.api.config

import com.kotlin.aiblogdraft.api.common.UserOrGuestArgumentResolver
import com.kotlin.aiblogdraft.api.common.WebUserArgumentResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    private val webUserArgumentResolver: WebUserArgumentResolver,
    private val userArgumentResolver: UserOrGuestArgumentResolver,
) : WebMvcConfigurer {
    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(webUserArgumentResolver)
        resolvers.add(userArgumentResolver)
    }
}
