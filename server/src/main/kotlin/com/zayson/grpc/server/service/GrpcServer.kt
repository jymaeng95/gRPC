package com.zayson.grpc.server.service

import io.grpc.Server
import io.grpc.ServerBuilder
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class GrpcServer(private val apiRegisterService: ApiRegisterService) {
    // gRPC 서버 빌더 및 서비스 등록
    private val server: Server = ServerBuilder
        .forPort(9000)
        .addService(apiRegisterService)
        .build()

    // 코틀린 로깅
    private val logger = KotlinLogging.logger {  }

    /**
     * ServerApplication이 시작될 때 init 블록을 통해서 gRPC서버 자동 구동
     */
    fun start() {
        server.start()
        logger.info("gRPC Server Start When Application is started")
    }

    /**
     * gRPC로 작성한 서비스 실제 호출
     */
    fun callApiRegister() {
        server.awaitTermination()
        logger.info("gRPC Server call function success")
    }
}