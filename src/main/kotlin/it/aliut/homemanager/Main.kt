package it.aliut.homemanager

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.gson.gson
import io.ktor.routing.routing
import it.aliut.homemanager.controller.users
import it.aliut.homemanager.di.modules
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
}

fun Application.routes() {
    routing {
        users()
    }
}