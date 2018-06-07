package test.web

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Flux
import test.User
import test.UserRepository
import test.formatDate
import java.time.LocalDate

@Suppress("UNUSED_PARAMETER")
class UserHandler(private val repository: UserRepository) {


  fun findAll(req: ServerRequest) =
      ok().body(repository.findAll())

  fun findAllView(req: ServerRequest) =
      ok().render("users", mapOf("users" to repository.findAll().map { it.toDto() }))
}

class UserDto(val firstName: String, val lastName: String, val birthDate: String)

fun User.toDto() = UserDto(firstName, lastName, birthDate.formatDate())
