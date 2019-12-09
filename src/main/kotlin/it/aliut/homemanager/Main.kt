package it.aliut.homemanager

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.features.StatusPages
import io.ktor.gson.gson
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.routing
import it.aliut.homemanager.controller.users
import it.aliut.homemanager.di.modules
import it.aliut.homemanager.exception.InvalidDataException
import it.aliut.homemanager.exception.ResourceNotFoundException
import it.aliut.homemanager.response.ApiResponse
import org.koin.core.context.startKoin
import java.text.DateFormat

fun Application.main() {
    startKoin {
        modules(modules)
    }

    install(DefaultHeaders)
    install(CallLogging)
    install(ContentNegotiation) {
        gson {
            setDateFormat(DateFormat.LONG)
            setPrettyPrinting()
        }
    }
    install(StatusPages) {
        exception<Throwable> { cause ->
            call.respond(HttpStatusCode.InternalServerError, ApiResponse(null, cause.message))
            throw cause
        }

        exception<ResourceNotFoundException> { cause ->
            call.respond(HttpStatusCode.NotFound, ApiResponse(null, cause.message))
        }

        exception<InvalidDataException> { cause ->
            call.respond(HttpStatusCode.BadRequest, ApiResponse(null, cause.message))
        }
    }
}

fun Application.routes() {
    routing {
        users()
    }
}