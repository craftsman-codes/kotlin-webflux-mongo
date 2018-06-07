package test.web

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Flux
import test.User
import test.formatDate
import java.time.LocalDate

@Suppress("UNUSED_PARAMETER")
class UserHandler {

  private val users = Flux.just(
      User("Foo", "Foo", LocalDate.now().minusDays(1)),
      User("Bar", "Bar", LocalDate.now().minusDays(10)),
      User("Baz", "Baz", LocalDate.now().minusDays(100)))

  fun findAll(req: ServerRequest) =
      ok().body(users)

  fun findAllView(req: ServerRequest) =
      ok().render("users", mapOf("users" to users.map { it.toDto() }))
}

class UserDto(val firstName: String, val lastName: String, val birthDate: String)

fun User.toDto() = UserDto(firstName, lastName, birthDate.formatDate())
