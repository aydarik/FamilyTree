package ru.gumerbaev.family.account.controller

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class ErrorHandler {

    private val log = LoggerFactory.getLogger(javaClass)

    @Suppress("unused")
    class ErrorEntity(val error: String, val error_description: String?)

    @ExceptionHandler(IllegalArgumentException::class,
            MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun processValidationError(e: IllegalArgumentException): ErrorEntity {
        log.info("Handling error: {}, {}", e.javaClass.simpleName, e.message)
        return ErrorEntity("account_exists", e.message)
    }
}
