package it.aliut.homemanager.di

import it.aliut.homemanager.repository.UserRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val repositoryModule = module {
    single { UserRepository(get()) }
}

val databaseModule = module {
    single {
        val host = get<String>(named("mongoHost"))
        val port = get<String>(named("mongoPort"))
        KMongo.createClient("mongodb://$host:$port").coroutine.getDatabase("home-manager-users")
    }

    single(named("mongoHost")) { System.getenv("MONGO_HOST") ?: "localhost" }
    single(named("mongoPort")) { System.getenv("MONGO_PORT") ?: "27017" }
}

val modules = listOf(repositoryModule, databaseModule)