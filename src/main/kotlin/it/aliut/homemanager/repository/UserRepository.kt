package it.aliut.homemanager.repository

import it.aliut.homemanager.model.User
import org.litote.kmongo.coroutine.CoroutineDatabase

class UserRepository(mongoDatabase: CoroutineDatabase) {

    private val usersCollection = mongoDatabase.getCollection<User>("users")

    suspend fun getById(id: String): User? = usersCollection.findOneById(id)

    suspend fun add(user: User): User {
        usersCollection.insertOne(user)
        return user
    }
}