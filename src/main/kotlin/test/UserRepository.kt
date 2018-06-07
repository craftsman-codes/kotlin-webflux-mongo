package test

import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Query

class UserRepository(val template: ReactiveMongoTemplate) {
    fun save(user: User) = template.save(user)
    fun deleteAll() = template.remove(Query(), User::class.java)
    fun findAll() = template.findAll(User::class.java)
}