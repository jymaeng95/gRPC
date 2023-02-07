package com.zayson.grpc.client.config

import io.grpc.ManagedChannelBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GrpcClientConfig {

    // Client 설정 알아보기 (주로 사용하는 함수 알아보기)
    @Bean
    fun grpcClient() = ManagedChannelBuilder
        .forAddress("localhost",9000)
        .usePlaintext()
        .build()!!
}