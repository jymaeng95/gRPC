package com.zayson.grpc.client.service

import com.zayson.grpc.client.config.GrpcClientConfig
import com.zayson.grpc.client.dto.ApiRegisterDto
import com.zayson.grpc.client.proto.ApiRegisterServiceGrpc
import com.zayson.grpc.client.proto.RegisterRequest
import com.zayson.grpc.client.proto.RegisterRequestKt
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class GrpcSender(private val client: GrpcClientConfig) {
    private val logger = KotlinLogging.logger { }
    private val stub = ApiRegisterServiceGrpc.newBlockingStub(client.grpcClient())

    /**
     * Api 실제로 호출하기
     */
    fun send(apiRegisterDto: ApiRegisterDto) =
        // stub을 통해서 호출하기
        stub.registerApiKey(createRegisterRequest(apiRegisterDto))


    /**
     * REST를 통해 받은 Api 등록 객체 받아서 protobuf 타입으로 변경해주기
     */
    private fun createRegisterRequest(apiRegisterDto: ApiRegisterDto): RegisterRequest = RegisterRequest.newBuilder()
        .setKey(apiRegisterDto.key)
        .setId(apiRegisterDto.id)
        .setNickname(apiRegisterDto.nickname)
        .build()
}