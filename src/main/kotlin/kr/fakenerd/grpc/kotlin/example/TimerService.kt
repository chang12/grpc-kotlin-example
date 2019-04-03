package kr.fakenerd.grpc.kotlin.example

import io.grpc.stub.StreamObserver
import java.time.Instant

class TimerService : TimerServiceGrpc.TimerServiceImplBase() {
    override fun sendPeriodically(request: TimerRequest, responseObserver: StreamObserver<TimerResponse>) {
        val durationSeconds = request.durationSeconds

        while (true) {
            val epochMillis = Instant.now().toEpochMilli()
            val response = TimerResponse.newBuilder().setEpochMillis(epochMillis).build()
            responseObserver.onNext(response)

            Thread.sleep(durationSeconds * 1000L)
        }
    }
}
