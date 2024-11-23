package com.kuby.routes.sucursal


import com.kuby.domain.model.ApiResponseError
import com.kuby.domain.sucursal.repocitory.SucursalDataSource
import com.kuby.util.Permissions
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getSucursalByIdRoute(
    sucursalDataSource: SucursalDataSource
) {
    authenticate("another-auth"){
        get("searchId/{id}"){
            try {
                if (extractPrincipalUsername(call)?.let { it1 -> Permissions(it1, "CREATION_SERVICES") } == true){
                    val id: String = call.parameters["id"].toString()
                    val empresas = sucursalDataSource.getSucursalById(id)
                    if (empresas != null){
                        call.respond(
                            status = HttpStatusCode.OK,
                            message = empresas
                        )
                    }else{
                        call.respond(
                            status = HttpStatusCode.OK,
                            message = "no hay resultado"
                        )
                    }
                } else{
                    call.respond(
                        status = HttpStatusCode.Forbidden,
                        message = ApiResponseError(
                            statusCode = HttpStatusCode.Forbidden.value,
                            message = "Privilegios no validos"
                        )
                    )
                }

            }catch (e: Exception){
                call.respond(
                    status = HttpStatusCode.Forbidden,
                    message = ApiResponseError(
                        statusCode = HttpStatusCode.Forbidden.value,
                        message = "Error al registrar la Empresa"
                    )
                )
            }
        }
    }
}


private fun extractPrincipalUsername(call: ApplicationCall): String? =
    call.principal<JWTPrincipal>()
        ?.payload
        ?.getClaim("UserId")
        ?.asString()