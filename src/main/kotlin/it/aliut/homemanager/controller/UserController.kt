package it.aliut.homemanager.controller

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import it.aliut.homemanager.exception.InvalidDataException
import it.aliut.homemanager.exception.ResourceNotFoundException
import it.aliut.homemanager.model.User
import it.aliut.homemanager.repository.UserRepository
import it.aliut.homemanager.response.ApiResponse
import org.koin.ktor.ext.inject

fun Routing.users() {
    val userRepository: UserRepository by inject()

    route("/users") {
        post {
            val user = call.receive<User>()

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