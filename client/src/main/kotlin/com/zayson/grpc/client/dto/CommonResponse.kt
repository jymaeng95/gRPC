package com.zayson.grpc.client.dto

import org.springframework.web.bind.annotation.ResponseBody

class CommonResponse private constructor(val message: String) {
    companion object {
        fun create(message: String) = CommonResponse(message)
    }
}
