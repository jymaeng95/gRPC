package com.zayson.grpc.server.service

import com.zayson.grpc.server.proto.ApiRegisterServiceGrpc;
import com.zayson.grpc.server.proto.RegisterRequest
import com.zayson.grpc.server.proto.RegisterResponse
import io.grpc.stub.StreamObserver
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
// /build/generated/.../grpc 하위의 클래스를 import해서 구현
class ApiRegisterService : ApiRegisterServiceGrpc.ApiRegisterServiceImplBase() {

    // 로깅
    private val logger = KotlinLogging.logger {  }

    /**
     * 실제 gRPC를 통해서 호출되는 함수 (proto 파일에 정의한 service 명과 동일)
     * 함수를 오버라이딩해서 실제 서비스를 구현
     */
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