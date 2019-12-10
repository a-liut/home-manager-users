package it.aliut.homemanager.usecase

abstract class UseCase<ResultType> {

    abstract suspend fun run(): ResultType

    suspend fun start(): ResultType {
        println(this.javaClass.simpleName + " started.")
        try {
            return run()
        } finally {
            println(this.javaClass.simpleName + " ended.")
        }
    }
}