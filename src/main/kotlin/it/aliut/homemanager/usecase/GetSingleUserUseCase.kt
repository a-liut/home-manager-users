package it.aliut.homemanager.usecase

import it.aliut.homemanager.exception.InvalidDataException
import it.aliut.homemanager.exception.ResourceNotFoundException
import it.aliut.homemanager.model.User
import it.aliut.homemanager.repository.UserRepository

class GetSingleUserUseCase(private val id: String, private val userRepository: UserRepository) : UseCase<User?>() {

    override suspend fun run(): User? {
        if (id.isEmpty()) {
            throw InvalidDataException("Invalid Id")
        }

        return userRepository.getById(id)
    }
}