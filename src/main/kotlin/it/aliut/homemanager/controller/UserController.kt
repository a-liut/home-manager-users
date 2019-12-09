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
import it.aliut.homemanager.model.User
import it.aliut.homemanager.parameters.CreateUserParameters
import it.aliut.homemanager.repository.UserRepository
import it.aliut.homemanager.response.ApiResponse
import org.koin.ktor.ext.inject

fun Routing.users() {
    val userRepository: UserRepository by inject()

    route("/users") {
        post {
            val params = call.receiveOrNull<CreateUserParameters>() ?: throw InvalidDataException("Missing parameters")

            params.name ?: throw InvalidDataException("Missing name")

            if (userRepository.getByName(params.name) != null) {
                throw InvalidDataException("User with name ${params.name} already exists")
            }

            val user = User(
                name = params.name,
                email = params.email
            )

            val newUser = userRepository.add(user)

            call.respond(HttpStatusCode.Created, ApiResponse(newUser, null))
        }
    }

    route("/users/{id}") {
        get {
            val id = call.parameters["id"]

            if (id.isNullOrEmpty()) {
                throw InvalidDataException("Invalid Id")
            }

            val user = userRepository.getById(id) ?: throw ResourceNotFoundException("User $id not found.")

            call.respond(HttpStatusCode.OK, ApiResponse(user, null))
        }
    }
}