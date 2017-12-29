package ru.gumerbaev.family.ethereum.controller

import feign.FeignException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import java.io.IOException

@ControllerAdvice
class ErrorHandler {

    private val log = LoggerFactory.getLogger(javaClass)

    @Suppress("unused")
    class ErrorEntity(val error: String, val error_description: String?)

    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun processValidationError(e: IllegalArgumentException): ErrorEntity {
        log.info("Handling error: {}, {}", e.javaClass.simpleName, e.message)
        return ErrorEntity("validation_error", e.message)
    }

    @ExceptionHandler(FeignException::class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ResponseBody
    fun processFeignException(e: FeignException): ErrorEntity {
        log.info("Handling error: {}, {}", e.javaClass.simpleName, e.message)
        return ErrorEntity("feign_exception", e.message)
    }

    @ExceptionHandler(IOException::class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ResponseBody
    fun processServiceUnavailableException(e: IOException): ErrorEntity {
        log.info("Handling error: {}, {}", e.javaClass.simpleName, e.message)
        return ErrorEntity("exception", e.message)
    }
}
