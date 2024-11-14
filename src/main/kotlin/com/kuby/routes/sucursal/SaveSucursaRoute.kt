package com.kuby.routes.sucursal


import com.kuby.domain.model.ApiResponseError
import com.kuby.domain.sucursal.model.Sucursal
import com.kuby.domain.sucursal.repocitory.SucursalDataSource
import com.kuby.util.Permissions
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*
import java.time.LocalDateTime

fun Route.saveSucursalRoute(
    sucursalDataSource: SucursalDataSource
) {
    authenticate("another-auth"){
        post() {

            try {
                val sucursalRequest = call.receive<Sucursal>()
                if (extractPrincipalUsername(call)?.let { it1 -> Permissions(it1, "CREATION_SERVICES") } == true){
                    saveEmpresaToDatabase(
                        sucursalRequest,
                        sucursalDataSource
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

private fun Sucursal.toModel(): Sucursal = Sucursal(
    nombre = this.nombre,
    idEmpresa = this.idEmpresa,
    phone = this.phone,
    direccion = this.direccion,
    municipio = this.municipio,
    createdAt = LocalDateTime.now(),
    updatedAt = LocalDateTime.now()
)


private suspend fun PipelineContext<Unit, ApplicationCall>.saveEmpresaToDatabase(
    sucursalRequest: Sucursal,
    sucursalDataSource: SucursalDataSource
){
    val sucursal = sucursalRequest.toModel()
    val response = sucursalDataSource.saveSucursal(sucursal)
    return if (response) {
        call.respond(
            status = HttpStatusCode.OK,
            message = ApiResponseError(
                statusCode = HttpStatusCode.OK.value,
                message = "ok"
            )
        )
    } else {
        call.respond(
            status = HttpStatusCode.Forbidden,
            message = ApiResponseError(
                statusCode = 403,
                message = "Numero NIT ya esta en uso."
            )
        )
    }
}


private fun extractPrincipalUsername(call: ApplicationCall): String? =
    call.principal<JWTPrincipal>()
        ?.payload
        ?.getClaim("UserId")
        ?.asString()