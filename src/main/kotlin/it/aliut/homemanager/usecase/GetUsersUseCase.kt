package it.aliut.homemanager.usecase

import it.aliut.homemanager.model.User
import it.aliut.homemanager.repository.UserRepository

class GetUsersUseCase(private val userRepository: UserRepository) : UseCase<List<User>>() {

    override suspend fun run(): List<User> {
        return userRepository.getAll()
    }
}