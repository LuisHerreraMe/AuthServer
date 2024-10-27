package com.kuby.routes.empresa

import com.kuby.domain.empresa.model.Empresa
import com.kuby.domain.empresa.model.UpdateEmpresa
import com.kuby.domain.empresa.repocitory.EmpresaDataSource
import com.kuby.domain.model.ApiResponse
import com.kuby.domain.model.ApiResponseError
import com.kuby.service.JwtService
import com.kuby.util.Permissions
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*

fun Route.getEmpresaByNameRoute(
    empresaDataSource: EmpresaDataSource
) {
    authenticate("another-auth"){
        get("/{id}"){
            try {
                if (extractPrincipalUsername(call)?.let { it1 -> Permissions(it1, "CREATION_SERVICES") } == true){
                    val id: String = call.parameters["id"].toString()
                    val empresas = empresaDataSource.getEmpresaByName(id)
                    call.respond(
                        status = HttpStatusCode.OK,
                        message = empresas
                    )
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