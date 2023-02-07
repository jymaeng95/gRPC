package com.zayson.grpc.server

import com.zayson.grpc.server.service.GrpcServer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import kotlin.concurrent.thread

@SpringBootApplication
class ServerApplication (private val grpcServer: GrpcServer){
    init {
        thread {
            grpcServer.start()
            grpcServer.callApiRegister()
        }
    }
}

fun main(args: Array<String>) {
    runApplication<ServerApplication>(*args)

}
