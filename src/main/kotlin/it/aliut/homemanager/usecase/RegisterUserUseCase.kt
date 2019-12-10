package it.aliut.homemanager.usecase

import it.aliut.homemanager.exception.InvalidDataException
import it.aliut.homemanager.model.User
import it.aliut.homemanager.parameters.CreateUserParameters
import it.aliut.homemanager.repository.UserRepository

class RegisterUserUseCase(private val parameters: CreateUserParameters, private val userRepository: UserRepository) :
    UseCase<User>() {

    override suspend fun run(): User {
        parameters.name ?: throw InvalidDataException("Missing name")

        if (userRepository.getByName(parameters.name) != null) {
            throw InvalidDataException("User ${parameters.name} already exists")
        }

        val user = User(
            name = parameters.name,
            email = parameters.email
        )

        return userRepository.add(user)
    }

}