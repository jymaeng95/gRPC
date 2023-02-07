package com.zayson.grpc.server.service

import com.zayson.grpc.server.proto.ApiRegisterServiceGrpc;
import com.zayson.grpc.server.proto.RegisterRequest
import com.zayson.grpc.server.proto.RegisterResponse
import io.grpc.stub.StreamObserver
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class ApiRegisterService : ApiRegisterServiceGrpc.ApiRegisterServiceImplBase() {

    private val logger = KotlinLogging.logger {  }

    override fun registerApiKey(request: RegisterRequest?, responseObserver: StreamObserver<RegisterResponse>?) {
        logger.info("[gRPC Request] Call ApiRegisterService : ${request?.toString()}")

        // Service Layer 코드 호출
        val responseMessage = getNicknameLength(request?.nickname).let { length ->
            if(length > 5) "닉네임이 5자리를 넘어갑니다."
            else "닉네임이 5자리 이하입니다."
        }

        // 응답 작성
        val response = RegisterResponse.newBuilder().setMessage(responseMessage).build()
        responseObserver?.onNext(response)
        responseObserver?.onCompleted()

        logger.info("END OF gRPC")
    }

    private fun getNicknameLength(nickname: String?) = nickname?.length ?: 0
}