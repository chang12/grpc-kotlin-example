package kr.fakenerd.grpc.kotlin.example

import io.grpc.ServerBuilder

fun main(args: Array<String>) {
    val port = 50051
    val server = ServerBuilder.forPort(port).addService(TimerService()).build().start()
    server.awaitTermination()
}
