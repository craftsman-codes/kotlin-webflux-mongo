package test.web

import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

class Routes(private val userHandler: UserHandler) {
  fun router() = router {
    "/api".nest {
      accept(MediaType.APPLICATION_JSON).nest {
        GET("/users", userHandler::findAll)
      }
    }
  }
}