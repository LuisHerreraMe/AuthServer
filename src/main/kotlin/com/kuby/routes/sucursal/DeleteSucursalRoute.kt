package com.kuby.routes.sucursal

import com.kuby.domain.empresa.model.Empresa
import com.kuby.domain.empresa.model.UpdateEmpresa
import com.kuby.domain.empresa.repocitory.EmpresaDataSource
import com.kuby.domain.model.ApiResponse
import com.kuby.domain.model.ApiResponseError
import com.kuby.domain.sucursal.repocitory.SucursalDataSource
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

fun Route.deleteSucursalRoute(
    sucursalDataSource: SucursalDataSource
) {
    authenticate("another-auth") {
        delete("/{id}") {
            val id: String = call.parameters["id"]
                ?: return@delete call.respond(
                    status = HttpStatusCode.NotFound,
                    message = "credenciales nulas"
                )

            try {
                if (extractPrincipalUsername(call)?.let { it1 -> Permissions(it1, "CREATION_SERVICES") } == true) {
                    val deleted = sucursalDataSource.deleteSucursal(id)
                    if (deleted) {
                        call.respond(
                            status = HttpStatusCode.OK,
                            message = ApiResponseError(
                                statusCode = HttpStatusCode.OK.value,
                                message = "Empresa eliminada exitosamente"
                            )
                        )
                    } else {
                        call.respond(
                            status = HttpStatusCode.InternalServerError,
                            message = ApiResponseError(
                                statusCode = HttpStatusCode.InternalServerError.value,
                                message = "Error al eliminar la Sucursal"
                            )
                        )
                    }
                } else {
                    call.respond(
                        status = HttpStatusCode.Forbidden,
                        message = ApiResponseError(
                            statusCode = HttpStatusCode.Forbidden.value,
                            message = "Privilegios no válidos"
                        )
                    )
                }

            } catch (e: Exception) {
                call.respond(
                    status = HttpStatusCode.InternalServerError,
                    message = ApiResponseError(
                        statusCode = HttpStatusCode.InternalServerError.value,
                        message = "Error al procesar la solicitud de eliminación de Empresa"
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