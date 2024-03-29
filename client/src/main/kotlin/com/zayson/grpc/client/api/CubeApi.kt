package com.zayson.grpc.client.api

import com.zayson.grpc.client.dto.ApiRegisterDto
import com.zayson.grpc.client.dto.CommonResponse
import com.zayson.grpc.client.service.GrpcSender
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class CubeApi(private val grpcSender: GrpcSender) {

    // 코틀린 logger
    private val logger = KotlinLogging.logger {}

    @PostMapping("/new")
    fun registerUser(@RequestBody apiRegisterDto: ApiRegisterDto): ResponseEntity<CommonResponse> {
        logger.info("Register User Information : ${apiRegisterDto.toString()}")

        // gRPC Server로 protobuf를 이용해 전달하기
        val response = grpcSender.send(apiRegisterDto)
        // 서버의 응답 저장해서 메세지로 리턴하기

        logger.info("Response : ${response.message}")
        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.create(response.message))
    }
}