package kr.fakenerd.grpc.kotlin.example

import io.grpc.ManagedChannelBuilder
import io.grpc.stub.StreamObserver
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

class ExampleClient(host: String, port: Int) {
    private val channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build()
    private val stub = TimerServiceGrpc.newStub(channel)

    fun print(durationSeconds: Int) {
        val request = TimerRequest.newBuilder().setDurationSeconds(durationSeconds.toLong()).build()
        val responseObserver = object: StreamObserver<TimerResponse> {
            override fun onNext(value: TimerResponse?) {
                val zdt = ZonedDateTime.ofInstant(Instant.ofEpochMilli(value!!.epochMillis), ZoneId.of("Asia/Seoul"))
                println("onNext 에서 출력: $zdt")
            }
            override fun onError(t: Throwable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onCompleted() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }

        stub.sendPeriodically(request, responseObserver)
    }
}

fun main(args: Array<String>) {
    ExampleClient("localhost", 50051).print(durationSeconds = 5)
    while (true) {
        val zdt = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
        println("main 에서 출력: $zdt")
        Thread.sleep(1000L)
    }
}
