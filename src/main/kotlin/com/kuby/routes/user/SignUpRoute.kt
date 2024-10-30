package com.kuby.routes.user

import com.kuby.domain.model.*
import com.kuby.domain.user.model.LoginRequest
import com.kuby.domain.user.model.User
import com.kuby.domain.user.repository.UserDataSource
import com.kuby.service.JwtService
import com.kuby.util.enviarCorreoPersonalizado
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*
import java.time.LocalDateTime
import java.util.*

fun Route.signUpRoute (
    jwtService: JwtService,
    userDataSource: UserDataSource
){
    post (EndPoint.SignUp.path){
        try {
            val userRequest = call.receive<User>()

            saveUserToDatabase(
                userRequest,
                jwtService,
                userDataSource
            )

        } catch (e: Exception) {
            call.respond(
                status = HttpStatusCode.Forbidden,
                message = ApiResponseError(
                    statusCode = 403,
                    message = "Error al registrar usuario"
                )
            )
        }
    }
}

private fun User.toModel(): User =
    User(
        id = UUID.randomUUID().toString(),
        emailAddress = this.emailAddress,
        name = this.name,
        lastName = this.lastName,
        phone = this.phone,
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now(),
        password = this.password,
        idRol = this.idRol
    )

private suspend fun PipelineContext<Unit, ApplicationCall>.saveUserToDatabase(
    userRequest: User,
    jwtService: JwtService,
    userDataSource: UserDataSource
) {
    val user = userRequest.toModel()
    val response = userDataSource.saveUserInfo(user)
    val foundUser = user.emailAddress.let { userDataSource.getUserInfoByEmail(it) }
    val tokenId = user.emailAddress.let {
        user.password?.let { it1 ->
            LoginRequest(
                emailAddress = it,
                password = it1
            )
        }
    }

    try {
        val destinatario = user.emailAddress
        val asunto = "¡Bienvenido a la familia ASTEC!"
        val plantillaRuta = "src/main/kotlin/com/kuby/resources/templates/correo_plantilla.html"

        val variables = mapOf(
            "titulo" to "Bienvenido",
            "nombreCliente" to "${user.name} ${user.lastName}",
            "mensaje" to "Los roles y permisos por defecto se han inicializado correctamente.",
            "fecha" to user.createdAt.toString()
        )

        enviarCorreoPersonalizado(destinatario, asunto, plantillaRuta, variables)
    } catch (e: Exception) {
        e.printStackTrace()
        println("Error al enviar el correo de inicialización.")
    }

    return if (response && tokenId != null) {
        val token: String? = jwtService.createJwtToken(tokenId)
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
                message = "Email ya esta en uso."
            )
        )
    }
}