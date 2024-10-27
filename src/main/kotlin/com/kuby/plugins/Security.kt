package com.kuby.plugins

import com.kuby.domain.model.ApiResponseError
import com.kuby.service.JwtService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*

fun Application.configureSecurity(
    jwtService: JwtService
) {
    authentication {
        jwt {
            realm = jwtService.realm
            verifier(jwtService.jwtVerifier)
            validate { credential ->
                jwtService.customValidator(credential)
            }
        }

        jwt("another-auth") {
            realm = jwtService.realm
            verifier(jwtService.jwtVerifier)

            validate { credential ->
                jwtService.customValidator(credential)
            }
            challenge { _, _ ->
                call.respond(
                    status = HttpStatusCode.Unauthorized,
                    message = ApiResponseError(
                        statusCode = HttpStatusCode.Unauthorized.value,
                        message = "Autenticación denegada"
                    )
                )
            }
        }

    }
}

