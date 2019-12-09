package it.aliut.homemanager.repository

import it.aliut.homemanager.model.User
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class UserRepository(mongoDatabase: CoroutineDatabase) {

    private val usersCollection = mongoDatabase.getCollection<User>("users")

    suspend fun getById(id: String): User? = usersCollection.findOneById(id)

    suspend fun add(user: User): User {
        usersCollection.insertOne(user)
        return user
    }

    suspend fun getByName(name: String): User? = usersCollection.findOne(User::name eq name)
}