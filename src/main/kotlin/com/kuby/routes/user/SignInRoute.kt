package com.kuby.routes.user

import com.kuby.domain.model.ApiResponse
import com.kuby.domain.model.ApiResponseError
import com.kuby.domain.model.EndPoint
import com.kuby.domain.model.LoginRequest
import com.kuby.domain.repository.UserDataSource
import com.kuby.service.JwtService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.signInRoute(
    jwtService: JwtService,
    userDataSource: UserDataSource
){
    post(EndPoint.SignIn.path) {
        try {
            // Intentar recibir el JSON y convertirlo en LoginRequest
            val loginRequest = call.receive<LoginRequest>()

            val foundUser = userDataSource.getUserInfoByEmail(loginRequest.emailAddress)
            if (foundUser != null) {
                val token: String? = jwtService.createJwtToken(loginRequest)
                token?.let {
                    call.respond(
                        status = HttpStatusCode.OK,
                        message = ApiResponse(
                            user = foundUser,
                            token = token
                        )
                    )
                } ?: call.respond(
                    status = HttpStatusCode.Forbidden,
                    message = ApiResponseError(
                        statusCode = 403,
                        message = "password Error"
                    )
                )
            } else {
                call.respond(
                    status = HttpStatusCode.Forbidden,
                    message = ApiResponseError(
                        statusCode = 403,
                        message = "Email Error"
                    )
                )
            }
        } catch (e: ContentTransformationException) {
            call.respond(
                status = HttpStatusCode.BadRequest,
                message = ApiResponseError(
                    statusCode = 400,
                    message = "Invalid JSON format: ${e.localizedMessage}"
                )
            )
        } catch (e: Exception) {
            // Manejo general de errores
            call.respond(
                status = HttpStatusCode.InternalServerError,
                message = ApiResponseError(
                    statusCode = 500,
                    message = "An unexpected error occurred"
                )
            )
        }
    }
}
