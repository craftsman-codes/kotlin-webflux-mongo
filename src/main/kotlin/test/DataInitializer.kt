package test

import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import java.time.LocalDate


class DataInitializr(val users: UserRepository) {
  private val log = LoggerFactory.getLogger(DataInitializr::class.java)

  fun initData() {
    log.info("start data initialization ...")

    users
        .deleteAll()
        .thenMany(
            Flux
                .just(
                    User("Foo", "Foo", LocalDate.now().minusDays(1)),
                    User("Bar", "Bar", LocalDate.now().minusDays(10)),
                    User("Baz", "Baz", LocalDate.now().minusDays(100))
                )
                .flatMap(users::save)
        )
        .log()
        .subscribe(
            null,
            null,
            { log.info("done users initialization...") }
        )
  }
}