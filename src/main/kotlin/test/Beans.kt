package test

import org.springframework.context.support.beans
import org.springframework.web.reactive.function.server.RouterFunctions
import test.web.Routes
import test.web.UserHandler

fun beans() = beans {
  bean<UserHandler>()
  bean<Routes>()
  bean("webHandler") {
    RouterFunctions.toWebHandler(ref<Routes>().router())
  }
}
