package it.aliut.homemanager.repository

import it.aliut.homemanager.model.User
import org.bson.types.ObjectId
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.id.toId

class UserRepository(mongoDatabase: CoroutineDatabase) {

    private val usersCollection = mongoDatabase.getCollection<User>("users")

    suspend fun getById(id: String): User? = usersCollection.findOneById(ObjectId(id).toId<User>())

    suspend fun add(user: User) {
        usersCollection.insertOne(user)
    }
}