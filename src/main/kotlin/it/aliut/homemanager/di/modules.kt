package it.aliut.homemanager.di

import it.aliut.homemanager.repository.UserRepository
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val repositoryModule = module {
    single { UserRepository(get()) }
}

val databaseModule = module {
    single { KMongo.createClient("mongodb://mongo:27017").coroutine.getDatabase("home-manager-users") }
}

val modules = listOf(repositoryModule, databaseModule)