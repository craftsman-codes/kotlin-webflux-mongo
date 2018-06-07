package test.web

import com.samskivert.mustache.Mustache
import org.springframework.context.MessageSource
import org.springframework.core.io.ClassPathResource
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RenderingResponse
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.toMono
import test.locale
import java.util.*

class Routes(private val userHandler: UserHandler,
             private val messageSource: MessageSource) {
  fun router() = router {
    accept(MediaType.TEXT_HTML).nest {
      GET("/") { ServerResponse.ok().render("index") }
      GET("/users", userHandler::findAllView)
    }
    "/api".nest {
      accept(MediaType.APPLICATION_JSON).nest {
        GET("/users", userHandler::findAll)
      }
    }
    resources("/**", ClassPathResource("static/"))
  }.filter { request, next ->
    next.handle(request).flatMap {
      if (it is RenderingResponse) RenderingResponse.from(it).modelAttributes(attributes(request.locale(), messageSource)).build() else it.toMono()
    }
  }

  private fun attributes(locale: Locale, messageSource: MessageSource) = mutableMapOf<String, Any>(
      "i18n" to Mustache.Lambda { frag, out ->
        val tokens = frag.execute().split("|")
        out.write(messageSource.getMessage(tokens[0], tokens.slice(IntRange(1, tokens.size - 1)).toTypedArray(), locale)) })
}