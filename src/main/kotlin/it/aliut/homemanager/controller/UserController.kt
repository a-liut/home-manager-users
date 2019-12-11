package it.aliut.homemanager.controller

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receiveOrNull
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import it.aliut.homemanager.exception.InvalidDataException
import it.aliut.homemanager.exception.ResourceNotFoundException
import it.aliut.homemanager.parameters.CreateUserParameters
import it.aliut.homemanager.repository.UserRepository
import it.aliut.homemanager.response.ApiResponse
import it.aliut.homemanager.usecase.GetSingleUserUseCase
import it.aliut.homemanager.usecase.GetUsersUseCase
import it.aliut.homemanager.usecase.RegisterUserUseCase
import org.koin.ktor.ext.inject

fun Routing.users() {
    val userRepository: UserRepository by inject()

    route("/users") {
        get {
            val useCase = GetUsersUseCase(userRepository)
            val list = useCase.start()

            call.respond(HttpStatusCode.OK, ApiResponse(list, null))
        }

        post {
            val params = call.receiveOrNull<CreateUserParameters>() ?: throw InvalidDataException("Missing parameters")

            val useCase = RegisterUserUseCase(params, userRepository)
            val user = useCase.start()

            call.respond(HttpStatusCode.Created, ApiResponse(user, null))
        }
    }

    route("/users/{id}") {
        get {
            val id = call.parameters["id"] ?: throw InvalidDataException("Missing Id")

            val useCase = GetSingleUserUseCase(id, userRepository)

            val user = useCase.start() ?: throw ResourceNotFoundException("User $id not found.")

            call.respond(HttpStatusCode.OK, ApiResponse(user, null))
        }
    }
}