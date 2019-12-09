package it.aliut.homemanager.controller

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import it.aliut.homemanager.model.User
import it.aliut.homemanager.repository.UserRepository
import org.koin.ktor.ext.inject

fun Routing.users() {
    val userRepository: UserRepository by inject()

    route("/users") {
        post {
            val user = call.receive<User>()

            val newUser = userRepository.add(user)

            call.respond(HttpStatusCode.OK, newUser)
        }
    }

    route("/users/{id}") {
        get {
            val id = call.parameters["id"]

            if (id.isNullOrEmpty()) {
                call.respond(HttpStatusCode.BadRequest, "Invalid id")
                return@get
            }

            val user = userRepository.getById(id)

            if (user != null) {
                call.respond(user)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }
    }
}